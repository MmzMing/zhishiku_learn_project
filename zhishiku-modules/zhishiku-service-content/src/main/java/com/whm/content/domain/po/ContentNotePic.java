package com.whm.content.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 内容管理服务_笔记多图关联表
 *
 * @author ：吴华明
 * @since : 2025-12-12 11:38:10
 */
@Data
@TableName("content_note_pic")
@ApiModel(value = "content_note_pic对象", description = "内容管理服务_笔记多图关联表")
public class ContentNotePic extends BaseDomain {

    /**
     * 笔记id
     */
    @ApiModelProperty("笔记id")
    private Long noteId;
    /**
     * 图片url
     */
    @ApiModelProperty("图片url")
    private String picUrl;
    /**
     * 排序（1-封面图）
     */
    @ApiModelProperty("排序（1-封面图）")
    private Integer sort;
}