package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.api.domain.vo.SysUserVo;
import com.whm.system.domain.po.SysUser;

/**
 * 系统服务_用户信息表 表服务接口
 *
 * @author : 吴华明
 * @since : 2025-12-12 12:28:04
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询
     *
     * @param sysUser   筛选条件
     * @param pageQuery 分页查询
     * @return 查询结果
     */
    TableDataInfo<SysUser> pageQuery(SysUser sysUser, PageQuery pageQuery);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserVo selectUserByUserName(String userName);

}