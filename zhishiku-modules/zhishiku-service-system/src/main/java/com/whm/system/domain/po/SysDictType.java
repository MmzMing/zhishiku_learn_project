package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 系统服务_字典类型表
 *
 * @author ：吴华明
 * @since : 2025-12-12 14:03:26
 */
@Data
@TableName("sys_dict_type")
@ApiModel(value = "sys_dict_type对象", description = "系统服务_字典类型表")
public class SysDictType extends BaseDomain {

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    private String dictName ;
    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    private String dictType ;
    /**
     * 状态（0正常  1停用）
     */
    @ApiModelProperty("状态（0正常  1停用）")
    private String status ;
}