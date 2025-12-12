package com.whm.content.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 内容管理服务_笔记信息表
 *
 * @author ：吴华明
 * @since : 2025-12-12 10:41:41
 */
@Data
@TableName("content_note")
@ApiModel(value = "content_note对象", description = "内容管理服务_笔记信息表")
public class ContentNote extends BaseDomain {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
    /**
     * 笔记名称
     */
    @ApiModelProperty("笔记名称")
    private String noteName;
    /**
     * 笔记标签（多个标签用英文逗号分隔，如：java,mysql,优化）
     */
    @ApiModelProperty("笔记标签（多个标签用英文逗号分隔，如：java,mysql,优化）")
    private String noteTags;
    /**
     * 笔记简介/描述
     */
    @ApiModelProperty("笔记简介/描述")
    private String description;
    /**
     * 大类（如：技术、生活、职场）
     */
    @ApiModelProperty("大类（如：技术、生活、职场）")
    private String broadCode;
    /**
     * 小类（如：技术-java、生活-美食）
     */
    @ApiModelProperty("小类（如：技术-java、生活-美食）")
    private String narrowCode;
    /**
     * 笔记等级（1-入门  2-进阶  3-高级  4-专家）
     */
    @ApiModelProperty("笔记等级（1-入门  2-进阶  3-高级  4-专家）")
    private Integer noteGrade;
    /**
     * 笔记模式（1-公开  2-仅自己可见  3-指定租户可见  4-付费可见）
     */
    @ApiModelProperty("笔记模式（1-公开  2-仅自己可见  3-指定租户可见  4-付费可见）")
    private Integer noteMode;
    /**
     * 适合人群（多个人群用英文逗号分隔，如：初学者,开发工程师,架构师）
     */
    @ApiModelProperty("适合人群（多个人群用英文逗号分隔，如：初学者,开发工程师,架构师）")
    private String suitableUsers;
    /**
     * 审核状态（0-待审核  1-审核通过  2-审核驳回  3-已撤回）
     */
    @ApiModelProperty("审核状态（0-待审核  1-审核通过  2-审核驳回  3-已撤回）")
    private Integer auditStatus;
    /**
     * 笔记状态（1-正常  2-下架  3-草稿  4-过期）
     */
    @ApiModelProperty("笔记状态（1-正常  2-下架  3-草稿  4-过期）")
    private Integer status;
    /**
     * 笔记发布时间（审核通过后生效）
     */
    @ApiModelProperty("笔记发布时间（审核通过后生效）")
    private LocalDateTime publishTime;
    /**
     * 笔记浏览量
     */
    @ApiModelProperty("笔记浏览量")
    private Long viewCount;
    /**
     * 笔记点赞量
     */
    @ApiModelProperty("笔记点赞量")
    private Long likeCount;
    /**
     * 乐观锁版本号（防并发更新冲突）
     */
    @ApiModelProperty("乐观锁版本号（防并发更新冲突）")
    private Long version;
    /**
     * 备注（如审核驳回原因、特殊说明）
     */
    @ApiModelProperty("备注（如审核驳回原因、特殊说明）")
    private String remark;
}