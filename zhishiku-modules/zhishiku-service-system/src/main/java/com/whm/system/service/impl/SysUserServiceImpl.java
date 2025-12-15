package com.whm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.api.domain.vo.SysUserVo;
import com.whm.system.domain.po.SysUser;
import com.whm.system.mapper.SysUserMapper;
import com.whm.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统服务_用户信息表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 12:28:14
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 分页查询
     *
     * @param sysUser   筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysUser> pageQuery(SysUser sysUser, PageQuery pageQuery) {
        return TableDataInfo.build(sysUserMapper.pageQuery(pageQuery.build(), sysUser));
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUserVo selectUserByUserName(String userName) {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, userName)
                //.eq(SysUser::getTenantId, tenantId)//TODO后面加入多租户
                .eq(SysUser::getDeleted, 0));
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        return sysUserVo;
    }
}