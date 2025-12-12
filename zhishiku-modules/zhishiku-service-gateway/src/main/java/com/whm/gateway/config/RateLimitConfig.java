package com.whm.gateway.config;


import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 防刷限流配置（多维度支持）
 *
 * @author : 吴华明
 * @since 2025-12-12  16:14
 */
//@Configuration
public class RateLimitConfig {

    /**
     * 按IP限流（默认）
     */
//    @Bean
//    @Primary
//    public KeyResolver ipKeyResolver() {
//        return exchange -> Mono.just(
//                IpUtils.getRealIp(exchange.getRequest())
//        );
//    }
//
//    /**
//     * 按用户ID限流（需Token解析后）
//     */
//    @Bean
//    public KeyResolver userIdKeyResolver() {
//        return exchange -> Mono.justOrEmpty(
//                exchange.getRequest().getHeaders().getFirst("X-User-Id")
//        ).defaultIfEmpty("anonymous");
//    }
//
//    /**
//     * 按接口路径限流
//     */
//    @Bean
//    public KeyResolver apiKeyResolver() {
//        return exchange -> Mono.just(
//                exchange.getRequest().getPath().toString()
//        );
//    }
//
//    /**
//     * 配置Redis限流器（动态阈值）
//     */
//    @Bean
//    public RedisRateLimiter redisRateLimiter() {
//        // 可从配置中心动态读取阈值，此处先默认
//        return new RedisRateLimiter(10, 20);
//    }
}
