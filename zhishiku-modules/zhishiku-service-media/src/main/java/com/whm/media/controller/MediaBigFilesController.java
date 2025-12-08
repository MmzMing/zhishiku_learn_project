package com.whm.media.controller;


import com.whm.common.core.domain.R;
import com.whm.media.service.MediaFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 大文件信息接口 控制层
 *
 * @author : 吴华明
 * @since 2025-11-30  10:31
 */
@Api(tags = "大文件信息接口")
@RestController
@RequestMapping("/mediaBigFiles")
public class MediaBigFilesController {
    @Autowired
    MediaFilesService mediaFileService;

    /**
     * 检查文件MD5
     *
     * @param fileMd5 文件MD5值
     * @return 检查结果，true表示文件存在，false表示文件不存在
     */
    @ApiOperation(value = "检查文件md5")
    @PostMapping("/checkFileMd5")
    public R<Boolean> checkFileMd5(@RequestParam("fileMd5") String fileMd5) {
        return R.ok(mediaFileService.checkFiles(fileMd5));
    }

    /**
     * 检查分块文件
     *
     * @param fileMd5    文件的MD5值，用于唯一标识文件
     * @param chunkIndex 分块序号，表示当前检查的是第几个分块
     * @return 返回检查结果，true表示分块存在，false表示分块不存在
     */
    @ApiOperation(value = "检查分块文件")
    @PostMapping("/checkChunk")
    public R<Boolean> checkChunk(@RequestParam("fileMd5") String fileMd5,
                                 @RequestParam("chunkIndex") int chunkIndex) {
        return R.ok(mediaFileService.checkChunk(fileMd5, chunkIndex));
    }

    /**
     * 上传分块文件
     *
     * @param file       文件对象，包含上传的文件数据
     * @param fileMd5    文件的MD5值，用于标识文件唯一性
     * @param chunkIndex 分块索引，表示当前上传的是第几个文件分块
     * @return R<Boolean> 响应结果，包含上传是否成功的布尔值
     */
    @ApiOperation(value = "上传分块文件")
    @PostMapping(value = "/uploadChunk",
            //仅限文件
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Boolean> uploadChunk(@RequestPart("file") MultipartFile file,
                                  @RequestParam("fileMd5") String fileMd5,
                                  @RequestParam("chunkIndex") int chunkIndex) {
        // 调用媒体文件服务处理分块文件上传
        return R.ok(mediaFileService.uploadChunks(file, fileMd5, chunkIndex));
    }

    /**
     * 合并分块文件
     *
     * @param fileMd5    文件的MD5值，用于标识文件唯一性
     * @param fileName   文件名
     * @param chunkTotal 分块总和
     * @return R<Boolean> 响应结果，包含合并是否成功的布尔值
     */
    @ApiOperation(value = "合并分块文件")
    @PostMapping("/mergeChunks")
    public R<Boolean> mergeChunks(@RequestParam("fileMd5") String fileMd5,
                                  @RequestParam("fileName") String fileName,
                                  @RequestParam("chunkTotal") int chunkTotal) {
        // 调用媒体文件服务处理分块文件合并
        return R.ok(mediaFileService.mergeChunks(fileMd5, fileName, chunkTotal));
    }


}
