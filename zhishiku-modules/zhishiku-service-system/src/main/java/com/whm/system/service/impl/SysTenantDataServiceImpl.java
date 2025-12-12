package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysTenantData;
import com.whm.system.mapper.SysTenantDataMapper;
import com.whm.system.service.SysTenantDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统服务_租户信息表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:09:09
 */
@Service
public class SysTenantDataServiceImpl extends ServiceImpl<SysTenantDataMapper, SysTenantData> implements SysTenantDataService {
    @Autowired
    private SysTenantDataMapper sysTenantDataMapper;

    /**
     * 分页查询
     *
     * @param sysTenantData 筛选条件
     * @param pageQuery     分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysTenantData> pageQuery(SysTenantData sysTenantData, PageQuery pageQuery) {
        return TableDataInfo.build(sysTenantDataMapper.pageQuery(pageQuery.build(), sysTenantData));
    }

}