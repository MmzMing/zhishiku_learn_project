package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 系统服务_快码类型表
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:30:29
 */
@Data
@TableName("sys_lookup_type")
@ApiModel(value = "sys_lookup_type对象", description = "系统服务_快码类型表")
public class SysLookupType extends BaseDomain {

    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private Long parentId ;
    /**
     * 快码类型
     */
    @ApiModelProperty("快码类型")
    private String lookupType ;
    /**
     * 业务领域
     */
    @ApiModelProperty("业务领域")
    private String lookupTypeName ;
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