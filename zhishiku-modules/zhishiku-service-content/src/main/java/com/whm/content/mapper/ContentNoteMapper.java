package com.whm.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.content.domain.po.ContentNote;

/**
 * 内容管理服务_笔记信息表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 10:43:43
 */
public interface ContentNoteMapper extends BaseMapper<ContentNote>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param contentNote 查询条件
     * @return 分页查询
     */
    IPage<ContentNote> pageQuery(Page<ContentNote> page, @Param("param") ContentNote contentNote);
}