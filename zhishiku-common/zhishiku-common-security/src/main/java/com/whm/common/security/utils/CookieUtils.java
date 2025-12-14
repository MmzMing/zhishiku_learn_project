package com.whm.common.security.utils;


import com.whm.common.core.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie 工具类
 *
 * @author : 吴华明
 * @since 2025-12-14  15:21
 */
public class CookieUtils {
    public CookieUtils() {
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(null != path ? path : "/");
        if (!StringUtils.isEmpty(domain)) {
            cookie.setDomain(domain);
        }

        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie uid = new Cookie(name, (String) null);
        uid.setPath("/");
        uid.setMaxAge(0);
        response.addCookie(uid);
    }

    public static void removeCookie(HttpServletResponse response, String name, String path) {
        Cookie uid = new Cookie(name, (String) null);
        uid.setPath(path);
        uid.setMaxAge(0);
        response.addCookie(uid);
    }

    public static String getUid(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap(4);
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}