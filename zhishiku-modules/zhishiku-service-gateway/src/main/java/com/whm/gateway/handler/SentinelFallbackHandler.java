package com.whm.gateway.handler;


import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.whm.common.core.utils.ServletUtils;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 自定义限流异常处理
 * SentinelFallbackHandler 类用于处理限流异常，当请求超过限流阈值时，返回自定义的错误信息给客户端。
 *
 * @author 吴华明
 * @date 2025/9/11 14:39
 */
public class SentinelFallbackHandler implements WebExceptionHandler {
    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), "请求超过最大数，请稍候再试");
    }

    /**
     * 该方法是 WebExceptionHandler 接口的实现，用于处理异常。
     * 首先检查响应是否已经提交，如果是则返回异常。
     * 然后检查异常是否为 BlockException，如果不是则返回异常。
     * 如果是 BlockException，则调用 handleBlockedRequest 方法处理限流请求，并将响应写回客户端
     *
     * @param exchange 请求
     * @param ex 异常
     * @return 响应
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }
}
