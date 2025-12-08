package com.whm.common.core.exception.service;

/**
 * 异常公共
 *
 * @author 吴华明
 */
public interface IBusinessException {

    /**
     * getCode
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * getMsg
     *
     * @return String
     */
    String getMsg();
}

