package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 系统服务_角色信息表
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:19:30
 */
@Data
@TableName("sys_role")
@ApiModel(value = "sys_role对象", description = "系统服务_角色信息表")
public class SysRole extends BaseDomain {

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;
    /**
     * 角色权限字符串
     */
    @ApiModelProperty("角色权限字符串")
    private String roleKey;
    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer roleSort;
    /**
     * 数据范围（1：全部数据权限  2：自定数据权限  3：本部门数据权限  4：本部门及以下数据权限）
     */
    @ApiModelProperty("数据范围（1：全部数据权限  2：自定数据权限  3：本部门数据权限  4：本部门及以下数据权限）")
    private String dataScope;
    /**
     * 菜单树选择项是否关联显示
     */
    @ApiModelProperty("菜单树选择项是否关联显示")
    private Integer menuCheckStrictly;
    /**
     * 角色状态（0正常  1停用）
     */
    @ApiModelProperty("角色状态（0正常  1停用）")
    private String status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}