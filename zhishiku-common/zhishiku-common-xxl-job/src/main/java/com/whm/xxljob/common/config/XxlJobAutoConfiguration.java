package com.whm.xxljob.common.config;


import com.whm.xxljob.common.core.XxlJobAutoRegister;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * xxl-job 自动配置
 *
 * @author : 吴华明
 * @since 2025-12-04  13:23
 */
@Configuration
@ConditionalOnProperty(
        prefix = "xxl.job.default.config",
        value = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@Import({XxlJobConfig.class})
@ComponentScan({"com.whm.xxljob.common"})
public class XxlJobAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(XxlJobAutoConfiguration.class);
    @Autowired
    XxlJobConfig xxlJobConfig;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        this.logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(this.xxlJobConfig.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(this.xxlJobConfig.getAppname());
        xxlJobSpringExecutor.setAddress(this.xxlJobConfig.getAddress());
        xxlJobSpringExecutor.setIp(this.xxlJobConfig.getIp());
        xxlJobSpringExecutor.setPort(this.xxlJobConfig.getPort());
        xxlJobSpringExecutor.setAccessToken(this.xxlJobConfig.getAccessToken());
        xxlJobSpringExecutor.setLogPath(this.xxlJobConfig.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(this.xxlJobConfig.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }

    @Bean
    public XxlJobAutoRegister xxlJobAutoRegister() {
        return new XxlJobAutoRegister();
    }
}

