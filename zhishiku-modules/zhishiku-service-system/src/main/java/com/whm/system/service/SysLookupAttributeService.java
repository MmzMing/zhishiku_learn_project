package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLookupAttribute;

/**
 * 系统服务_快码属性设置表 表服务接口
 * @author : 吴华明
 * @since : 2025-12-12 13:58:46
 */
public interface SysLookupAttributeService extends IService<SysLookupAttribute> {

    /**
     * 分页查询
     *
     * @param sysLookupAttribute 筛选条件
     * @param pageQuery        分页查询
     * @return 查询结果
     */
    TableDataInfo<SysLookupAttribute> pageQuery( SysLookupAttribute sysLookupAttribute, PageQuery pageQuery);

}