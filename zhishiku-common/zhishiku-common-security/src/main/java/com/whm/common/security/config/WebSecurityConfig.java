package com.whm.common.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * Web安全配置
 *
 * @author : 吴华明
 * @since 2025-12-14  17:39
 */
@Configuration
@EnableWebSecurity
@Profile("dev")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置HTTP安全策略
     *
     * @param http HttpSecurity对象，用于配置web安全
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 创建认证成功处理器，用于处理用户登录成功后的跳转逻辑
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/");

        http
                // 禁用frame选项，允许页面被iframe嵌入
                .headers().frameOptions().disable()
                .and()
                // 配置URL访问权限
                .authorizeRequests()
                // 允许匿名访问的路径 - 相对于context-path配置，无需重复添加/auth前缀
                .antMatchers(
                        "/login",
                        "/register",
                        "/actuator/**",
                        "/instance/**",
                        "/favicon.ico",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**"
                ).permitAll()
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
                .and()
                // 配置表单登录 - 使用不同的URL避免与自定义登录冲突
                .formLogin().loginProcessingUrl("/spring-login")
                // 移除loginPage配置，避免重定向时出现双重/auth前缀
                .successHandler(successHandler).and()
                // 配置登出功能
                .logout().logoutUrl("/logout")
                .and()
                // 启用HTTP基本认证
                .httpBasic().and()
                // 禁用CSRF防护
                .csrf()
                .disable();
    }

}