package com.whm.gateway.service;


import com.whm.common.core.domain.web.AjaxResult;
import com.whm.common.core.exception.CaptchaException;

import java.io.IOException;

/**
 * 验证码处理
 *
 * @author : 吴华明
 * @since 2025-12-12  19:39
 */
public interface ValidateCodeService {
    /**
     * 生成验证码
     *
     * @return 结果
     * @throws IOException      异常
     * @throws CaptchaException 异常
     */
     AjaxResult createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     *
     * @param key   key
     * @param value v
     * @throws CaptchaException 异常
     */
     void checkCaptcha(String key, String value) throws CaptchaException;
}
