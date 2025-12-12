package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.system.domain.po.SysRole;
import org.apache.ibatis.annotations.Param;

/**
 * 系统服务_角色信息表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:20:00
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param sysRole 查询条件
     * @return 分页查询
     */
    IPage<SysRole> pageQuery(Page<SysRole> page, @Param("param") SysRole sysRole);
}