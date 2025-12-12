package com.whm.gateway.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * 白名单配置属性
 *
 * @author : 吴华明
 * @since 2025-12-12  16:03
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.gateway.ignore")
public class IgnoreWhiteConfigInfo {
    /**
     * 无需Token校验的URL列表（支持Ant表达式）
     */
    private List<String> whites;

    /**
     * 无需XSS过滤的URL列表
     */
    private List<String> xssExcludes;
}
