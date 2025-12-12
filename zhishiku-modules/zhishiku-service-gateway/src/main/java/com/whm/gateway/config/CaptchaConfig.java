package com.whm.gateway.config;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.google.code.kaptcha.Constants.*;

/**
 * 验证码生成配置（支持多类型）
 *
 * @author : 吴华明
 * @since 2025-12-12  16:08
 */
@Configuration
public class CaptchaConfig {
    /**
     * 获取验证码生成器Bean实例
     * 该方法用于创建并配置DefaultKaptcha验证码生成器，通过设置各种属性来自定义验证码的外观和行为。
     * 包括边框、颜色、尺寸、字体、字符长度以及图像效果等配置项。
     *
     * @return 配置完成的DefaultKaptcha实例，用于生成图形验证码
     */
    @Bean(name = "captchaProducer")
    public DefaultKaptcha getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        // 验证码基础样式配置
        properties.setProperty(KAPTCHA_BORDER, "yes");// 是否有边框 默认为true 我们可以自己设置yes，no
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");// 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");// 验证码图片宽度 默认为160
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");// 验证码图片高度 默认为60
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "38");// 设置验证码字体大小为38像素

        // 验证码会话配置和字符设置
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");// 设置在Session中存储验证码的键名
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4"); // 设置验证码字符长度为6位
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier"); // 设置可用的字体列表

        // 验证码图像效果配置
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy"); // 设置使用阴影扭曲效果

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }


    /**
     * 创建并配置用于数学验证码生成的Kaptcha Bean实例。
     * <p>
     * 该方法通过设置一系列属性来自定义验证码的外观和行为，
     * 包括边框、颜色、字体、尺寸、文本生成器等。
     * </p>
     *
     * @return 配置完成的DefaultKaptcha实例，用于生成数学类型验证码
     */
    @Bean(name = "captchaProducerMath")
    public DefaultKaptcha getKaptchaBeanMath() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        // 验证码基本样式与安全相关配置
        properties.setProperty(KAPTCHA_BORDER, "yes");                         // 设置是否有边框
        properties.setProperty(KAPTCHA_BORDER_COLOR, "105,179,90");           // 设置边框颜色
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");      // 设置验证码文字颜色
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");                   // 设置图片宽度
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");                   // 设置图片高度
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "35");         // 设置字体大小
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCodeMath");// 设置session中存储key

        // 文本内容及格式配置
        properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.whm.gateway.config.KaptchaTextCreator"); // 自定义文本生成器
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "3");         // 字符间距
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");        // 验证码字符长度
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier"); // 字体名称列表

        // 干扰与视觉效果配置
        properties.setProperty(KAPTCHA_NOISE_COLOR, "white");                 // 噪点颜色
        properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise"); // 无噪点干扰
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy"); // 使用阴影样式

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
