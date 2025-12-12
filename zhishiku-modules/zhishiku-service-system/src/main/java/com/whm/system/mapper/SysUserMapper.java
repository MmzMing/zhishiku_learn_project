package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.system.domain.po.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * 系统服务_用户信息表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 12:27:47
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param sysUser 查询条件
     * @return 分页查询
     */
    IPage<SysUser> pageQuery(Page<SysUser> page, @Param("param") SysUser sysUser);
}