package com.whm.common.core.constant;


/**
 * 验证常用
 *
 * @author : 吴华明
 * @since 2025-12-12  18:14
 */
public class TokenConstants {
    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥 TODO 需要配置
     */
    public final static String SECRET = "XXXXXX";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes_key:";


    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_INDATE = 2;

    /**
     * 验证码类型
     */
    public static final String MATH = "math";

    public static final String CHAR = "char";

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;

    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;

    public static final int PASSWORD_MAX_LENGTH = 20;
}