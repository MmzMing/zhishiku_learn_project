package com.whm.auth.service;


import com.whm.system.api.domain.vo.LoginUserVo;

/**
 * 登录认证服务
 *
 * @author : 吴华明
 * @since 2025-12-14  13:16
 */
public interface AuthService {


    LoginUserVo login(String username, String password);

    void logout(String loginName);

    void register(String username, String password);

}
