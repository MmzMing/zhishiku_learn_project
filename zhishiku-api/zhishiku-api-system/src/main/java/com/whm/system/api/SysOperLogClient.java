package com.whm.system.api;

import com.whm.common.core.constant.SecurityConstants;
import com.whm.common.core.constant.ServiceNameConstants;
import com.whm.common.core.domain.R;
import com.whm.system.api.domain.dto.SysLoginInforDto;
import com.whm.system.api.domain.dto.SysOperLogDto;
import com.whm.system.api.factory.SysOperLogClientFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 远程调用_操作日志记录
 *
 * @author : 吴华明
 * @since 2025-12-14  14:10
 */
@FeignClient(contextId = "SysOperLogClient",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        //本地测试时候加上自己的 ip，或者指定 nacos 配置的 ${media.service.url}
        url = "http://192.168.1.5:40020",
        fallbackFactory = SysOperLogClientFactory.class
)
public interface SysOperLogClient {
    /**
     * 保存系统日志
     *
     * @param sysOperLogDto 日志实体
     * @param source        请求来源
     * @return 结果
     */
    @PostMapping("/1")
    R<Boolean> saveLog(@RequestBody SysOperLogDto sysOperLogDto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 保存访问记录
     *
     * @param sysLogininforDto 访问实体
     * @param source           请求来源
     * @return 结果
     */
    @PostMapping("/2")
    R<Boolean> saveLoginInfor(@RequestBody SysLoginInforDto sysLogininforDto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
