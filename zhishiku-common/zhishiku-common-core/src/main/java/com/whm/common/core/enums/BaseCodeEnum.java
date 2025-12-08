package com.whm.common.core.enums;

import com.whm.common.core.exception.service.IBusinessException;

/**
 * 公共返回码
 *
 * @author 吴华明
 */
public enum BaseCodeEnum implements IBusinessException {

    /**
     * SUCCESS
     */
    SUCCESS(0, "Success"),
    FAILED(1, "系统繁忙,请稍后再试"),
    REMOTE_SERVICE_NULL(2, "远程服务不存在"),
    REQUEST_NULL(3, "请求数据为空或格式错误"),
    SAVE_UPDATE_FAILED(4, "保存/更新失败"),
    QUERY_RESULT_IS_EMPTY(5, "查询结果为空"),

    ;

    private Integer code;
    private String msg;

    BaseCodeEnum(Integer code, String msg) {
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

    @Override
    public String getMsg() {
        return msg;

    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseCodeEnum{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}