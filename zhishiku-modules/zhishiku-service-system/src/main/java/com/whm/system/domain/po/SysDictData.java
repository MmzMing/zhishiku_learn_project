package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 系统服务_字典数据表
 *
 * @author ：吴华明
 * @since : 2025-12-12 14:04:54
 */
@Data
@TableName("sys_dict_data")
@ApiModel(value = "sys_dict_data对象", description = "系统服务_字典数据表")
public class SysDictData extends BaseDomain {

    /**
     * 字典编码
     */
    @ApiModelProperty("字典编码")
    private String dictCode ;
    /**
     * 字典排序
     */
    @ApiModelProperty("字典排序")
    private Integer dictSort ;
    /**
     * 字典标签
     */
    @ApiModelProperty("字典标签")
    private String dictLabel ;
    /**
     * 字典键值
     */
    @ApiModelProperty("字典键值")
    private String dictValue ;
    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    private String dictType ;
    /**
     * 样式属性（其他样式扩展）
     */
    @ApiModelProperty("样式属性（其他样式扩展）")
    private String cssClass ;
    /**
     * 表格回显样式
     */
    @ApiModelProperty("表格回显样式")
    private String listClass ;
    /**
     * 默认（y是  n否）
     */
    @ApiModelProperty("默认（y是  n否）")
    private String isDefault ;
    /**
     * 状态（0正常  1停用）
     */
    @ApiModelProperty("状态（0正常  1停用）")
    private String status ;
}