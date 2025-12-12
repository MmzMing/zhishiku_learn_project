package com.whm.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * XSS过滤配置属性
 *
 * @author : 吴华明
 * @since 2025-12-12  16:07
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "xxx.gateway.xss")
public class XssConfigInfo {
    /**
     * 是否开启XSS过滤
     */
    private Boolean enabled;

    /**
     * 排除的URL列表（支持Ant表达式）
     */
    private List<String> excludeUrls;


}
