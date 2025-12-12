package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.system.domain.po.SysLookupType;

/**
 * 系统服务_快码类型表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:31:00
 */
public interface SysLookupTypeMapper extends BaseMapper<SysLookupType>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysLookupType 查询条件
     * @return 分页查询
     */
    IPage<SysLookupType> pageQuery(Page<SysLookupType> page, @Param("param") SysLookupType sysLookupType);
}