package com.whm.common.core.exception.service;

import cn.hutool.core.util.StrUtil;

/**
 * 务异常公共类
 *
 * @author 吴华明
 */
public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String messageFormat, Object... args) {
        this.message = String.format(messageFormat, args);
    }

    public ServiceException(String messageFormat, Integer code, String... args) {
        this(messageFormat, args);
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    public ServiceException(IBusinessException errorCodeEnum, Object[] objects) {
        this.code = errorCodeEnum.getCode();
        if (null != errorCodeEnum.getMsg() && null != objects) {
            this.message = StrUtil.format(errorCodeEnum.getMsg(), objects);
        } else {
            this.message = errorCodeEnum.getMsg();
        }
    }
}
