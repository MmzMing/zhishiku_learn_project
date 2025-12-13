package com.whm.gateway.handler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.whm.common.core.enums.GatewayErrorCodeEnum;
import com.whm.common.core.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 * 该异常处理器会比其他默认的异常处理器优先执行。
 *
 * @author 吴华明
 * @since 2025/9/11 18:14
 */
@Order(-1)
@Slf4j
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        // 1. 检查响应是否已提交（防止重复写入响应）
        if (response.isCommitted()) {
            log.warn("响应已提交，跳过异常处理，异常类型: {}", ex.getClass().getSimpleName());
            return Mono.error(ex);
        }

        // 2. 处理Sentinel限流异常（交由Sentinel降级处理器处理）
        if (isBlockException(ex)) {
            log.debug("Sentinel限流异常，交由SentinelFallbackHandler处理");
            return Mono.error(ex);
        }

        // 3. 解析异常信息
        GatewayErrorCodeEnum errorCode = resolveErrorCode(ex);
        String errorMsg = buildErrorMessage(errorCode, ex);
        // 记录异常日志
        log.error("[网关异常处理] 请求路径:{}, 异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());

        // 写入响应并返回处理结果
        return ServletUtils.webFluxResponseWriter(response, errorMsg);
    }

    /**
     * 判断是否为Sentinel限流异常
     * 包括BlockException及其所有子类
     */
    private boolean isBlockException(Throwable ex) {
        return ex instanceof BlockException ||
                (ex.getCause() != null && ex.getCause() instanceof BlockException);
    }

    /**
     * 解析异常类型，映射到对应的错误码枚举
     */
    private GatewayErrorCodeEnum resolveErrorCode(Throwable ex) {
        // 检查异常链中是否存在NotFoundException
        if (ex instanceof NotFoundException ||
                (ex.getCause() != null && ex.getCause() instanceof NotFoundException)) {
            return GatewayErrorCodeEnum.SERVICE_NOT_FOUND;
        }

        // 处理ResponseStatusException及其子类
        ResponseStatusException statusEx = extractResponseStatusException(ex);
        if (statusEx != null) {
            HttpStatus status = statusEx.getStatus();
            switch (status) {
                case NOT_FOUND:
                    return GatewayErrorCodeEnum.SERVICE_NOT_FOUND;
                case INTERNAL_SERVER_ERROR:
                    return GatewayErrorCodeEnum.SERVICE_INTERNAL_ERROR;
                case BAD_REQUEST:
                case UNAUTHORIZED:
                case FORBIDDEN:
                case SERVICE_UNAVAILABLE:
                    return GatewayErrorCodeEnum.SERVICE_RESPONSE_ERROR;
                default:
                    return GatewayErrorCodeEnum.UNKNOWN_ERROR;
            }
        }

        return GatewayErrorCodeEnum.UNKNOWN_ERROR;
    }

    /**
     * 从异常链中提取 ResponseStatusException
     */
    private ResponseStatusException extractResponseStatusException(Throwable ex) {
        Throwable current = ex;
        while (current != null) {
            if (current instanceof ResponseStatusException) {
                return (ResponseStatusException) current;
            }
            current = current.getCause();
        }
        return null;
    }

    /**
     * 构建错误消息
     */
    private String buildErrorMessage(GatewayErrorCodeEnum errorCode, Throwable ex) {
        String baseMsg = errorCode.getErrorMsg();

        // 对于响应错误，包含原始异常消息
        if (errorCode == GatewayErrorCodeEnum.SERVICE_RESPONSE_ERROR && ex != null) {
            ResponseStatusException statusEx = extractResponseStatusException(ex);
            if (statusEx != null) {
                return String.format(baseMsg, statusEx.getMessage());
            }
        }

        return baseMsg;
    }
}
