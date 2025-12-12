package com.whm.common.core.exception;


/**
 * 验证码错误异常类
 *
 * @author : 吴华明
 * @since 2025-12-12  19:40
 */
public class CaptchaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CaptchaException(String msg) {
        super(msg);
    }
}

