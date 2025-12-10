package com.whm.content.controller;


import com.whm.common.core.domain.R;
import com.whm.media.api.MediaFilesClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试Feign
 *
 * @author : 吴华明
 * @since 2025-12-10  13:29
 */
@RestController
@Api(tags = "Feign调用测试")
@RequestMapping("/HelloFeginController")
public class HelloFeginController {
    @Resource
    MediaFilesClient mediaFilesClient;

    @ApiOperation(value = "测试Feign")
    @GetMapping("/hello/{fileMd5}")
    public R<Boolean> hello(@PathVariable("fileMd5") String fileMd5) {
        return mediaFilesClient.checkFiles(fileMd5);
    }
}
