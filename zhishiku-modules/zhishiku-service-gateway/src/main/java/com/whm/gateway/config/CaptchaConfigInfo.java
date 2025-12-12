package com.whm.gateway.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


/**
 * 验证码配置属性（绑定配置文件）
 *
 * @author : 吴华明
 * @since 2025-12-12  16:03
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.gateway.captcha")
public class CaptchaConfigInfo {
    /**
     * 验证码开关
     */
    private Boolean enabled;

    /**
     * 验证码类型（math 数组计算 char 字符）
     */
    private String type;
}