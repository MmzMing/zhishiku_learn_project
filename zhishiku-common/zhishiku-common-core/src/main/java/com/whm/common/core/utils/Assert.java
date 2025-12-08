package com.whm.common.core.utils;


import com.whm.common.core.exception.service.IBusinessException;
import com.whm.common.core.exception.service.ServiceException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.Nullable;

/**
 * 抛业务异常的断言工具
 *
 * @author 吴华明
 * @date 2025/9/16 19:44
 */
public class Assert {

    private Assert() {
        throw new IllegalStateException("Utility class");
    }

    public static void isTrue(boolean expression, IBusinessException errorCodeEnum) {
        isTrue(expression, errorCodeEnum, null);
    }

    public static void isTrue(boolean expression, IBusinessException errorCodeEnum, Object... objects) {
        if (!expression) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void isNotTrue(boolean expression, IBusinessException errorCodeEnum, Object... objects) {
        if (expression) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void isNull(@Nullable Object object, IBusinessException errorCodeEnum) {
        isNull(object, errorCodeEnum, null);
    }

    public static void isNotNull(@Nullable Object object, IBusinessException errorCodeEnum) {
        if (null == object) {
            throw new ServiceException(errorCodeEnum, null);
        }
    }

    private static void isNull(@Nullable Object object, IBusinessException errorCodeEnum, Object... objects) {
        if (null != object) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void notNull(@Nullable Object object, IBusinessException errorCodeEnum) {
        notNull(object, errorCodeEnum, null);
    }

    public static void notNull(@Nullable Object object, IBusinessException errorCodeEnum, Object... objects) {
        if (null == object) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void notEmpty(@Nullable Object object, IBusinessException errorCodeEnum) {
        notEmpty(object, errorCodeEnum, null);
    }

    public static void notEmpty(@Nullable Object object, IBusinessException errorCodeEnum, Object... objects) {
        if (ObjectUtils.isEmpty(object)) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void isEmpty(@Nullable Object object, IBusinessException errorCodeEnum) {
        isEmpty(object, errorCodeEnum, null);
    }

    public static void isEquals(@Nullable Object obj1, Object obj2, IBusinessException errorCodeEnum) {
        isEquals(obj1, obj2, errorCodeEnum, null);
    }

    public static void isEquals(@Nullable Object obj1, Object obj2, IBusinessException errorCodeEnum, Object... objects) {
        if (ObjectUtils.notEqual(obj1, obj2)) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void isNotEquals(@Nullable Object obj1, Object obj2, IBusinessException errorCodeEnum) {
        isNotEquals(obj1, obj2, errorCodeEnum, null);
    }

    public static void isNotEquals(@Nullable Object obj1, Object obj2, IBusinessException errorCodeEnum, Object... objects) {
        if (!ObjectUtils.notEqual(obj1, obj2)) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void isEmpty(@Nullable Object object, IBusinessException errorCodeEnum, Object... objects) {
        if (ObjectUtils.isNotEmpty(object)) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

    public static void isNotEmpty(@Nullable Object object, IBusinessException errorCodeEnum, Object... objects) {
        if (ObjectUtils.isEmpty(object)) {
            throw new ServiceException(errorCodeEnum, objects);
        }
    }

}

