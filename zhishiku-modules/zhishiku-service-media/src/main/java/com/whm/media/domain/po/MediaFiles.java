package com.whm.media.domain.po;


import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 媒资信息
 *
 * @author ：吴华明
 * @since : 2025-09-16 18:43:57
 */
@Data
@TableName("media_files")
@ApiModel(value = "media_files对象", description = "媒资信息")
public class MediaFiles extends BaseDomain {

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;
    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    private String organizationCode;
    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    private String organizationName;
    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;
    /**
     * 文件类型（图片、文档、视频）
     */
    @ApiModelProperty("文件类型（图片、文档、视频）")
    private String fileType;
    /**
     * 标签
     */
    @ApiModelProperty("标签")
    private String tags;
    /**
     * 存储目录
     */
    @ApiModelProperty("存储目录")
    private String bucket;
    /**
     * 存储路径
     */
    @ApiModelProperty("存储路径")
    private String filePath;
    /**
     * 文件id
     */
    @ApiModelProperty("文件id")
    private String fileId;
    /**
     * 媒资文件访问地址
     */
    @ApiModelProperty("媒资文件访问地址")
    private String url;
    /**
     * 上传人
     */
    @ApiModelProperty("上传人")
    private String userName;
    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    private LocalDateTime createDate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime changeDate;
    /**
     * 状态,1:正常，0:不展示
     */
    @ApiModelProperty("状态,1:正常，0:不展示")
    private String status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private String auditStatus;
    /**
     * 审核意见
     */
    @ApiModelProperty("审核意见")
    private String auditMind;
    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Long fileSize;
}
