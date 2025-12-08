package com.whm.media.domain.po;


import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * minio上传信息
 *
 * @author ：吴华明
 * @date : 2025-09-18 15:14:45
 */
@Data
@TableName("media_process")
@ApiModel(value = "media_process对象", description = "minio上传信息")
public class MediaProcess extends BaseDomain {

    /**
     * 文件标识
     */
    @ApiModelProperty("文件标识")
    private String fileId;
    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;
    /**
     * 存储桶
     */
    @ApiModelProperty("存储桶")
    private String bucket;
    /**
     * 存储路径
     */
    @ApiModelProperty("存储路径")
    private String filePath;
    /**
     * 状态,1:未处理，2：处理成功  3处理失败
     */
    @ApiModelProperty("状态,1:未处理，2：处理成功  3处理失败")
    private String status;
    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    private LocalDateTime createDate;
    /**
     * 完成时间
     */
    @ApiModelProperty("完成时间")
    private LocalDateTime finishDate;
    /**
     * 失败次数
     */
    @ApiModelProperty("失败次数")
    private Integer failCount;
    /**
     * 媒资文件访问地址
     */
    @ApiModelProperty("媒资文件访问地址")
    private String url;
    /**
     * 失败原因
     */
    @ApiModelProperty("失败原因")
    private String errorMsg;
}