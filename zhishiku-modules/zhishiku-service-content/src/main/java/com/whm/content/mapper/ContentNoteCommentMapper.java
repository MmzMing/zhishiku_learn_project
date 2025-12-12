package com.whm.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.content.domain.po.ContentNoteComment;

/**
 * 内容管理服务_笔记评论表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:30:16
 */
public interface ContentNoteCommentMapper extends BaseMapper<ContentNoteComment>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param contentNoteComment 查询条件
     * @return 分页查询
     */
    IPage<ContentNoteComment> pageQuery(Page<ContentNoteComment> page, @Param("param") ContentNoteComment contentNoteComment);
}