package com.whm.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 网关错误码枚举
 *
 * @author 吴华明
 * @since 2025-12-12  16:03
 */
@Getter
@AllArgsConstructor
public enum GatewayErrorCodeEnum {


    // ======================== 400xxx - 请求参数错误 ========================
    PARAM_MISSING(400, "400001", "缺少必要请求参数，请检查并补充完整请求数据"),
    PARAM_FORMAT_ERROR(400, "400002", "请求参数格式不正确，请参照API文档检查数据格式"),
    PARAM_VALIDATE_FAILED(400, "400003", "请求参数验证失败，请检查参数值是否符合业务规则"),

    // ======================== 401xxx - 认证错误 ========================
// 当前 Token 相关错误文本不够统一
    AUTH_TOKEN_MISSING(401, "401001", "Token不能为空，请先登录"),
    AUTH_TOKEN_EXPIRED(401, "401002", "Token已过期，请重新登录"),
    AUTH_TOKEN_INVALID(401, "401003", "Token非法无效，请重新登录"),
    AUTH_TOKEN_PARSE_ERROR(401, "401004", "Token验证失败，请检查格式"),
    AUTH_TOKEN_BLACK_FOUND(401, "401005", "Token已被拉黑，请联系管理员"),
    AUTH_LOGIN_EXPIRED(401, "401006", "登录状态已过期，请重新登录"),

    // ======================== 403xxx - 授权/权限错误 ========================
    AUTH_NO_PERMISSION(403, "403001", "当前账号无此接口访问权限，请联系管理员进行授权"),
    AUTH_IP_LIMIT(403, "403002", "当前IP地址不在访问白名单内，请联系管理员添加白名单"),
    AUTH_APPID_INVALID(403, "403003", "应用ID无效或已被禁用，请检查应用配置信息"),

    // ======================== 404xxx - 路由/服务不存在 ========================
    ROUTE_NOT_FOUND(404, "404001", "未匹配到任何路由规则，请检查请求路径或路由配置"),
    SERVICE_NOT_FOUND(404, "404002", "未找到服务，请检查服务状态"),

    // ======================== 429xxx - 限流/熔断错误 ========================
    FLOW_LIMIT(429, "429001", "当前请求频率超出接口限流阈值，请稍后重试"),
    SERVICE_CIRCUIT_BREAKER(429, "429002", "目标服务异常率过高，已触发熔断，请稍后重试"),
    CONCURRENT_LIMIT(429, "429003", "当前接口并发请求数超出限制，请稍后重试"),

    // ======================== 500xxx - 网关内部/服务调用错误 ========================
    GATEWAY_CONFIG_ERROR(500, "500001", "网关配置异常，请联系管理员检查配置"),
    GATEWAY_INIT_FAILED(500, "500002", "网关核心组件初始化失败，请重启网关服务"),
    GATEWAY_FILTER_ERROR(500, "500003", "网关过滤器执行异常，请检查过滤器逻辑"),
    GATEWAY_CODEC_ERROR(500, "500004", "请求/响应数据编解码失败，请检查数据格式"),
    SERVICE_RESPONSE_ERROR(500, "500005", "目标服务返回异常结果，请联系服务提供方"),
    SERVICE_BUSINESS_ERROR(500, "500006", "目标服务返回业务异常：%s"),
    SERVICE_CONNECTION_REFUSED(500, "500007", "无法连接到目标服务，请检查服务是否在线且网络可达"),
    ROUTE_CONFIG_ERROR(500, "500008", "路由规则配置无效，请联系管理员检查路由配置"),
    ROUTE_FORWARD_FAILED(500, "500009", "请求转发到目标服务失败，请检查路由地址是否正确"),
    UNKNOWN_ERROR(500, "500999", "网关处理请求时发生未知异常，请联系管理员排查"),

    // ======================== 504xxx - 超时错误 ========================
    REQUEST_TIMEOUT(504, "504001", "网关接收请求超时，请检查网络或缩短请求耗时"),
    SERVICE_RESPONSE_TIMEOUT(504, "504002", "目标服务响应超时，请检查服务性能或增加超时时间"),
    CONNECTION_TIMEOUT(504, "504003", "网关与目标服务建立连接超时，请检查网络状态"),

    // ======================== 405xxx - 不支持的请求方法 ========================
    UNSUPPORTED_METHOD(405, "405001", "当前接口不支持该请求方法，请检查请求方式");

    /**
     * HTTP状态码（一级码）
     */
    private final int httpStatus;
    /**
     * 细分错误码（二级码：HTTP状态码+3位序号）
     */
    private final String errorCode;
    /**
     * 错误描述（支持占位符格式化）
     */
    private final String errorMsg;

    /**
     * 根据错误码快速查找枚举项
     *
     * @param errorCode 细分错误码
     * @return 匹配的枚举项（无匹配返回null）
     */
    public static GatewayErrorCodeEnum getByErrorCode(String errorCode) {
        for (GatewayErrorCodeEnum error : values()) {
            if (StringUtils.equals(error.getErrorCode(), errorCode)) {
                return error;
            }
        }
        return null;
    }

    /**
     * 根据 HTTP 状态码查找所有匹配的枚举项
     *
     * @param httpStatus HTTP 状态码
     * @return 匹配的枚举项列表
     */
    public static List<GatewayErrorCodeEnum> getByHttpStatus(int httpStatus) {
        return Arrays.stream(values())
                .filter(error -> error.getHttpStatus() == httpStatus)
                .collect(Collectors.toList());
    }

}
