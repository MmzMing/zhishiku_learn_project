package com.whm.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author : 吴华明
 * @since 2025-12-14  16:07
 */
@Getter
@AllArgsConstructor
public enum MenuEnum {
    DIR("M", "目录"),
    MENU("C", "菜单"),
    BUTTON("F", "按钮");
    private final String code;
    private final String info;
}
