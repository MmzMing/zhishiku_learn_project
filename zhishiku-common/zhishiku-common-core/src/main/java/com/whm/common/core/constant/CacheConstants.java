package com.whm.common.core.constant;


/**
 * 缓存的key 常量
 *
 * @author : 吴华明
 * @since 2025-12-12  20:29
 */
public class CacheConstants {

    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 720;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public final static long REFRESH_TIME = 120;


    public static final long MILLIS_SECOND = 1000;

    public static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "whm:login_tokens:";
}
