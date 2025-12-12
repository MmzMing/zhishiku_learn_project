package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysTenantData;

/**
 * 系统服务_租户信息表 表服务接口
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:08:58
 */
public interface SysTenantDataService extends IService<SysTenantData> {

    /**
     * 分页查询
     *
     * @param sysTenantData 筛选条件
     * @param pageQuery     分页查询
     * @return 查询结果
     */
    TableDataInfo<SysTenantData> pageQuery(SysTenantData sysTenantData, PageQuery pageQuery);

}