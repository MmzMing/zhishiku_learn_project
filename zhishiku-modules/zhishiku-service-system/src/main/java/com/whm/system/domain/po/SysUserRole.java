package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统服务_用户和角色关联表
 *
 * @author ：吴华明
 * @since : 2025-12-12 12:26:45
 */
@Data
@ApiModel(value = "sys_user_role对象", description = "系统服务_用户和角色关联表")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long roleId;
}