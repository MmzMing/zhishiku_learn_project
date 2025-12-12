package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whm.system.domain.po.SysMenu;
import com.whm.system.mapper.SysMenuMapper;
import com.whm.system.service.SysMenuService;

/**
 * 系统服务_菜单权限表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:29:54
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 分页查询
     *
     * @param sysMenu 筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysMenu> pageQuery(SysMenu sysMenu, PageQuery pageQuery){
        return TableDataInfo.build(sysMenuMapper.pageQuery(pageQuery.build(), sysMenu));
    }

}