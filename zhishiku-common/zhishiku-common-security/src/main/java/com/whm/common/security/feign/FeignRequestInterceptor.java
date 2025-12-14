package com.whm.common.security.feign;


import com.whm.common.core.constant.HttpConstant;
import com.whm.common.core.constant.SecurityConstants;
import com.whm.common.core.utils.ServletUtils;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.core.utils.ip.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * feign 请求拦截器
 *
 * @author : 吴华明
 * @since 2025-12-14  15:10
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    /**
     * 应用请求模板处理方法
     *
     * @param requestTemplate 请求模板对象，用于设置HTTP请求的相关信息
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {

        HttpServletRequest request = ServletUtils.getRequest();
        Map<String, String> headers = ServletUtils.getHeaders(request);
        // 传递用户信息请求头，防止丢失
        setHeaderInformation(requestTemplate, headers);
        //子线程的
        Map<String, String> threadHeader = ServletUtils.getThreadHeader();
        if (threadHeader != null) {
            setHeaderInformation(requestTemplate, threadHeader);
        }
    }


    /**
     * 设置请求头信息
     *
     * @param requestTemplate 请求模板对象，用于设置HTTP请求头
     * @param headers 包含用户信息的请求头映射表
     */
    private void setHeaderInformation(RequestTemplate requestTemplate, Map<String, String> headers) {
        // 设置用户ID到请求头
        String userId = headers.get(SecurityConstants.DETAILS_USER_ID);
        if (StringUtils.isNotEmpty(userId)) {
            requestTemplate.header(SecurityConstants.DETAILS_USER_ID, userId);
        }

        // 设置用户名到请求头
        String userName = headers.get(SecurityConstants.DETAILS_USERNAME);
        if (StringUtils.isNotEmpty(userName)) {
            requestTemplate.header(SecurityConstants.DETAILS_USERNAME, userName);
        }

        // 设置认证信息到请求头
        String authentication = headers.get(SecurityConstants.AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(authentication)) {
            requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER, authentication);
        }

        // 配置客户端IP
        requestTemplate.header(HttpConstant.X_FORWARDED_FOR, IpUtils.getIpAddr(ServletUtils.getRequest()));
    }

}