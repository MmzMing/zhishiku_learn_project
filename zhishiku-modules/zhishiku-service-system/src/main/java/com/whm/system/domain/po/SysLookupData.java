package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 系统服务_快码数据表
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:52:30
 */
@Data
@TableName("sys_lookup_data")
@ApiModel(value = "sys_lookup_data对象", description = "系统服务_快码数据表")
public class SysLookupData extends BaseDomain {

    /**
     * 快码排序
     */
    @ApiModelProperty("快码排序")
    private Integer lookupSort;
    /**
     * 快码code
     */
    @ApiModelProperty("快码code")
    private String lookupCode;
    /**
     * 快码名称
     */
    @ApiModelProperty("快码名称")
    private String lookupName;
    /**
     * 快码类型id
     */
    @ApiModelProperty("快码类型id")
    private String lookupTypeId;
    /**
     * 快码类型
     */
    @ApiModelProperty("快码类型")
    private String lookupType;
    /**
     * 生效日期
     */
    @ApiModelProperty("生效日期")
    private LocalDateTime staDate;
    /**
     * 失效日期
     */
    @ApiModelProperty("失效日期")
    private LocalDateTime endDate;
    /**
     * 快码标记
     */
    @ApiModelProperty("快码标记")
    private String lookupTag;
    /**
     * 是否默认（y是  n否）
     */
    @ApiModelProperty("是否默认（y是  n否）")
    private String isDefault;
    /**
     * 状态（0正常  1停用）
     */
    @ApiModelProperty("状态（0正常  1停用）")
    private String status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}