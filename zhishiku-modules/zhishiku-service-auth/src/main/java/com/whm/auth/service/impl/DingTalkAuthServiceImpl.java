package com.whm.auth.service.impl;



import com.whm.auth.service.AuthService;
import com.whm.system.api.domain.vo.LoginUserVo;
import org.springframework.stereotype.Service;

/**
 * 钉钉登录
 *
 * @author : 吴华明
 * @since 2025-12-14  13:18
 */
@Service
public class DingTalkAuthServiceImpl implements AuthService {
    @Override
    public LoginUserVo login(String username, String password) {
        return null;
    }

    @Override
    public void logout(String loginName) {

    }

    @Override
    public void register(String username, String password) {

    }
}
