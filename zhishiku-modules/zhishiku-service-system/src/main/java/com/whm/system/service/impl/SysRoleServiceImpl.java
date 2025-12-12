package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysRole;
import com.whm.system.mapper.SysRoleMapper;
import com.whm.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统服务_角色信息表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:20:21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 分页查询
     *
     * @param sysRole   筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysRole> pageQuery(SysRole sysRole, PageQuery pageQuery) {
        return TableDataInfo.build(sysRoleMapper.pageQuery(pageQuery.build(), sysRole));
    }

}