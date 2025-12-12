package com.whm.gateway.service.impl;


import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.whm.common.core.constant.TokenConstants;
import com.whm.common.core.domain.web.AjaxResult;
import com.whm.common.core.enums.GatewayErrorCodeEnum;
import com.whm.common.core.exception.CaptchaException;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.core.utils.uuid.UUID;
import com.whm.common.redis.service.RedisService;
import com.whm.gateway.config.CaptchaConfigInfo;
import com.whm.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 * 提供验证码的生成和校验功能
 *
 * @author : 吴华明
 * @since 2025-12-12  19:41
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {


    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CaptchaConfigInfo captchaConfigInfo;

    /**
     * 生成验证码
     *
     * @return 验证码信息
     * @throws CaptchaException 验证码异常
     */
    @Override
    public AjaxResult createCaptcha() throws CaptchaException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaOnOff = captchaConfigInfo.getEnabled();
        ajax.put("captchaOnOff", captchaOnOff);
        if (!captchaOnOff) {
            return ajax;
        }

        // 保存验证码信息
        String uuid = UUID.fastUUID().toString(true);
        String verifyKey = TokenConstants.CAPTCHA_CODE_KEY + uuid;
        String capStr, code;
        BufferedImage image;

        String captchaType = captchaConfigInfo.getType();
        // 生成验证码 并且 验证码图片转Base64
        if (TokenConstants.MATH.equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if (TokenConstants.CHAR.equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        } else {
            throw new CaptchaException("不支持的验证码类型: " + captchaType);
        }

        redisService.setCacheObject(verifyKey, code, TokenConstants.CAPTCHA_INDATE, TimeUnit.MINUTES);
        // 转换流信息写出
        try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", os);
            ajax.put("uuid", uuid);
            ajax.put("img", Base64.encode(os.toByteArray()));
            return ajax;
        } catch (IOException e) {
            throw new CaptchaException("生成验证码图片失败: " + e.getMessage());
        }
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @throws CaptchaException 验证码异常
     */
    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaException {
        if (StringUtils.isEmpty(code)) {
            throw new CaptchaException(GatewayErrorCodeEnum.AUTH_LOGIN_CAPTCHA_MISSING.getErrorMsg());
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new CaptchaException(GatewayErrorCodeEnum.AUTH_LOGIN_CAPTCHA_EXPIRED.getErrorMsg());
        }
        String verifyKey = TokenConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);

        // 立即删除验证码，防止重复使用
        redisService.deleteObject(verifyKey);
        if (StringUtils.isEmpty(captcha) || !code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException(GatewayErrorCodeEnum.AUTH_LOGIN_CAPTCHA_ERROR.getErrorMsg());
        }
    }
}
