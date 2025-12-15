package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysMenu;

import java.util.List;

/**
 * 系统服务_菜单权限表 表服务接口
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:29:43
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 分页查询
     *
     * @param sysMenu   筛选条件
     * @param pageQuery 分页查询
     * @return 查询结果
     */
    TableDataInfo<SysMenu> pageQuery(SysMenu sysMenu, PageQuery pageQuery);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);
    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectButtonByUserId(Long userId);
}