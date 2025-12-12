package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 系统服务_用户信息表
 *
 * @author ：吴华明
 * @since : 2025-12-12 12:26:45
 */
@Data
@TableName("sys_user")
@ApiModel(value = "sys_user对象", description = "系统服务_用户信息表")
public class SysUser extends BaseDomain {

    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userName;
    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty("用户类型（00系统用户）")
    private String userType;
    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phoneNumber;
    /**
     * 用户性别（0男  1女  2未知）
     */
    @ApiModelProperty("用户性别（0男  1女  2未知）")
    private String sex;
    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatar;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
    /**
     * 帐号状态（0正常  1停用）
     */
    @ApiModelProperty("帐号状态（0正常  1停用）")
    private String status;
    /**
     * 删除标志（0代表存在  2代表删除）
     */
    @ApiModelProperty("删除标志（0代表存在  2代表删除）")
    private String delFlag;
    /**
     * 最后登录ip
     */
    @ApiModelProperty("最后登录ip")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginDate;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 钉钉id
     */
    @ApiModelProperty("钉钉id")
    private String dingId;
    /**
     * 微信id
     */
    @ApiModelProperty("微信id")
    private String wxId;
    /**
     * 企鹅id
     */
    @ApiModelProperty("企鹅id")
    private String qqId;
}