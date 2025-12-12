package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.system.domain.po.SysTenantData;
import org.apache.ibatis.annotations.Param;

/**
 * 系统服务_租户信息表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:08:47
 */
public interface SysTenantDataMapper extends BaseMapper<SysTenantData> {
    /**
     * 分页查询
     *
     * @param page          分页对象
     * @param sysTenantData 查询条件
     * @return 分页查询
     */
    IPage<SysTenantData> pageQuery(Page<SysTenantData> page, @Param("param") SysTenantData sysTenantData);
}