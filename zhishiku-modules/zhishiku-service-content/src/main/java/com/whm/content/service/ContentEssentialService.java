package com.whm.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentEssential;

/**
 * content_essential表服务接口
 *
 * @author : 吴华明
 * @date : 2025-09-05 16:29:29
 */
public interface ContentEssentialService extends IService<ContentEssential> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ContentEssential queryById(long id);

    /**
     * 分页查询
     *
     * @param contentEssential 筛选条件
     * @param pageQuery        分页对象
     * @return
     */
    TableDataInfo<ContentEssential> contentEssentialPaginQuery(ContentEssential contentEssential, PageQuery pageQuery);

}
