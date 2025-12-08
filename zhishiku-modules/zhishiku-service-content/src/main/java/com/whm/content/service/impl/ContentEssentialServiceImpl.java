package com.whm.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentEssential;
import com.whm.content.mapper.ContentEssentialMapper;
import com.whm.content.service.ContentEssentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * content_essential表服务实现类
 *
 * @author : 吴华明
 * @date : 2025-09-05 16:29:40
 */
@Service
@Slf4j
public class ContentEssentialServiceImpl extends ServiceImpl<ContentEssentialMapper, ContentEssential> implements ContentEssentialService {
    @Autowired
    private ContentEssentialMapper contentEssentialMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ContentEssential queryById(long id) {
        return contentEssentialMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param contentEssential 筛选条件
     * @param pageQuery        分页查询
     * @return
     */
    @Override
    public TableDataInfo<ContentEssential> contentEssentialPaginQuery(ContentEssential contentEssential, PageQuery pageQuery) {
        return TableDataInfo.build(contentEssentialMapper.contentEssentialPaginQuery(pageQuery.build(), contentEssential));
    }
}