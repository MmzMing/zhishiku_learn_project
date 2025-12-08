package com.whm.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.media.domain.po.MediaProcessHistory;
import org.apache.ibatis.annotations.Param;

/**
 * minio上传历史处理信息 表数据库访问层
 *
 * @author : 吴华明
 * @date : 2025-09-18 15:18:59
 */
public interface MediaProcessHistoryMapper extends BaseMapper<MediaProcessHistory> {
    /**
     * 分页查询
     *
     * @param page                分页对象
     * @param mediaProcessHistory 查询条件
     * @return 分页查询
     */
    IPage<MediaProcessHistory> pageQuery(Page<MediaProcessHistory> page, @Param("param") MediaProcessHistory mediaProcessHistory);
}