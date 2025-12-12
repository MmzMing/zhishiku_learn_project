package com.whm.common.redis.config;


import com.whm.common.core.utils.StringUtils;
import com.whm.common.redis.util.RedisPropertiesHelper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * redis 配置
 *
 * @author : 吴华明
 * @since 2025-12-12  21:05
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissionConfig {

    RedisProperties properties;

    @Autowired
    public RedissionConfig(RedisProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RedissonClient redissonClient() {
        if (RedisPropertiesHelper.getClusterConfiguration(properties) != null) {
            return Redisson.create(getClusterCponfig(properties));
        } else if (RedisPropertiesHelper.getSentinelConfig(properties) != null) {
            return Redisson.create(getSentinelConfig(properties));
        }
        return Redisson.create(getSingleConfig(properties));
    }

    private Config getClusterCponfig(RedisProperties properties) {
        Config config = new Config();
        List<String> clusterNodes = properties.getCluster().getNodes();
        config.useClusterServers()
                .addNodeAddress(clusterNodes.stream().map(o -> "redis://" + o).toArray(String[]::new))
                .setPassword(properties.getPassword());
        return config;
    }

    private Config getSentinelConfig(RedisProperties properties) {
        Config config = new Config();
        List<String> nodes = properties.getSentinel().getNodes();
        config.useSentinelServers()
                .setMasterName(properties.getSentinel().getMaster())
                .addSentinelAddress(nodes.stream().map(o -> "redis://" + o).toArray(String[]::new))
                .setPassword(properties.getPassword())
                .setReadMode(ReadMode.SLAVE);
        return config;
    }

    private Config getSingleConfig(RedisProperties properties) {
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s", properties.getHost() + "", properties.getPort() + "");
        if (StringUtils.isNotBlank(properties.getPassword()) && StringUtils.isNotBlank(properties.getUsername())) {
            config.useSingleServer()
                    .setAddress(redisUrl)
                    .setUsername(properties.getUsername())
                    .setPassword(properties.getPassword())
                    .setDatabase(properties.getDatabase());
        } else if (StringUtils.isBlank(properties.getPassword())) {
            config.useSingleServer()
                    .setAddress(redisUrl)
                    .setDatabase(properties.getDatabase());
        } else {
            config.useSingleServer()
                    .setAddress(redisUrl)
                    .setPassword(properties.getPassword())
                    .setDatabase(properties.getDatabase());
        }
        return config;
    }
}