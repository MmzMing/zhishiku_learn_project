package com.whm.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}