package com.whm.content.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 内容管理服务_笔记评论表
 *
 * @author ：吴华明
 * @since : 2025-12-12 11:28:36
 */
@Data
@TableName("content_note_comment")
@ApiModel(value = "content_note_comment对象", description = "内容管理服务_笔记评论表")
public class ContentNoteComment extends BaseDomain {

    /**
     * 关联笔记id（关联content_note.id）
     */
    @ApiModelProperty("关联笔记id（关联content_note.id）")
    private Long noteId ;
    /**
     * 评论人id
     */
    @ApiModelProperty("评论人id")
    private String commentUserId ;
    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String commentContent ;
    /**
     * 父评论id（用于回复：null为根评论，非null为回复某条评论）
     */
    @ApiModelProperty("父评论id（用于回复：null为根评论，非null为回复某条评论）")
    private Long parentCommentId ;
    /**
     * 审核状态（0-待审核  1-审核通过  2-审核驳回）
     */
    @ApiModelProperty("审核状态（0-待审核  1-审核通过  2-审核驳回）")
    private Integer auditStatus ;
    /**
     * 评论状态（1-正常  2-隐藏  3-删除）
     */
    @ApiModelProperty("评论状态（1-正常  2-隐藏  3-删除）")
    private Integer status ;
    /**
     * 评论点赞数
     */
    @ApiModelProperty("评论点赞数")
    private Long likeCount ;
    /**
     * 乐观锁版本号（防并发更新冲突）
     */
    @ApiModelProperty("乐观锁版本号（防并发更新冲突）")
    private Long version ;
}