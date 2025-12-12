package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 系统服务_租户信息表
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:07:47
 */
@Data
@TableName("sys_tenant_data")
@ApiModel(value = "sys_tenant_data对象", description = "系统服务_租户信息表")
public class SysTenantData extends BaseDomain {

    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
    /**
     * 租户编码
     */
    @ApiModelProperty("租户编码")
    private String tenantCode;
    /**
     * 租户名字
     */
    @ApiModelProperty("租户名字")
    private String tenantName;
    /**
     * 租户类型(common-普通租户  admin-管理员租户  test-测试租户)
     */
    @ApiModelProperty("租户类型(common-普通租户  admin-管理员租户  test-测试租户)")
    private String tenantType;
    /**
     * 租户过期时间
     */
    @ApiModelProperty("租户过期时间")
    private LocalDateTime expireTime;
    /**
     * 上级租户id
     */
    @ApiModelProperty("上级租户id")
    private String parentTenantId;
    /**
     * 租户联系人
     */
    @ApiModelProperty("租户联系人")
    private String tenantContact;
    /**
     * 联系人电话
     */
    @ApiModelProperty("联系人电话")
    private String tenantPhone;
    /**
     * 联系人邮箱
     */
    @ApiModelProperty("联系人邮箱")
    private String tenantEmail;
    /**
     * 数据隔离级别(shared-共享表  separate-独立表  db-独立库)
     */
    @ApiModelProperty("数据隔离级别(shared-共享表  separate-独立表  db-独立库)")
    private String isolationLevel;
    /**
     * 租户最大用户数限制
     */
    @ApiModelProperty("租户最大用户数限制")
    private Integer maxUserNum;
    /**
     * 审核状态(0-未审核  1-审核通过  2-审核驳回)
     */
    @ApiModelProperty("审核状态(0-未审核  1-审核通过  2-审核驳回)")
    private Integer auditStatus;
    /**
     * 审核人id
     */
    @ApiModelProperty("审核人id")
    private String auditBy;
    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    private LocalDateTime auditTime;
}