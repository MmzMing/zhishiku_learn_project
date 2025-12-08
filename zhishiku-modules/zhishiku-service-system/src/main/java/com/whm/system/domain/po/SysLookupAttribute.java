package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 快码属性设置;
 *
 * @author ：吴华明
 * @date : 2025-09-09 11:46:28
 */
@Data
@TableName("sysLookupAttribute")
@ApiModel(value = "SysLookupAttribute对象", description = "快码属性设置")
public class SysLookupAttribute extends BaseDomain {

    /**
     * 快码类型id
     */
    @ApiModelProperty("快码类型id")
    private Long lookupTypeId;
    /**
     * 表字段
     */
    @ApiModelProperty("表字段")
    private String tblValue;
    /**
     * 字段属性
     */
    @ApiModelProperty("字段属性")
    private String tblAttribute;
    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    private String label;
    /**
     * 是否必填（0必填  1非必填）
     */
    @ApiModelProperty("是否必填（0必填  1非必填）")
    private String requireFlag;
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