package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 系统服务_通知公告表
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:25:54
 */
@Data
@TableName("sys_notice")
@ApiModel(value = "sys_notice对象", description = "系统服务_通知公告表")
public class SysNotice extends BaseDomain {

    /**
     * 公告标题
     */
    @ApiModelProperty("公告标题")
    private String noticeTitle ;
    /**
     * 公告类型（1通知  2公告）
     */
    @ApiModelProperty("公告类型（1通知  2公告）")
    private String noticeType ;
    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    private byte[] noticeContent ;
    /**
     * 公告状态（0正常  1关闭）
     */
    @ApiModelProperty("公告状态（0正常  1关闭）")
    private String status ;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark ;
}