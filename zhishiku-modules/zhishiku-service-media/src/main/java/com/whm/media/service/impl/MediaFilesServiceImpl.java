package com.whm.media.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.core.constant.DateConstant;
import com.whm.common.core.exception.service.ServiceException;
import com.whm.media.config.MinioConfigInfo;
import com.whm.media.domain.po.MediaFiles;
import com.whm.media.domain.po.MediaProcess;
import com.whm.media.mapper.MediaFilesMapper;
import com.whm.media.mapper.MediaProcessMapper;
import com.whm.media.service.MediaFilesService;
import com.whm.media.utils.FileType;
import com.whm.media.utils.MinioUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 媒资信息 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-09-16 18:46:42
 */
@Slf4j
@Service
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles> implements MediaFilesService {
    @Autowired
    MinioClient minioClient;

    @Autowired
    MediaFilesMapper mediaFilesMapper;
    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Resource
    private MinioConfigInfo minioConfigInfo;


    /**
     * 上传文件到MinIO服务器
     *
     * @param file       要上传的文件对象
     * @param objectName 自定义存储位置以及名称文件，校验后变为文件存储路径，fileName要他的名字，路径要他的路径
     * @return Boolean 上传是否成功
     */
    @Override
    public Boolean uploadFile(MultipartFile file, String objectName) {

        String fileName = file.getOriginalFilename();

        if (StringUtils.isEmpty(fileName)) {
            log.error("文件名为空");
            throw new ServiceException("文件名为空");
        }
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String mimeType = MinioUtil.getMimeType(extension);

        //文件的md5
        String fileMd5 = MinioUtil.getFileMd5(file);
        //(子目录 / 年/月/日 / 文件的md5值 + 扩展名) 或者 (子目录 / 自定义路径 / 文件的md5值 + 扩展名)
        objectName = StringUtils.isEmpty(objectName)
                ? MinioUtil.getRandomFilePathByName(fileName, DateUtil.format(LocalDateTime.now(), DateConstant.DATE_PATH), fileMd5).getPath()
                : MinioUtil.getRandomFilePathByName(fileName, objectName, fileMd5).getPath();
        //根据objectName查出文件信息
//        MediaFiles mediaFiles = mediaFilesMapper.selectOne(new LambdaQueryWrapper<MediaFiles>().eq(MediaFiles::getFileId, fileMd5));
        MediaFiles mediaFiles = lambdaQuery().eq(MediaFiles::getFileId, fileMd5).one();
        //上传文件到minio
        if (ObjectUtils.isEmpty(mediaFiles)) {
            addFilesToMinIo(file, mimeType, minioConfigInfo.getBucket().get("files"), objectName);
        }
        //添加到数据库
        SpringUtil.getBean(MediaFilesServiceImpl.class).addMediaFilesToDb(mediaFiles, fileMd5, fileName, file.getSize(), minioConfigInfo.getBucket().get("files"), objectName, extension, mimeType);
        return true;
    }


    /**
     * 上传文件分片到MinIO存储
     *
     * @param file       待上传的文件分片
     * @param fileMd5    文件的MD5值，用于构建存储路径
     * @param chunkIndex 分片索引，标识当前是第几个分片
     * @return 上传结果，true表示上传成功
     */
    @Override
    public Boolean uploadChunks(MultipartFile file, String fileMd5, int chunkIndex) {
        // 获取文件的MIME类型
        String mimeType = MinioUtil.getMimeType(null);

        // 构建分片文件存储路径，目录结构格式：第一个字符/第二个字符/完整MD5值/chunk/
        String chunkFilePath = MinioUtil.getChunkFilePath(fileMd5) + chunkIndex;

        // 将文件分片添加到MinIO存储中
        addFilesToMinIo(file, mimeType, minioConfigInfo.getBucket().get("videoFiles"), chunkFilePath);

        // 上传成功
        return true;
    }


    /**
     * 合并分块文件，并完成文件上传的后续处理流程。
     * <p>
     * 该方法主要执行以下操作：
     * 1. 获取与文件相关的元数据及分块路径信息；
     * 2. 使用MinIO客户端将所有分块文件合并成完整文件；
     * 3. 对合并后的文件进行MD5一致性校验；
     * 4. 将完整的文件信息保存到数据库中；
     * 5. 清理已合并的分块文件。
     *
     * @param fileMd5    文件的MD5值，用于标识唯一文件及构建存储路径
     * @param fileName   原始文件名，用于提取扩展名等信息
     * @param chunkTotal 分块总数
     * @return Boolean   操作成功返回true，失败则抛出异常
     */
    @Override
    public Boolean mergeChunks(String fileMd5, String fileName, int chunkTotal) {
        //===========1.获取文件信息===========
        String chunkFilePath = MinioUtil.getChunkFilePath(fileMd5);
        String chunkBucket = minioConfigInfo.getBucket().get("videoFiles");
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String objectName = MinioUtil.getChunkFilePathByMd5(fileMd5, extension);
        String mimeType = MinioUtil.getMimeType(extension);
        // 找到所有的分块文件作为合成来源
        List<ComposeSource> chunksSources = Stream.iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> ComposeSource.builder().bucket(chunkBucket).object(chunkFilePath + i).build())
                .collect(Collectors.toList());
        //===========2.合并文件===========
        try {
            minioClient.composeObject(ComposeObjectArgs.builder()
                    .bucket(chunkBucket)
                    .object(objectName)
                    .sources(chunksSources)
                    .build());
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", chunkBucket, objectName, e.getMessage(), e);
            throw new ServiceException("合并文件出错");
        }
        //===========3.校验合并后的和源文件是否一致===========
        StatObjectResponse fileObjectStat = checkChunkFiles(chunkBucket, objectName, fileMd5);
        //===========4.将文件信息入库===========
        SpringUtil.getBean(MediaFilesServiceImpl.class).addMediaFilesToDb(null, fileMd5, fileName, fileObjectStat.size(), chunkBucket, objectName, extension, mimeType);
        //===========5.清理minio分块文件===========
        clearChunkFiles(chunkFilePath, chunkTotal);
        return true;
    }


    /**
     * 检查文件是否存在
     * 该方法首先在数据库中查询文件记录，如果存在则进一步在MinIO中验证文件实际存在性
     *
     * @param filesMd5 文件的MD5值，用于唯一标识文件
     * @return Boolean 文件是否存在的检查结果，true表示文件存在，false表示文件不存在
     */
    @Override
    public Boolean checkFiles(String filesMd5) {
        //先检验数据库
        MediaFiles mediaFiles = mediaFilesMapper.selectOne(new LambdaQueryWrapper<MediaFiles>().eq(MediaFiles::getFileId, filesMd5));
        //再检验minio
        if (ObjectUtils.isNotEmpty(mediaFiles)) {
            //获取桶信息
            StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                    .bucket(mediaFiles.getBucket())
                    .object(mediaFiles.getFilePath())
                    .build();
            try {
                StatObjectResponse statObjectResponse = minioClient.statObject(statObjectArgs);
                if (ObjectUtils.isNotEmpty(statObjectResponse)) {
                    return true;
                }
            } catch (Exception e) {
                //这里如果查不到就会报一个“The specified key does not exist”错误
                return false;
            }
        }
        return false;
    }

    /**
     * 检查指定文件的分片是否存在
     *
     * @param fileMd5    文件的MD5值，用于确定文件的分片存储路径
     * @param chunkIndex 分片索引，标识具体是第几个分片
     * @return Boolean 存在返回true，不存在返回false
     */
    @Override
    public Boolean checkChunk(String fileMd5, int chunkIndex) {
        String chunkFileFolderPath = MinioUtil.getChunkFilePath(fileMd5);
        //获取桶信息
        StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                .bucket(minioConfigInfo.getBucket().get("videoFiles"))
                .object(chunkFileFolderPath + chunkIndex)
                .build();
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(statObjectArgs);
            if (ObjectUtils.isNotEmpty(statObjectResponse)) {
                return true;
            }
        } catch (Exception e) {
            //这里如果查不到就会报一个“The specified key does not exist”错误
            return false;
        }
        return false;
    }


    /**
     * 检查分片文件的完整性和正确性
     * <p>
     * 该方法主要完成以下功能：
     * 1. 获取合并后的文件流并计算其MD5值
     * 2. 对比合并文件的MD5值与源文件MD5值是否一致
     * 3. 获取合并后文件的基本属性供入库使用
     * </p>
     *
     * @param chunkBucket 分片文件所在的存储桶名称
     * @param objectName  合并后的对象名称
     * @param fileMd5     源文件的MD5值，用于校验完整性
     * @return StatObjectResponse 合并后文件的状态信息，包含文件大小等属性
     * @throws ServiceException 当文件校验失败或MD5值不匹配时抛出
     */
    private StatObjectResponse checkChunkFiles(String chunkBucket, String objectName, String fileMd5) {
        // 采用流式读取方式计算合并后文件的MD5值并进行比对
        try {
            // 获取合并后的文件流并计算其MD5值
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(chunkBucket)
                    .object(objectName)
                    .build());
            // 对比合并文件的MD5值与源文件MD5值是否一致
            if (!StringUtils.equals(fileMd5, DigestUtils.md5Hex(stream))) {
                log.error("合并文件出错,bucket:{},objectName:{},错误信息:{}", chunkBucket, objectName, "合并后的文件MD5值与源文件MD5值不一致");
                throw new ServiceException("合并后的文件MD5值与源文件MD5值不一致");
            }
            // 获取合并后文件的基本属性（如大小）以供入库使用
            return minioClient.statObject(StatObjectArgs.builder()
                    .bucket(chunkBucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", chunkBucket, objectName, e.getMessage(), e);
            throw new ServiceException("文件校验失败");
        }
    }


    /**
     * 将文件上传到minio
     *
     * @param file       文件
     * @param mimeType   媒体类型
     * @param bucket     桶
     * @param objectName 对象名
     */
    public void addFilesToMinIo(MultipartFile file, String mimeType, String bucket, String objectName) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(mimeType)
                            .build());
            log.info("上传文件流到minio成功,bucket:{},objectName:{}", bucket, objectName);
        } catch (Exception e) {
            log.error("上传文件流出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage(), e);
            throw new ServiceException("上传文件时发生错误");
        }
    }

    /**
     * 根据本地文件路径上传到MinIO存储服务
     *
     * @param localFilePath 本地文件路径
     * @param mimeType      文件的媒体类型
     * @param bucket        MinIO存储桶名称
     * @param objectName    存储对象名称（可包含子目录路径）
     */
    public void addFilesForPathToMinIo(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            // 构建上传对象参数
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucket)
                    .filename(localFilePath)
                    .object(objectName)
                    .contentType(mimeType)
                    .build();
            // 上传文件到MinIO
            minioClient.uploadObject(uploadObjectArgs);
            log.info("上传文件到minio成功,bucket:{},objectName:{}", bucket, objectName);
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage(), e);
            throw new ServiceException("上传文件时发生错误");
        }
    }


    /**
     * 保存到数据库
     *
     * @param mediaFiles 媒体文件信息
     * @param fileMd5    文件md5
     * @param fileName   文件名称
     * @param fileSize   文件大小
     * @param bucket     桶
     * @param objectName 对象名称
     * @param extension  扩展名
     * @return Boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addMediaFilesToDb(MediaFiles mediaFiles, String fileMd5, String fileName, long fileSize, String bucket, String objectName, String extension, String mimeType) {
        //将文件信息保存到数据库
        LocalDateTime nowDate = LocalDateTime.now();
        if (ObjectUtils.isEmpty(mediaFiles)) {
            mediaFiles = new MediaFiles();
            //组织id，暂时写死
            mediaFiles.setOrganizationId(101L);
            mediaFiles.setOrganizationName("测试组织");
            mediaFiles.setFileId(fileMd5);
            mediaFiles.setFileName(fileName);
            mediaFiles.setFilePath(objectName);
            mediaFiles.setFileSize(fileSize);
            mediaFiles.setBucket(bucket);
            mediaFiles.setFileType(FileType.getFileType(extension).getName());
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            mediaFiles.setCreateDate(nowDate);
            mediaFiles.setChangeDate(nowDate);
            mediaFiles.setStatus("1");
            mediaFiles.setAuditStatus("00001");

            //先写死公共字段
            mediaFiles.setCreateBy("system");
            mediaFiles.setUpdateBy("system");
            mediaFiles.setCreateName("system");
            mediaFiles.setUpdateName("system");
            mediaFiles.setCreateTime(nowDate);
            mediaFiles.setUpdateTime(nowDate);
            mediaFiles.setDeleted(0);

            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert <= 0) {
                throw new ServiceException("向数据库保存文件失败");
            }
            //记录待处理任务
            //如果是avi视频写入待处理任务
            addWaitingTask(mediaFiles, mimeType);
        } else {
            //更新数据库
            mediaFiles.setFileName(fileName);
            //先写死公共字段
            mediaFiles.setCreateBy("system");
            mediaFiles.setUpdateBy("system");
            mediaFiles.setCreateName("system");
            mediaFiles.setUpdateName("system");
            mediaFiles.setCreateTime(nowDate);
            mediaFiles.setUpdateTime(nowDate);
            mediaFiles.setDeleted(0);
            int update = mediaFilesMapper.update(mediaFiles, new LambdaUpdateWrapper<MediaFiles>().eq(MediaFiles::getFileId, fileMd5));
            if (update <= 0) {
                throw new ServiceException("向数据库更新文件失败");
            }
        }
        return true;
    }

    /**
     * 添加等待处理的媒体任务
     *
     * @param mediaFiles 媒体文件信息对象，用于复制属性到处理任务中
     * @param mimeType   媒体文件的MIME类型，用于判断是否需要创建处理任务
     */
    private void addWaitingTask(MediaFiles mediaFiles, String mimeType) {
        // 判断是否为avi视频格式，如果是则创建媒体处理任务
        if ("video/x-msvideo".equals(mimeType)) {
            MediaProcess mediaProcess = new MediaProcess();
            BeanUtils.copyProperties(mediaFiles, mediaProcess);
            //状态是未处理
            mediaProcess.setStatus("1");
            mediaProcess.setCreateDate(LocalDateTime.now());
            mediaProcess.setFailCount(0);
            mediaProcess.setUrl(null);
            int taskInt = mediaProcessMapper.insert(mediaProcess);
            if (taskInt <= 0) {
                log.error("写入待处理任务保存文件失败");
                throw new ServiceException("向数据库保存文件失败");
            }
        }
    }

    /**
     * 清除分块文件
     *
     * @param chunkFileFolderPath 分块文件路径
     * @param chunkTotal          分块文件总数
     */
    private void clearChunkFiles(String chunkFileFolderPath, int chunkTotal) {
        Iterable<DeleteObject> objects = Stream.iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> new DeleteObject(chunkFileFolderPath + i))
                .collect(Collectors.toList());
        RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder()
                .bucket(minioConfigInfo.getBucket().get("videoFiles"))
                .objects(objects)
                .build();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
        //要想真正删除
        results.forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                log.error("删除分块文件失败,{}", e.getMessage());
            }
        });

    }
}