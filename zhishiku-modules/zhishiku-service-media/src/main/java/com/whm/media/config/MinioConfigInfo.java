package com.whm.media.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * minio配置信息
 *
 * @author 吴华明
 * @since 2025/10/1 19:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfigInfo {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private Integer expiry;
    private Integer breakpointTime;

    private Map<String, String> bucket;
}
