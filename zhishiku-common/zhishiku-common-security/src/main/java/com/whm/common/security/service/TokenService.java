package com.whm.common.security.service;


import com.whm.common.core.constant.CacheConstants;
import com.whm.common.core.constant.SecurityConstants;
import com.whm.common.core.utils.ServletUtils;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.core.utils.check.JwtUtils;
import com.whm.common.core.utils.ip.IpUtils;
import com.whm.common.core.utils.uuid.UUID;
import com.whm.common.redis.service.RedisService;
import com.whm.common.security.utils.SecurityUtils;
import com.whm.system.api.domain.vo.LoginUserVo;
import com.whm.system.api.domain.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Jwt 登录信息处理
 *
 * @author : 吴华明
 * @since 2025-12-14  15:50
 */
@Slf4j
@Component
public class TokenService {
    @Autowired
    private RedisService redisService;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUserVo loginUser) {
        String token = UUID.fastUUID().toString();
        SysUserVo sysUser = loginUser.getSysUser();
        Long userId = sysUser.getId();
        String userName = sysUser.getUserName();
        String nickName = sysUser.getNickName();
        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setUsername(userName);
        loginUser.setNickName(nickName);
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>(3);
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>(2);
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", CacheConstants.EXPIRATION);
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserVo getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserVo getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }


    /**
     * 根据 token 获取登录用户信息
     *
     * @param token 用户令牌
     * @return 登录用户信息对象，如果获取失败则返回null
     */
    public LoginUserVo getLoginUser(String token) {
        try {
            // 验证 token 有效性并从 redis 中获取用户信息
            if (StringUtils.isNotEmpty(token)) {
                String userKey = JwtUtils.getUserKey(token);
                return redisService.getCacheObject(getTokenKey(userKey));
            }
        } catch (Exception e) {
            log.error("获取当前身份出错", e);
        }
        return null;
    }


    /**
     * 设置登录用户信息
     * 当登录用户对象及其令牌不为空时，刷新用户令牌信息
     *
     * @param loginUser 登录用户信息对象，包含用户的基本信息和认证令牌
     */
    public void setLoginUser(LoginUserVo loginUser) {
        // 检查登录用户对象和令牌是否有效，如果有效则刷新令牌
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }


    /**
     * 删除登录用户信息
     *
     * @param token 用户令牌
     */
    public void delLoginUser(String token) {
        // 如果token不为空，则删除对应的用户登录信息
        if (StringUtils.isNotEmpty(token)) {
            String userkey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userkey));
        }
    }


    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser 登录用户信息对象，包含令牌过期时间等信息
     */
    public void verifyToken(LoginUserVo loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        // 检查令牌是否即将过期，如果是则刷新令牌
        if (expireTime - currentTime <= CacheConstants.REFRESH_TIME * CacheConstants.MILLIS_MINUTE) {
            refreshToken(loginUser);
        }
    }


    /**
     * 刷新用户登录令牌的有效期
     *
     * @param loginUser 登录用户信息对象，包含用户的登录状态和令牌信息
     */
    public void refreshToken(LoginUserVo loginUser) {
        // 更新登录时间和过期时间
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + CacheConstants.EXPIRATION * CacheConstants.MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, CacheConstants.EXPIRATION, TimeUnit.MINUTES);
    }


    protected String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }
}
