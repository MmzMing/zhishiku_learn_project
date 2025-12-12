package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.system.domain.po.SysMenu;

/**
 * 系统服务_菜单权限表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:29:37
 */
public interface SysMenuMapper extends BaseMapper<SysMenu>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysMenu 查询条件
     * @return 分页查询
     */
    IPage<SysMenu> pageQuery(Page<SysMenu> page, @Param("param") SysMenu sysMenu);
}