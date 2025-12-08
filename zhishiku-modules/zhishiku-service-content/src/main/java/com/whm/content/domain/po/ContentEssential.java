package com.whm.content.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识库基本信息;
 *
 * @author ：吴华明
 * @date : 2025-09-05 16:07:56
 */
@Data
@TableName("content_essential")
@ApiModel(value = "ContentEssential对象", description = "知识库基本信息")
public class ContentEssential extends BaseDomain {
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
     * 笔记名称
     */
    @ApiModelProperty("笔记名称")
    private String noteName;

    /**
     * 标签
     */
    @ApiModelProperty("标签")
    private String noteTags;

    /**
     * 介绍
     */
    @ApiModelProperty("介绍")
    private String description;

    /**
     * 大类
     */
    @ApiModelProperty("大类")
    private String broadCategory;

    /**
     * 小类
     */
    @ApiModelProperty("小类")
    private String narrowCategory;

    /**
     * 等级
     */
    @ApiModelProperty("等级")
    private String noteGrade;

    /**
     * 模式
     */
    @ApiModelProperty("模式")
    private String noteMode;

    /**
     * 适合人群
     */
    @ApiModelProperty("适合人群")
    private String suitableUsers;

    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String pic;

    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private String auditStatus;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;


}