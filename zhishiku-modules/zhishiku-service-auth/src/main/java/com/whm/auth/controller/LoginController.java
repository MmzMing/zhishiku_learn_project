package com.whm.auth.controller;


import com.whm.auth.admin.LoginDto;
import com.whm.auth.admin.RfTokenDto;
import com.whm.auth.enums.LoginMode;
import com.whm.common.core.domain.R;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.core.utils.check.JwtUtils;
import com.whm.common.security.service.TokenService;
import com.whm.common.security.utils.AuthUtil;
import com.whm.common.security.utils.SecurityUtils;
import com.whm.system.api.domain.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 *
 * @author : 吴华明
 * @since 2025-12-14  13:23
 */
@Slf4j
@Api(tags = "认证服务_用户登录")
@RestController
public class LoginController {


    @Autowired
    private TokenService tokenService;


    public static AuthUtil authUtil = new AuthUtil();

    /**
     * 用户登录接口
     *
     * @param loginDate 登录数据传输对象，包含用户名、密码和登录类型
     * @return 返回登录结果，包含登录token信息
     */
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("login")
    public R<?> login(@RequestBody LoginDto loginDate) {
        LoginMode mode = LoginMode.getByMode(loginDate.getLoginType());
        if (mode == null) {
            log.error("登录方式不存在");
            return R.fail("登录方式不存在");
        }
        // 用户登录
        LoginUserVo userInfo = mode.getAuthService().login(loginDate.getUsername(), loginDate.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    /**
     * 用户注册接口
     *
     * @param loginDate 登录数据传输对象，包含用户名、密码和登录类型
     * @return 注册结果响应对象，成功返回R.ok()，失败返回R.fail()
     */
    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping("register")
    public R register(@RequestBody LoginDto loginDate) {
        // 根据登录类型获取对应的登录模式
        LoginMode mode = LoginMode.getByMode(loginDate.getLoginType());
        if (mode == null) {
            return R.fail("注册方式不存在");
        }
        // 用户注册
        mode.getAuthService().register(loginDate.getUsername(), loginDate.getPassword());
        return R.ok();
    }

    /**
     * 用户退出登录接口
     *
     * @param request HTTP请求对象，用于获取请求头中的token信息
     * @return 统一响应结果对象，表示操作成功
     */
    @DeleteMapping("logout")
    public R logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            authUtil.logoutByToken(token);
            // 记录用户退出日志
            LoginMode.getByMode("pw").getAuthService().logout(username);
        }
        return R.ok();
    }

    /**
     * 刷新用户令牌有效期 TODO 等写SSO
     *
     * @param request HTTP请求对象，用于获取当前登录用户信息
     * @return R 响应结果对象，操作成功返回R.ok()
     */
    @PostMapping("refresh")
    public R refresh(HttpServletRequest request) {
        return R.ok();
    }

    /**
     * 设置Cookie接口  TODO 等写SSO
     * 通过GET请求访问/setCookie.do路径时调用此方法
     *
     * @param lsmyToken 用户令牌参数，用于设置SSO Cookie
     */
    @GetMapping("setCookie")
    public void setCookie(String lsmyToken) {

    }

    /**
     * 获取 Web 刷新令牌令牌  TODO 等写SSO
     *
     * @param rfTokenDto WebRfTokenBody对象，包含获取刷新令牌所需的信息
     * @return R<?> 统一响应结果，包含获取到的Web刷新令牌令牌数据
     */
    @PostMapping("getWebRfTokenToken")
    public R<?> getWebRfTokenToken(@RequestBody RfTokenDto rfTokenDto) {
        return R.ok();
    }

}