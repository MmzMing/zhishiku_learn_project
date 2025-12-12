package com.whm.media.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 媒资服务_文件处理历史表
 *
 * @author ：吴华明
 * @since : 2025-12-12 11:45:49
 */
@Data
@TableName("media_process_history")
@ApiModel(value = "media_process_history对象", description = "媒资服务_文件处理历史表")
public class MediaProcessHistory extends BaseDomain {

    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId ;
    /**
     * 文件标识
     */
    @ApiModelProperty("文件标识")
    private String fileId ;
    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName ;
    /**
     * 存储源
     */
    @ApiModelProperty("存储源")
    private String bucket ;
    /**
     * 状态,1:未处理，2：处理成功  3处理失败
     */
    @ApiModelProperty("状态,1:未处理，2：处理成功  3处理失败")
    private String status ;
    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    private LocalDateTime createDate ;
    /**
     * 完成时间
     */
    @ApiModelProperty("完成时间")
    private LocalDateTime finishDate ;
    /**
     * 媒资文件访问地址
     */
    @ApiModelProperty("媒资文件访问地址")
    private String url ;
    /**
     * 失败次数
     */
    @ApiModelProperty("失败次数")
    private Integer failCount ;
    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String filePath ;
    /**
     * 失败原因
     */
    @ApiModelProperty("失败原因")
    private String errorMsg ;
}