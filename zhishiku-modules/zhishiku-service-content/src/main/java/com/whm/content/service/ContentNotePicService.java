package com.whm.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentNotePic;

/**
 * 内容管理服务_笔记多图关联表 表服务接口
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:39:04
 */
public interface ContentNotePicService extends IService<ContentNotePic> {

    /**
     * 分页查询
     *
     * @param contentNotePic 筛选条件
     * @param pageQuery      分页查询
     * @return 查询结果
     */
    TableDataInfo<ContentNotePic> pageQuery(ContentNotePic contentNotePic, PageQuery pageQuery);

}