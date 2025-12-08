package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 快码数据表
 *
 * @author ：吴华明
 * @date : 2025-09-09 12:23:39
 */
@Data
@TableName("sysLookupData")
@ApiModel(value = "SysLookupData对象", description = "快码数据表")
public class SysLookupData extends BaseDomain {

    /**
     * 快码排序
     */
    @ApiModelProperty("快码排序")
    private Integer lookupSort ;
    /**
     * 含义
     */
    @ApiModelProperty("含义")
    private String lookupValue ;
    /**
     * 快码code
     */
    @ApiModelProperty("快码code")
    private String lookupCode ;
    /**
     * 快码类型id
     */
    @ApiModelProperty("快码类型id")
    private String lookupTypeId ;
    /**
     * 快码类型
     */
    @ApiModelProperty("快码类型")
    private String lookupType ;
    /**
     * 生效日期
     */
    @ApiModelProperty("生效日期")
    private Date staDate ;
    /**
     * 失效日期
     */
    @ApiModelProperty("失效日期")
    private Date endDate ;
    /**
     * 快码标记
     */
    @ApiModelProperty("快码标记")
    private String lookupTag ;
    /**
     * 是否默认（y是  n否）
     */
    @ApiModelProperty("是否默认（y是  n否）")
    private String isDefault ;
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
    /**
     * 属性1
     */
    @ApiModelProperty("属性1")
    private String attribute1 ;
    /**
     * 属性2
     */
    @ApiModelProperty("属性2")
    private String attribute2 ;
    /**
     * 属性3
     */
    @ApiModelProperty("属性3")
    private String attribute3 ;
    /**
     * 属性4
     */
    @ApiModelProperty("属性4")
    private String attribute4 ;
    /**
     * 属性5
     */
    @ApiModelProperty("属性5")
    private String attribute5 ;
    /**
     * 属性6
     */
    @ApiModelProperty("属性6")
    private String attribute6 ;
    /**
     * 属性7
     */
    @ApiModelProperty("属性7")
    private String attribute7 ;
    /**
     * 属性8
     */
    @ApiModelProperty("属性8")
    private String attribute8 ;
    /**
     * 属性9
     */
    @ApiModelProperty("属性9")
    private String attribute9 ;
    /**
     * 属性10
     */
    @ApiModelProperty("属性10")
    private String attribute10 ;
    /**
     * 属性11
     */
    @ApiModelProperty("属性11")
    private String attribute11 ;
    /**
     * 属性12
     */
    @ApiModelProperty("属性12")
    private String attribute12 ;
    /**
     * 属性13
     */
    @ApiModelProperty("属性13")
    private String attribute13 ;
    /**
     * 属性14
     */
    @ApiModelProperty("属性14")
    private String attribute14 ;
    /**
     * 属性15
     */
    @ApiModelProperty("属性15")
    private String attribute15 ;
    /**
     *
     */
    @ApiModelProperty("")
    private String lookupName ;
}