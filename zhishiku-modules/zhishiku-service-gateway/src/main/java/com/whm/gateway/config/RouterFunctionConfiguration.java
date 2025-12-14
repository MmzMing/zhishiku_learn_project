package com.whm.gateway.config;


import com.whm.gateway.handler.ValidateCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * 函数式路由配置（验证码接口）
 *
 * @author : 吴华明
 * @since 2025-12-12  19:35
 */
@Configuration
public class RouterFunctionConfiguration {
    @Autowired
    private ValidateCodeHandler validateCodeHandler;
    /**
     * 验证码生成接口路由
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions
                // 图形验证码路由
                .route(RequestPredicates.GET("/graph/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                validateCodeHandler)
                // 微信二维码路由 TODO 待完善
                .andRoute(RequestPredicates.GET("/wechat/code"),
                validateCodeHandler);
    }
}
