package com.whm.system.api.factory;


import com.whm.common.core.domain.R;
import com.whm.system.api.SysUserClient;
import com.whm.system.api.domain.vo.LoginUserVo;
import com.whm.system.api.domain.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * SysUserClient 熔断降级
 *
 * @author : 吴华明
 * @since 2025-12-14  13:42
 */
@Slf4j
@Component
public class SysUserClientFactory implements FallbackFactory<SysUserClient> {
    @Override
    public SysUserClient create(Throwable throwable) {
        log.error("远程调用【用户服务调用失败】失败:{}", throwable.getMessage());
        return new SysUserClient() {
            @Override
            public R<LoginUserVo> getUserInfo(String username, String source) {
                return R.fail("远程调用【通过用户名查询用户信息】失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> registerUserInfo(SysUserVo sysUser, String source) {
                return R.fail("远程调用【注册用户信息】失败:" + throwable.getMessage());
            }
        };
    }
}
