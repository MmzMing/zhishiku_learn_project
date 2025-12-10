package com.whm.media.controller;


import com.whm.common.core.domain.R;
import com.whm.media.service.MediaFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 小文件信息接口 控制层
 *
 * @author 吴华明
 * @since 2025/9/16 17:47
 */
@Slf4j
@Api(tags = "小文件信息接口")
@RestController
@RequestMapping("/mediaFiles")
public class MediaFilesController {
    @Autowired
    private MediaFilesService mediaFilesService;

    /**
     * 上传图片文件接口
     *
     * @param fileData   上传的文件数据，不能为空
     * @param objectName 文件对象名称
     * @return 返回上传文件的结果信息，包含文件访问路径等信息
     */
    @ApiOperation("上传图片")
    @RequestMapping(value = "/uploadFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Boolean> upload(@RequestPart(name = "fileData") MultipartFile fileData, @RequestParam(name = "objectName", required = false) String objectName) {
        // 调用媒体文件服务处理文件上传逻辑
        return R.ok(mediaFilesService.uploadFile(fileData, objectName));
    }

    /**
     * 校验文件是否存在
     *
     * @param filesMd5 文件md5
     * @return Boolean
     */
    @ApiOperation("校验文件是否存在")
    @GetMapping("/checkFiles/{filesMd5}")
    public R<Boolean> checkFiles(@PathVariable("filesMd5") String filesMd5) {
        return R.ok(mediaFilesService.checkFiles(filesMd5));
    }

}
