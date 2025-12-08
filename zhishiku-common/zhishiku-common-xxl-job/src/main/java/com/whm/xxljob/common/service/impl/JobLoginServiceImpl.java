package com.whm.xxljob.common.service.impl;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.whm.common.core.exception.service.ServiceException;
import com.whm.xxljob.common.config.XxlJobConfig;
import com.whm.xxljob.common.service.JobLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 登录服务实现类
 *
 * @author : 吴华明
 * @since 2025-12-04  15:46
 */
@Service
@Slf4j
public class JobLoginServiceImpl implements JobLoginService {
    @Resource
    XxlJobConfig xxlJobConfig;


    public String login() {
        Map<String, Object> map = new HashMap(3);
        map.put("userName", xxlJobConfig.getUsername());
        map.put("password", xxlJobConfig.getPassword());
        map.put("ifRemember", "on");
        String url = xxlJobConfig.getAdminAddresses() + xxlJobConfig.getLogin();
        HttpResponse response = HttpRequest.post(url).form(map).execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
        if (!cookieOpt.isPresent())
            throw new ServiceException("get xxl-job cookie error!");
        String value = cookieOpt.get().getValue();

        return String.format("XXL_JOB_LOGIN_IDENTITY=%s", value);
    }

}
