package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLookupType;

/**
 * 系统服务_快码类型表 表服务接口
 * @author : 吴华明
 * @since : 2025-12-12 13:31:04
 */
public interface SysLookupTypeService extends IService<SysLookupType> {

    /**
     * 分页查询
     *
     * @param sysLookupType 筛选条件
     * @param pageQuery        分页查询
     * @return 查询结果
     */
    TableDataInfo<SysLookupType> pageQuery( SysLookupType sysLookupType, PageQuery pageQuery);

}