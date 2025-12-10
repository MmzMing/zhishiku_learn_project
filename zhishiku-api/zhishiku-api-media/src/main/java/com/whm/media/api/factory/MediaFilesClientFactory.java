package com.whm.media.api.factory;


import com.whm.common.core.domain.R;
import com.whm.media.api.MediaFilesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断降级处理
 *
 * @author : 吴华明
 * @since 2025-12-10  13:04
 */
@Slf4j
@Component
public class MediaFilesClientFactory implements FallbackFactory<MediaFilesClient> {
    @Override
    public MediaFilesClient create(Throwable throwable) {
        log.error("远程调用【media服务】失败:{}", throwable.getMessage());
        return new MediaFilesClient() {
            @Override
            public R<Boolean> checkFiles(String filesMd5) {
                return R.fail("远程调用【检查文件md5】失败:" + throwable.getMessage());
            }
        };
    }
}
