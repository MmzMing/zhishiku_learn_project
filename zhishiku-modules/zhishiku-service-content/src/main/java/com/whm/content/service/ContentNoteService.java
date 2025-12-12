package com.whm.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentNote;

/**
 * 内容管理服务_笔记信息表 表服务接口
 * @author : 吴华明
 * @since : 2025-12-12 10:44:38
 */
public interface ContentNoteService extends IService<ContentNote> {

    /**
     * 分页查询
     *
     * @param contentNote 筛选条件
     * @param pageQuery        分页查询
     * @return
     */
    TableDataInfo<ContentNote> pageQuery( ContentNote contentNote, PageQuery pageQuery);


}