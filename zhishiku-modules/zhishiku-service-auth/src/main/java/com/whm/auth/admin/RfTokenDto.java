package com.whm.auth.admin;


import lombok.Data;

/**
 * 刷新令牌传输对象
 *
 * @author : 吴华明
 * @since 2025-12-14  17:37
 */
@Data
public class RfTokenDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 随机数
     */
    private int nonce;

    /**
     * 签名
     */
    private String sign;

}

