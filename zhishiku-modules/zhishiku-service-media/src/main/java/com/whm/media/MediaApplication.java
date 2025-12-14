package com.whm.media;


import com.whm.common.core.utils.ConvertReUtils;
import com.whm.common.security.annotation.EnableRyFeignClients;
import com.whm.common.swagger.annotation.EnableCustomSwagger2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 媒咨管理
 *
 * @author 吴华明
 * @date 2025/9/16 14:56
 */
@Slf4j
@EnableRyFeignClients
@EnableCustomSwagger2
@SpringBootApplication
public class MediaApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MediaApplication.class);
    }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(MediaApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String nacosPort = env.getProperty("spring.cloud.nacos.server-addr");
        String path = ConvertReUtils.getString(env.getProperty("server.servlet.context-path"));
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Auth is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "nacos: \t\thttp://" + nacosPort + "/nacos" + "/\n\t" +
                "swagger: \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html\n" +
                "----------------------------------------------------------");
    }
}