package com.whm.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentNoteComment;
import com.whm.content.mapper.ContentNoteCommentMapper;
import com.whm.content.service.ContentNoteCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 内容管理服务_笔记评论表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:30:49
 */
@Service
public class ContentNoteCommentServiceImpl extends ServiceImpl<ContentNoteCommentMapper, ContentNoteComment> implements ContentNoteCommentService {
    @Autowired
    private ContentNoteCommentMapper contentNoteCommentMapper;

    /**
     * 分页查询
     *
     * @param contentNoteComment 筛选条件
     * @param pageQuery          分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<ContentNoteComment> pageQuery(ContentNoteComment contentNoteComment, PageQuery pageQuery) {
        return TableDataInfo.build(contentNoteCommentMapper.pageQuery(pageQuery.build(), contentNoteComment));
    }

}