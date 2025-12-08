package com.whm.xxljob.common.config;


import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Configuration;

/**
 * xxl job 配置
 *
 * @author : 吴华明
 * @since 2025-12-04  13:17
 */
@Configuration
@Data
public class XxlJobConfig {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;
    @Value("${xxl.job.admin.username}")
    private String username;
    @Value("${xxl.job.admin.password}")
    private String password;
    @Value("${xxl.job.admin.login}")
    private String login;
    @Value("${xxl.job.myjobgroup.pageList}")
    private String groupPageList;
    @Value("${xxl.job.myjobgroup.save}")
    private String groupSave;

    @Value("${xxl.job.myjobinfo.pageList}")
    private String pageList;
    @Value("${xxl.job.myjobinfo.add}")
    private String add;
    @Value("${xxl.job.myjobinfo.update}")
    private String update;
    @Value("${xxl.job.myjobinfo.start}")
    private String start;
    @Value("${xxl.job.myjobinfo.stop}")
    private String stop;
    @Value("${xxl.job.myjobinfo.remove}")
    private String remove;
    @Value("${xxl.job.myjobinfo.nextTriggerTime}")
    private String nextTriggerTime;
    @Value("${xxl.job.myjobinfo.trigger}")
    private String trigger;

    @Value("${xxl.job.accessToken}")
    private String accessToken;
    @Value("${xxl.job.executor.appname}")
    private String appname;
    @Value("${xxl.job.executor.address}")
    private String address;
    @Value("${xxl.job.executor.ip}")
    private String ip;
    @Value("${xxl.job.executor.port}")
    private int port;
    @Value("${xxl.job.executor.logpath}")
    private String logPath;
    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Value("${xxl.job.executor.addressType}")
    private Integer addressType;
    @Value("${xxl.job.executor.addressList}")
    private String addressList;
    @Value("${xxl.job.executor.title}")
    private String title;
    @Value("${spring.profiles.active:dev}")
    private String profiles;
    @Value("${spring.application.name}")
    private String applicationName;

    public String getTitle() {
        return StrUtil.isBlank(this.title) ? this.applicationName : this.title;
    }

}
