package com.whm.common.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 测试环境
 *
 * @author : 吴华明
 * @since 2025-12-14  22:33
 */
@Configuration
@EnableWebSecurity
@Profile("prod")
public class DevSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auth/**").authenticated()// 仅/auth/**路径需要认证通过
                .anyRequest().permitAll() // 所有请求无需登录
                .and()
                .formLogin().disable() // 关闭登录页
                .logout().disable() // 关闭登出
                .httpBasic().disable() // 关闭Basic认证
                .csrf().disable(); // 关闭CSRF
    }

    // 解决UserDetailsService报错
    @Override
    protected void configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder auth) throws Exception {
        // 空实现即可
    }
}