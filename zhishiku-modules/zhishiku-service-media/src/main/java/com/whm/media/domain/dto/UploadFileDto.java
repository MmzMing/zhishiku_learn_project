package com.whm.media.domain.dto;


import lombok.Data;

/**
 * TODO添加描述
 *
 * @author 吴华明
 * @date 2025/9/16 17:56
 */
@Data
public class UploadFileDto {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件类型（文档，音频，视频）
     */
    private String fileType;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 标签
     */
    private String tags;
    /**
     * 上传人
     */
    private String username;
    /**
     * 备注
     */
    private String remark;

}

