package com.whm.media.api;


import com.whm.common.core.constant.ServiceNameConstants;
import com.whm.common.core.domain.R;
import com.whm.media.api.factory.MediaFilesClientFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用
 *
 * @author : 吴华明
 * @since 2025-12-10  13:03
 */
@FeignClient(contextId = "MediaFilesClient",
        value = ServiceNameConstants.MEDIA_SERVICE,
        //本地测试时候加上自己的 ip，或者指定 nacos 配置的 ${media.service.url}
        url = "http://192.168.1.5:40030",
        fallbackFactory = MediaFilesClientFactory.class
)
public interface MediaFilesClient {
    /**
     * 校验文件是否存在
     *
     * @param filesMd5 文件md5
     * @return Boolean
     */
    @GetMapping("/media/mediaFiles/checkFiles/{filesMd5}")
    R<Boolean> checkFiles(@PathVariable("filesMd5") String filesMd5);
}
