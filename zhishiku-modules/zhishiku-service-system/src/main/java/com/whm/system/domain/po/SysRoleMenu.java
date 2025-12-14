package com.whm.system.domain.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统服务_角色和菜单关联表
 *
 * @author : 吴华明
 * @since 2025-12-12  13:10
 */
@Data
@ApiModel(value = "sys_role_menu对象", description = "系统服务_角色和菜单关联表")
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Long menuId;
}
