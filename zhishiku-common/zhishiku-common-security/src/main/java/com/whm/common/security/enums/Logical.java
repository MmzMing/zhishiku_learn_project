package com.whm.common.security.enums;

/**
 * 权限注解的验证模式
 *
 * @author : 吴华明
 * @since 2025-12-14  16:34
 */
public enum Logical {
    /**
     * 必须具有所有的元素
     */
    AND,

    /**
     * 只需具有其中一个元素
     */
    OR
}

