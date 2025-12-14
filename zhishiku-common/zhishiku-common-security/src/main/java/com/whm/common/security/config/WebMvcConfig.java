package com.whm.common.security.config;


import com.whm.common.security.interceptor.SecurityHeaderInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author : 吴华明
 * @since 2025-12-14  18:36
 */
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 不需要拦截地址
     */
    public static final String[] EXCLUDE_URLS = {
            "/login",
            "/logout",
            "/refresh"
    };

    /**
     * 添加拦截器配置
     *
     * @param registry 拦截器注册器，用于注册和配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Header拦截器，拦截所有路径，排除指定URL，并设置拦截器执行顺序
        registry.addInterceptor(getHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_URLS)
                .order(-10);
    }


    /**
     * 自定义请求头拦截器
     */
    public SecurityHeaderInterceptor getHeaderInterceptor() {
        return new SecurityHeaderInterceptor();
    }
}

