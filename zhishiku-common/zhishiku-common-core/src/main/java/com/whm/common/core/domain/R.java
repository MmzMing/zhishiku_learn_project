package com.whm.common.core.domain;

import com.whm.common.core.constant.Constants;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author 吴华明
 */
@Data
public class R <T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 成功
     */
    public static final int SUCCESS = Constants.SUCCESS;
    /**
     * 警告
     */
    public static final int WARNING = Constants.WARNING;
    /**
     * 失败
     */
    public static final int FAIL = Constants.FAIL;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> warn() {
        return restResult(null, WARNING, "数据警告");
    }

    public static <T> R<T> warn(T data) {
        return restResult(data, WARNING, "数据警告");
    }

    public static <T> R<T> warn(T data, String msg) {
        return restResult(data, WARNING, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
