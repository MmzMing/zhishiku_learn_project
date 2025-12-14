package com.whm.system.api;

import com.whm.common.core.constant.SecurityConstants;
import com.whm.common.core.constant.ServiceNameConstants;
import com.whm.common.core.domain.R;
import com.whm.system.api.domain.vo.LoginUserVo;
import com.whm.system.api.domain.vo.SysUserVo;
import com.whm.system.api.factory.SysUserClientFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户远程调用接口
 *
 * @author : 吴华明
 * @since 2025-12-14  13:40
 */
@FeignClient(contextId = "SysUserClient",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        //本地测试时候加上自己的 ip，或者指定 nacos 配置的 ${media.service.url}
        url = "http://192.168.1.5:40020",
        fallbackFactory = SysUserClientFactory.class
)
public interface SysUserClient {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    R<LoginUserVo> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    R<Boolean> registerUserInfo(@RequestBody SysUserVo sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
