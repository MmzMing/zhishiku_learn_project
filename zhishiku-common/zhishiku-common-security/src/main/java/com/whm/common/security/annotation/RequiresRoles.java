package com.whm.common.security.annotation;

import com.whm.common.security.enums.Logical;

/**
 * 必须具有指定 角色的标识 才能进入该方法
 *
 * @author : 吴华明
 * @since 2025-12-14  16:35
 */
public @interface RequiresRoles {
    /**
     * 需要校验的角色标识
     */
    String[] value() default {};

    /**
     * 验证逻辑：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;
}
