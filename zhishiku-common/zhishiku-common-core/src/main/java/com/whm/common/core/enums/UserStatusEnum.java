package com.whm.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author : 吴华明
 * @since 2025-12-14  16:02
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    OK("1", "正常"),
    DISABLE("0", "停用"),
    DELETED("2", "删除");
    private final String code;
    private final String info;

}
