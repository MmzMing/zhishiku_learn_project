package com.whm.common.security.interceptor;


import com.whm.common.core.constant.SecurityConstants;
import com.whm.common.core.context.SecurityContextHolder;
import com.whm.common.core.utils.ServletUtils;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.security.utils.AuthUtil;
import com.whm.common.security.utils.SecurityUtils;
import com.whm.system.api.domain.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 *
 * @author : 吴华明
 * @since 2025-12-14  16:26
 */
public class SecurityHeaderInterceptor  implements AsyncHandlerInterceptor {

    public static AuthUtil authUtil = new AuthUtil();

    @Value("${spring.profiles.active:}")
    private String activeProfile;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 非生产环境不注册安全拦截器
        if (!"prod".equals(activeProfile) && !"production".equals(activeProfile)) {
            // 可以选择性注册或者完全不注册
            return  true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));

        String token = SecurityUtils.getToken();
        if (StringUtils.isNotEmpty(token)) {
            LoginUserVo loginUser = authUtil.getLoginUser(token);
            if (StringUtils.isNotNull(loginUser)) {
                authUtil.verifyLoginUserExpire(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
                SecurityContextHolder.setNickName(loginUser.getNickName());
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        SecurityContextHolder.remove();
    }
}
