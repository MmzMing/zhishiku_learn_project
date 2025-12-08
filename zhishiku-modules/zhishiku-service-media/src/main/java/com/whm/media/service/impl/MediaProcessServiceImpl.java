package com.whm.media.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.core.utils.CollectionUtils;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.media.domain.po.MediaFiles;
import com.whm.media.domain.po.MediaProcess;
import com.whm.media.domain.po.MediaProcessHistory;
import com.whm.media.mapper.MediaFilesMapper;
import com.whm.media.mapper.MediaProcessHistoryMapper;
import com.whm.media.mapper.MediaProcessMapper;
import com.whm.media.service.MediaFilesService;
import com.whm.media.service.MediaProcessService;
import com.whm.media.utils.FfmpegVideoUtil;
import com.whm.media.utils.MinioUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * minio上传信息 表服务实现类
 *
 * @author : 吴华明
 * @date : 2025-09-18 15:16:59
 */
@Slf4j
@Service
public class MediaProcessServiceImpl extends ServiceImpl<MediaProcessMapper, MediaProcess> implements MediaProcessService {
    @Autowired
    @Qualifier("mediaExecutor")
    private Executor mediaExecutor;

    @Autowired
    private MediaProcessMapper mediaProcessMapper;

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Autowired
    MediaProcessHistoryMapper mediaProcessHistoryMapper;

    @Autowired
    MediaFilesService mediaFilesService;
    @Autowired
    MinioClient minioClient;
    @Value("${video-process.ffmpeg-path}")
    private String ffmpegPath;

    @Override
    public void mediaProcessProcessCode(int index, int total) {
        //CPU核-1
        int processors = Runtime.getRuntime().availableProcessors();
        //查数据
        List<MediaProcess> mediaProcessList = mediaProcessListByTotal(index, total, processors);
        if (CollectionUtils.isAnyEmpty(mediaProcessList))
            return;
        int size = mediaProcessList.size();
        //使用的计数器
        CountDownLatch countDownLatch = new CountDownLatch(size);
        mediaProcessList.forEach(mediaProcess -> {
            mediaExecutor.execute(() -> {
                try {
                    Long taskId = mediaProcess.getId();
                    String fileId = mediaProcess.getFileId();
                    String bucket = mediaProcess.getBucket();
                    String beginObjectName = mediaProcess.getFilePath();
                    String AfterObjectName = MinioUtil.getChunkFilePathByMd5(fileId, ".mp4");
                    // TODO: 写好common模块的redis后再用redis锁，先用数据库锁
                    //分布式锁redis锁
                    //====================获取锁====================
//                    String lockKey = "media:process:" + taskId;
//                    RLock rLock = redission.getLock(lockKey);
//                    if (rLock.tryLock(REDISSON_TIMEOUT, TimeUnit.MILLISECONDS)) {
//                        return;
//                    }
                    if (!this.startTask(taskId))
                        return;
                    //====================下载处理视频====================
                    File file = this.downloadVideo(bucket, beginObjectName);
                    if (ObjectUtil.isEmpty(file)) {
                        log.error("下载视频出错,bucket:{},objectName:{}", bucket, beginObjectName);
                        this.updateMediaProcessStatus(taskId, "3", fileId, null, "下载视频出错");
                        return;
                    }
                    //====================处理视频====================
                    File mp4File;
                    try {
                        mp4File = File.createTempFile("minio", ".mp4");
                    } catch (IOException e) {
                        log.error("创建临时文件异常,{}", e.getMessage(), e);
                        this.updateMediaProcessStatus(taskId, "3", fileId, null, "创建临时文件异常");
                        return;
                    }
                    String result = new FfmpegVideoUtil(ffmpegPath, file.getAbsolutePath(), fileId + ".mp4", mp4File.getAbsolutePath()).generateMp4();
                    if (!StringUtils.equals("success", result)) {
                        log.error("视频转码失败,bucket:{},objectName:{},原因:{}", bucket, beginObjectName, result);
                        this.updateMediaProcessStatus(taskId, "3", fileId, null, result);
                        return;
                    }
                    //====================上传到minio====================
                    mediaFilesService.addFilesForPathToMinIo(mp4File.getAbsolutePath(), MinioUtil.getMimeType("mp4"), bucket, AfterObjectName);

                    //mp4文件的url
                    String url = "/" + bucket + "/" + AfterObjectName;
                    //====================入库====================
                    this.updateMediaProcessStatus(taskId, "2", fileId, url, null);
                } finally {
                    //计算器减去1
                    countDownLatch.countDown();
                }
            });
        });

    }

    /**
     * 从MinIO下载视频文件到本地临时文件
     *
     * @param bucket     MinIO存储桶名称
     * @param objectName 存储对象名称（文件名）
     * @return 下载成功的本地临时文件对象，失败时返回null
     */
    private File downloadVideo(String bucket, String objectName) {
        try {
            // 从MinIO获取文件输入流
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            //创建临时文件
            File minioFile = File.createTempFile("minio", ".merge");
            FileOutputStream outputStream = new FileOutputStream(minioFile);
            // 将文件流复制到本地临时文件
            IOUtils.copy(stream, outputStream);
            return minioFile;
        } catch (Exception e) {
            log.error("下载文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage(), e);
        }
        return null;
    }


    /**
     * 根据分片索引和总数获取媒体处理任务列表
     * 1. 记录ID对总分片数取模等于当前分片索引（实现数据分片）
     * 2. 状态为1（待处理）或状态为3（处理中）
     * 3. 失败次数小于3次
     * 4. 限制返回记录数量为处理器数量
     *
     * @param index      分片索引，用于确定当前处理器负责的数据范围
     * @param total      分片总数，用于数据分片处理
     * @param processors 处理器数量，限制返回结果的数量
     * @return 符合条件的媒体处理任务列表
     */
    private List<MediaProcess> mediaProcessListByTotal(int index, int total, int processors) {
        return new LambdaQueryChainWrapper<>(mediaProcessMapper)
                .apply("id % {0} = {1}", total, index)
                .and(i -> i.eq(MediaProcess::getStatus, 1).or().eq(MediaProcess::getStatus, 3))
                .lt(MediaProcess::getFailCount, 3)
                .last("limit " + processors).list();
    }

    /**
     * 启动任务处理
     * 将指定ID的媒体处理任务状态更新为"正在处理"(状态码4)
     * 仅当任务当前状态为"未处理"(1)或"处理失败"(3)，且失败次数小于3次时才能启动
     *
     * @param id 媒体处理任务的唯一标识符
     * @return true表示更新成功，false表示更新失败(可能不满足启动条件)
     */
    private boolean startTask(long id) {
        return new LambdaUpdateChainWrapper<>(mediaProcessMapper)
                .set(MediaProcess::getStatus, 4)
                .eq(MediaProcess::getId, id)
                .and(i -> i.eq(MediaProcess::getStatus, 1).or().eq(MediaProcess::getStatus, 3))
                .lt(MediaProcess::getFailCount, 3)
                .update();
    }

    @Override
    public void updateMediaProcessStatus(Long taskId, String status, String fileId, String url, String errorMsg) {

        //获取要更新的任务
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if (ObjectUtil.isEmpty(mediaProcess)) {
            return;
        }
        //如果任务执行失败
        if (StringUtils.equals(status, "3")) {
            new LambdaUpdateChainWrapper<>(mediaProcessMapper)
                    .set(MediaProcess::getStatus, status)
                    .set(MediaProcess::getFinishDate, LocalDateTime.now())
                    .set(MediaProcess::getFailCount, mediaProcess.getFailCount() + 1)
                    .set(MediaProcess::getErrorMsg, errorMsg)
                    .eq(MediaProcess::getId, taskId);
            return;

        }
        //文件表记录
        new LambdaUpdateChainWrapper<>(mediaFilesMapper)
                .set(MediaFiles::getUrl, url)
                .set(MediaFiles::getStatus, "1")
                .set(MediaFiles::getUpdateTime, LocalDateTime.now())
                .set(MediaFiles::getUpdateName, "system")
                .set(MediaFiles::getUpdateBy, "system")
                .eq(MediaFiles::getId, fileId);
        //更新MediaProcess的状态
        mediaProcess.setStatus("2");
        mediaProcess.setFinishDate(LocalDateTime.now());
        mediaProcess.setUrl(url);
        //将MediaProcess表记录插入到MediaProcessHistory表
        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
        //更新和创建人
        mediaProcessHistory.setCreateName("system");
        mediaProcessHistory.setCreateBy("system");
        mediaProcessHistory.setUpdateName("system");
        mediaProcessHistory.setUpdateBy("system");
        mediaProcessHistory.setUpdateTime(LocalDateTime.now());
        mediaProcessHistory.setCreateTime(LocalDateTime.now());
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        //从MediaProcess删除当前任务
        mediaProcessMapper.deleteById(taskId);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MediaProcess queryById(long id) {
        return mediaProcessMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param mediaProcess 筛选条件
     * @param pageQuery    分页查询
     * @return
     */
    @Override
    public TableDataInfo<MediaProcess> pageQuery(MediaProcess mediaProcess, PageQuery pageQuery) {
        return TableDataInfo.build(mediaProcessMapper.pageQuery(pageQuery.build(), mediaProcess));
    }

    /**
     * 新增数据
     *
     * @param mediaProcess 实例对象
     * @return 实例对象
     */
    @Override
    public MediaProcess insert(MediaProcess mediaProcess) {
        mediaProcessMapper.insert(mediaProcess);
        return mediaProcess;
    }

    /**
     * 更新数据
     *
     * @param mediaProcess 实例对象
     * @return 实例对象
     */
    @Override
    public MediaProcess update(MediaProcess mediaProcess) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<MediaProcess> chainWrapper = new LambdaUpdateChainWrapper<MediaProcess>(mediaProcessMapper);

        if (!StringUtils.isEmpty(mediaProcess.getFileId())) {
            chainWrapper.eq(MediaProcess::getFileId, mediaProcess.getFileId());
        }
        if (!StringUtils.isEmpty(mediaProcess.getFileName())) {
            chainWrapper.eq(MediaProcess::getFileName, mediaProcess.getFileName());
        }
        if (!StringUtils.isEmpty(mediaProcess.getBucket())) {
            chainWrapper.eq(MediaProcess::getBucket, mediaProcess.getBucket());
        }
        if (!StringUtils.isEmpty(mediaProcess.getFilePath())) {
            chainWrapper.eq(MediaProcess::getFilePath, mediaProcess.getFilePath());
        }
        if (!StringUtils.isEmpty(mediaProcess.getStatus())) {
            chainWrapper.eq(MediaProcess::getStatus, mediaProcess.getStatus());
        }
        if (!StringUtils.isEmpty(mediaProcess.getUrl())) {
            chainWrapper.eq(MediaProcess::getUrl, mediaProcess.getUrl());
        }
        if (!StringUtils.isEmpty(mediaProcess.getErrorMsg())) {
            chainWrapper.eq(MediaProcess::getErrorMsg, mediaProcess.getErrorMsg());
        }
        if (!StringUtils.isEmpty(mediaProcess.getCreateBy())) {
            chainWrapper.eq(MediaProcess::getCreateBy, mediaProcess.getCreateBy());
        }
        if (!StringUtils.isEmpty(mediaProcess.getCreateName())) {
            chainWrapper.eq(MediaProcess::getCreateName, mediaProcess.getCreateName());
        }
        if (!StringUtils.isEmpty(mediaProcess.getUpdateBy())) {
            chainWrapper.eq(MediaProcess::getUpdateBy, mediaProcess.getUpdateBy());
        }
        if (!StringUtils.isEmpty(mediaProcess.getUpdateName())) {
            chainWrapper.eq(MediaProcess::getUpdateName, mediaProcess.getUpdateName());
        }
        //2. 设置主键，并更新
        chainWrapper.set(MediaProcess::getId, mediaProcess.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(mediaProcess.getId());
        } else {
            return mediaProcess;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        int total = mediaProcessMapper.deleteById(id);
        return total > 0;
    }
}
