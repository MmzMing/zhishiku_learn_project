package com.whm.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.media.domain.po.MediaFiles;
import org.apache.ibatis.annotations.Param;

/**
 * 媒资信息 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-09-16 18:44:37
 */
public interface MediaFilesMapper extends BaseMapper<MediaFiles>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param mediaFiles 查询条件
     * @return 分页查询
     */
    IPage<MediaFiles> pageQuery(Page<MediaFiles> page, @Param("param") MediaFiles mediaFiles);
}