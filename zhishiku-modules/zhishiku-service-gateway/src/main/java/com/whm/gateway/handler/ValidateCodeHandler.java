package com.whm.gateway.handler;


import com.whm.common.core.domain.web.AjaxResult;
import com.whm.common.core.exception.CaptchaException;
import com.whm.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 验证码 处理
 *
 * @author : 吴华明
 * @since 2025-12-12  19:36
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {
    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 处理服务器请求，生成验证码并返回响应结果
     *
     * @param serverRequest 服务器请求对象，包含客户端请求信息
     * @return Mono<ServerResponse> 包含验证码信息的服务器响应对象
     */
    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        AjaxResult ajax;
        try {
            // 生成验证码
            ajax = validateCodeService.createCaptcha();
        } catch (CaptchaException | IOException e) {
            // 验证码生成异常时返回错误信息
            return Mono.error(e);
        }
        // 构造成功响应，返回验证码数据
        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ajax));
    }

}
