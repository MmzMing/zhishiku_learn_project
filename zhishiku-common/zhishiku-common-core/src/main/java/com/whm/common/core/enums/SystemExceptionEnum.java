package com.whm.common.core.enums;

import com.whm.common.core.exception.service.IBusinessException;

/**
 * 系统异常返回码
 *
 * @author 吴华明
 */
public enum SystemExceptionEnum implements IBusinessException {

    //购物车30003
    OA_QUERY_USER_IS_NULL(30003000, "查询OA用户:{} 信息为空！");

    private Integer code;
    private String msg;

    SystemExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private void setCode(Integer code) {
        this.code = code;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;

    }
}
