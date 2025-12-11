package com.whm.common.core.constant;


/**
* Security登录授权信息
* 
* @author : 吴华明
* @since 2025-11-27  19:45
*/
public class SecurityConstants  {
    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段 job_code 工号
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 用户名字段
     */
    public static final String DETAILS_NICKNAME = "nickName";

    /**
     * 授权信息字段
     */
    public static final String AUTHORIZATION_HEADER = "authorization";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";

    /**
     * 用户标识
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * 用户OA账号
     */
    public static final String USER_ACCOUNT = "user_account";


    /**
     * otwb 时间戳字段名称
     */
    public static final String X_OTWB_SERVICE_TIMESTAMP = "X-Otwb-Service-Timestamp";
    /**
     * 签名字段名称
     */
    public static final String X_OTWB_SERVICE_SIGNATURE = "X-Otwb-Service-Signature";
}