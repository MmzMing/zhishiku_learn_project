package com.whm.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentNotePic;
import com.whm.content.mapper.ContentNotePicMapper;
import com.whm.content.service.ContentNotePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 内容管理服务_笔记多图关联表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:39:19
 */
@Service
public class ContentNotePicServiceImpl extends ServiceImpl<ContentNotePicMapper, ContentNotePic> implements ContentNotePicService {
    @Autowired
    private ContentNotePicMapper contentNotePicMapper;

    /**
     * 分页查询
     *
     * @param contentNotePic 筛选条件
     * @param pageQuery      分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<ContentNotePic> pageQuery(ContentNotePic contentNotePic, PageQuery pageQuery) {
        return TableDataInfo.build(contentNotePicMapper.pageQuery(pageQuery.build(), contentNotePic));
    }

}