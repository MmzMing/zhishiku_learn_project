package com.whm.system.api.factory;


import com.whm.common.core.domain.R;
import com.whm.system.api.SysOperLogClient;
import com.whm.system.api.domain.dto.SysLoginInforDto;
import com.whm.system.api.domain.dto.SysOperLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * SysOperLogClient 熔断降级
 *
 * @author : 吴华明
 * @since 2025-12-14  14:20
 */
@Slf4j
@Component
public class SysOperLogClientFactory implements FallbackFactory<SysOperLogClient> {
    @Override
    public SysOperLogClient create(Throwable throwable) {
        log.error("远程调用【log服务】失败:{}", throwable.getMessage());
        return new SysOperLogClient() {
            @Override
            public R<Boolean> saveLog(SysOperLogDto sysOperLogDto, String source) {
                return R.fail("远程调用【保存系统日志】失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> saveLoginInfor(SysLoginInforDto sysLogininforDto, String source) {
                return R.fail("远程调用【保存访问记录】失败:" + throwable.getMessage());
            }

        };
    }
}
