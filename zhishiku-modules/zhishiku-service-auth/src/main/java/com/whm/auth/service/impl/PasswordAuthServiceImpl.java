package com.whm.auth.service.impl;


import com.whm.auth.service.AuthService;
import com.whm.common.core.constant.Constants;
import com.whm.common.core.constant.SecurityConstants;
import com.whm.common.core.constant.TokenConstants;
import com.whm.common.core.domain.R;
import com.whm.common.core.enums.UserStatusEnum;
import com.whm.common.core.exception.service.ServiceException;
import com.whm.common.core.utils.ServletUtils;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.core.utils.ip.IpUtils;
import com.whm.common.security.utils.DESUtil;
import com.whm.common.security.utils.SecurityUtils;
import com.whm.system.api.SysOperLogClient;
import com.whm.system.api.SysUserClient;
import com.whm.system.api.domain.dto.SysLoginInforDto;
import com.whm.system.api.domain.vo.LoginUserVo;
import com.whm.system.api.domain.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 图形密码登录
 *
 * @author : 吴华明
 * @since 2025-12-14  13:16
 */
@Service
public class PasswordAuthServiceImpl implements AuthService {
    @Autowired
    private SysOperLogClient sysOperLogClient;

    @Autowired
    private SysUserClient sysUserClient;

    /**
     * 登录
     */
    public LoginUserVo login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }

        // 查询用户信息
        R<LoginUserVo> userResult = sysUserClient.getUserInfo(username, SecurityConstants.INNER);

        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        if (StringUtils.isNull(userResult.getData())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        LoginUserVo userInfo = userResult.getData();
        SysUserVo user = userInfo.getSysUser();
        if (UserStatusEnum.DELETED.getCode().equals(user.getDelFlag())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        if (!DESUtil.matchesPassword(password, user.getPassword())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码错误");
            throw new ServiceException("用户不存在/密码错误");
        }
        recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public void logout(String loginName) {
        recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }


    /**
     * 用户注册功能
     *
     * @param username 用户名
     * @param password 密码
     */
    public void register(String username, String password) {
        // 验证用户名和密码格式
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < TokenConstants.USERNAME_MIN_LENGTH
                || username.length() > TokenConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < TokenConstants.PASSWORD_MIN_LENGTH
                || password.length() > TokenConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        SysUserVo sysUser = new SysUserVo();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        R<?> registerResult = sysUserClient.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode()) {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLogininfor(username, Constants.REGISTER, "注册成功");
    }


    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    public void recordLogininfor(String username, String status, String message) {
        SysLoginInforDto loginInfor = new SysLoginInforDto();
        loginInfor.setUserName(username);
        loginInfor.setIpAddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        loginInfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            loginInfor.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            loginInfor.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        sysOperLogClient.saveLoginInfor(loginInfor, SecurityConstants.INNER);
    }
}
