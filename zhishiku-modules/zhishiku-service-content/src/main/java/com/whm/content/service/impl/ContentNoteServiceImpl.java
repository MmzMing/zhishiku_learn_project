package com.whm.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentNote;
import com.whm.content.mapper.ContentNoteMapper;
import com.whm.content.service.ContentNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 内容管理服务_笔记信息表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 10:45:46
 */
@Service
public class ContentNoteServiceImpl extends ServiceImpl<ContentNoteMapper, ContentNote> implements ContentNoteService {
    @Autowired
    private ContentNoteMapper contentNoteMapper;


    /**
     * 分页查询
     *
     * @param contentNote 筛选条件
     * @param pageQuery   分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<ContentNote> pageQuery(ContentNote contentNote, PageQuery pageQuery) {
        return TableDataInfo.build(contentNoteMapper.pageQuery(pageQuery.build(), contentNote));
    }


}