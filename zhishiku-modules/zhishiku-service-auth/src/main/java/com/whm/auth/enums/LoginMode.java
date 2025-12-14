package com.whm.auth.enums;

import cn.hutool.extra.spring.SpringUtil;
import com.whm.auth.service.AuthService;
import com.whm.system.api.domain.vo.LoginUserVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录方式枚举
 *
 * @author : 吴华明
 * @since 2025-12-14  16:58
 */
@Getter
@AllArgsConstructor
public enum LoginMode {


    PASSWORD("pw", "密码登录", "passwordAuthServiceImpl"),
    WECHAT("wc", "微信登录", "weChatAuthServiceImpl"),
    PHONE("p", "手机登录", "phoneAuthServiceImpl"),
    DING_TALK("dt", "钉钉登录", "dingTalkAuthServiceImpl");

    private final String mode;
    private final String desc;
    private final String authServiceBeanName; // 存储Bean名称
    public static LoginMode getByMode(String mode) {
        for (LoginMode value : values()) {
            if (value.mode.equals(mode)) {
                return value;
            }
        }
        return null;
    }
    // 延迟获取AuthService实例（使用时才从Spring容器获取）
    public AuthService getAuthService() {
        return SpringUtil.getBean(this.authServiceBeanName, AuthService.class);
    }

}
