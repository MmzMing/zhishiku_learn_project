package com.whm.auth.controller;

import com.whm.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author 吴华明
 */
@Api(tags = "测试")
@RestController
public class HelloController {
    @Value("${k1}")
    private String k1;

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping("login")
    public R<String> login() {
        // 获取登录token
        return R.ok(k1);
    }
}