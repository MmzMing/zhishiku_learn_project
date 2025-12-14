package com.whm.auth.admin;


import lombok.Data;

/**
 * 登录参数
 *
 * @author : 吴华明
 * @since 2025-12-14  16:53
 */
@Data
public class LoginDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 登录方式
     */
    private String loginType;
}
