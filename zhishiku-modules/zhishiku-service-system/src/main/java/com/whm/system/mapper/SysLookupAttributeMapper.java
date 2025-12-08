package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.system.domain.po.SysLookupAttribute;
import org.apache.ibatis.annotations.Param;

/**
 * 快码属性设置 表数据库访问层
 *
 * @author : 吴华明
 * @date : 2025-09-09 11:50:42
 */
public interface SysLookupAttributeMapper extends BaseMapper<SysLookupAttribute>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysLookupAttribute 查询条件
     * @return 分页查询
     */
    IPage<SysLookupAttribute> pageQuery(Page<SysLookupAttribute> page, @Param("param") SysLookupAttribute sysLookupAttribute);
}