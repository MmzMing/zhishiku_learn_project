package com.whm.common.security.annotation;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * 开启自定义配置
 *
 * @author : 吴华明
 * @since 2025-12-14  15:10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 开启线程异步执行
@EnableAsync
// 自动加载类
@Import({FeignAutoConfiguration.class})
public @interface EnableCustomConfig {

}