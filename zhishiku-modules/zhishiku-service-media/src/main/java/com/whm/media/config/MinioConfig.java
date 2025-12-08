package com.whm.media.config;


import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * minio配置信息
 *
 * @author 吴华明
 * @date 2025/9/16 17:42
 */
@Slf4j
@Configuration
public class MinioConfig {
    @Resource
    private MinioConfigInfo minioConfigInfo;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioConfigInfo.getEndpoint())
                .credentials(minioConfigInfo.getAccessKey(), minioConfigInfo.getSecretKey())
                .build();
    }
}
