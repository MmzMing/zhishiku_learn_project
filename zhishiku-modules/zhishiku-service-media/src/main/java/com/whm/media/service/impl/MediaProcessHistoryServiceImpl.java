package com.whm.media.service.impl;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.media.domain.po.MediaProcessHistory;
import com.whm.media.mapper.MediaProcessHistoryMapper;
import com.whm.media.service.MediaProcessHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * minio上传历史处理信息 表服务实现类
 *
 * @author : 吴华明
 * @date : 2025-09-18 15:20:59
 */
@Service
public class MediaProcessHistoryServiceImpl extends ServiceImpl<MediaProcessHistoryMapper, MediaProcessHistory> implements MediaProcessHistoryService {
    @Autowired
    private MediaProcessHistoryMapper mediaProcessHistoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MediaProcessHistory queryById(long id) {
        return mediaProcessHistoryMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param mediaProcessHistory 筛选条件
     * @param pageQuery           分页查询
     * @return
     */
    @Override
    public TableDataInfo<MediaProcessHistory> pageQuery(MediaProcessHistory mediaProcessHistory, PageQuery pageQuery) {
        return TableDataInfo.build(mediaProcessHistoryMapper.pageQuery(pageQuery.build(), mediaProcessHistory));
    }

    /**
     * 新增数据
     *
     * @param mediaProcessHistory 实例对象
     * @return 实例对象
     */
    @Override
    public MediaProcessHistory insert(MediaProcessHistory mediaProcessHistory) {
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        return mediaProcessHistory;
    }

    /**
     * 更新数据
     *
     * @param mediaProcessHistory 实例对象
     * @return 实例对象
     */
    @Override
    public MediaProcessHistory update(MediaProcessHistory mediaProcessHistory) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<MediaProcessHistory> chainWrapper = new LambdaUpdateChainWrapper<MediaProcessHistory>(mediaProcessHistoryMapper);

        if (!StringUtils.isEmpty(mediaProcessHistory.getFileId())) {
            chainWrapper.eq(MediaProcessHistory::getFileId, mediaProcessHistory.getFileId());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getFileName())) {
            chainWrapper.eq(MediaProcessHistory::getFileName, mediaProcessHistory.getFileName());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getBucket())) {
            chainWrapper.eq(MediaProcessHistory::getBucket, mediaProcessHistory.getBucket());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getStatus())) {
            chainWrapper.eq(MediaProcessHistory::getStatus, mediaProcessHistory.getStatus());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getUrl())) {
            chainWrapper.eq(MediaProcessHistory::getUrl, mediaProcessHistory.getUrl());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getFilePath())) {
            chainWrapper.eq(MediaProcessHistory::getFilePath, mediaProcessHistory.getFilePath());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getErrorMsg())) {
            chainWrapper.eq(MediaProcessHistory::getErrorMsg, mediaProcessHistory.getErrorMsg());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getCreateBy())) {
            chainWrapper.eq(MediaProcessHistory::getCreateBy, mediaProcessHistory.getCreateBy());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getCreateName())) {
            chainWrapper.eq(MediaProcessHistory::getCreateName, mediaProcessHistory.getCreateName());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getUpdateBy())) {
            chainWrapper.eq(MediaProcessHistory::getUpdateBy, mediaProcessHistory.getUpdateBy());
        }
        if (!StringUtils.isEmpty(mediaProcessHistory.getUpdateName())) {
            chainWrapper.eq(MediaProcessHistory::getUpdateName, mediaProcessHistory.getUpdateName());
        }
        //2. 设置主键，并更新
        chainWrapper.set(MediaProcessHistory::getId, mediaProcessHistory.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(mediaProcessHistory.getId());
        } else {
            return mediaProcessHistory;
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
        int total = mediaProcessHistoryMapper.deleteById(id);
        return total > 0;
    }
}