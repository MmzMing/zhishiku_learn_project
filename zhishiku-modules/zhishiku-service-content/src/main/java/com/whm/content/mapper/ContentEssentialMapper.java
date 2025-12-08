package com.whm.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.content.domain.po.ContentEssential;
import org.apache.ibatis.annotations.Param;

/**
 * content_essential表数据库访问层
 *
 * @author : 吴华明
 * @date : 2025-09-05 16:20:44
 */
public interface ContentEssentialMapper extends BaseMapper<ContentEssential> {

    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param contentEssential 查询条件
     * @return 分页查询
     */
    IPage<ContentEssential> contentEssentialPaginQuery(Page<ContentEssential> page, @Param("param") ContentEssential contentEssential);
}