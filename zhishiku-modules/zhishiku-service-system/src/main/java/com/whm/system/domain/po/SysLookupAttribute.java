package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 系统服务_快码属性设置表
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:57:52
 */
@Data
@TableName("sys_lookup_attribute")
@ApiModel(value = "sys_lookup_attribute对象", description = "系统服务_快码属性设置表")
public class SysLookupAttribute extends BaseDomain {

    /**
     * 快码id
     */
    @ApiModelProperty("快码id")
    private Long lookupDataId ;
    /**
     * 字段编码
     */
    @ApiModelProperty("字段编码")
    private String tblCode ;
    /**
     * 字段属性
     */
    @ApiModelProperty("字段属性")
    private String tblValue ;
    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    private String tagName ;
    /**
     * 是否必填（0必填  1非必填）
     */
    @ApiModelProperty("是否必填（0必填  1非必填）")
    private String requireFlag ;
    /**
     * 状态（0正常  1停用）
     */
    @ApiModelProperty("状态（0正常  1停用）")
    private String status ;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark ;
}