package com.whm.gateway.handler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.whm.common.core.enums.GatewayErrorCodeEnum;
import com.whm.common.core.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 * 该异常处理器会比其他默认的异常处理器优先执行。
 *
 * @author 吴华明
 * @date 2025/9/11 18:14
 */
@Order(-1)
@Slf4j
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    /**
     * 处理网关异常的回调方法
     *
     * @param exchange 当前的服务器Web交换对象，包含请求和响应信息
     * @param ex       抛出的异常对象
     * @return Mono<Void> 异步处理结果，表示响应写入完成
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        // 若为Sentinel限流异常，交给SentinelFallbackHandler处理
        if (ex instanceof BlockException) {
            return Mono.error(ex);
        }

        // 如果响应已经提交，则直接返回异常
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 根据异常类型设置相应的错误消息
        String msg;
        if (ex instanceof NotFoundException) {
            //当请求的服务未找到时触发
            msg = GatewayErrorCodeEnum.SERVICE_NOT_FOUND.getErrorMsg();
        } else if (ex instanceof ResponseStatusException) {
            //当响应状态异常时触发
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            msg = responseStatusException.getMessage();
        } else {
            msg = GatewayErrorCodeEnum.UNKNOWN_ERROR.getErrorMsg();
        }

        // 记录异常日志
        log.error("[网关异常处理] 请求路径:{}, 异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());

        // 写入响应并返回处理结果
        return ServletUtils.webFluxResponseWriter(response, msg);
    }

}
