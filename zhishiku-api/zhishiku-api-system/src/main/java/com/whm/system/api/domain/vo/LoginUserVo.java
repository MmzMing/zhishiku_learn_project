package com.whm.system.api.domain.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 登录信息
 *
 * @author : 吴华明
 * @since 2025-12-14  13:46
 */
@Data
public class LoginUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userid;

    /**
     * 用户名（job_code，这个是登录的账号）
     */
    private String username;

    /**
     * 用户名（用户名称）
     */
    private String nickName;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 员工号
     */
    private String userAccount;
    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 用户信息
     */
    private SysUserVo sysUser;

}

