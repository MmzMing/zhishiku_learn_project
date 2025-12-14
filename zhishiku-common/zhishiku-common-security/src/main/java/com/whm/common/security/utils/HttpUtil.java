package com.whm.common.security.utils;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.whm.common.core.constant.HttpConstant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * Http工具类
 *
 * @author : 吴华明
 * @since 2025-12-14  15:22
 */
@Slf4j
public class HttpUtil {
    private HttpUtil() {
    }

    public static String encodeUrl(String url, String encodeCharset) {
        if (url != null && url.length() > 0) {
            try {
                return encodeCharset != null && encodeCharset.length() > 0 ? URLEncoder.encode(url, encodeCharset) : URLEncoder.encode(url, Constants.UTF_8);
            } catch (UnsupportedEncodingException var3) {
                log.error("编码url出错!", var3);
                return null;
            }
        } else {
            return null;
        }
    }

    public static String decodeUrl(String url, String decodeCharset) {
        if (url != null && url.length() > 0) {
            try {
                return decodeCharset != null && decodeCharset.length() > 0 ? URLDecoder.decode(url, decodeCharset) : URLDecoder.decode(url, Constants.UTF_8);
            } catch (UnsupportedEncodingException var3) {
                log.error("解码url出错!", var3);
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getRequestFullPath(HttpServletRequest request) {
        String result = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
        String queryurl = request.getQueryString();
        if (null != queryurl) {
            result = result + "?" + queryurl;
        }

        return result;
    }

    public static String getRequestFullPathNoParam(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
    }

    public static String getRequestContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || HttpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || HttpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || HttpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (HttpConstant.IPV4.equals(ipAddress) || HttpConstant.IPV6.equals(ipAddress)) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException var4) {
                    log.error(var4.getMessage());
                }
            }
        }

        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(StrUtil.C_COMMA) > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(StrUtil.C_COMMA));
        }

        return ipAddress;
    }
}
