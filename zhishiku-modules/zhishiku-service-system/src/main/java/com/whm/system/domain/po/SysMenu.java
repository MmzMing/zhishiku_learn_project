package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 系统服务_菜单权限表
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:51:35
 */
@Data
@TableName(value = "sys_menu",excludeProperty = {"children"})
@ApiModel(value = "sys_menu对象", description = "系统服务_菜单权限表")
public class SysMenu extends BaseDomain {

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;
    /**
     * 父菜单id
     */
    @ApiModelProperty("父菜单id")
    private Long parentId;
    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer orderNum;
    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;
    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
    private String component;
    /**
     * 路由参数
     */
    @ApiModelProperty("路由参数")
    private String query;
    /**
     * 是否为外链（0是  1否）
     */
    @ApiModelProperty("是否为外链（0是  1否）")
    private Integer isFrame;
    /**
     * 是否缓存（0缓存  1不缓存）
     */
    @ApiModelProperty("是否缓存（0缓存  1不缓存）")
    private Integer isCache;
    /**
     * 菜单类型（m目录  c菜单  f按钮）
     */
    @ApiModelProperty("菜单类型（m目录  c菜单  f按钮）")
    private String menuType;
    /**
     * 菜单状态（0显示  1隐藏）
     */
    @ApiModelProperty("菜单状态（0显示  1隐藏）")
    private String visible;
    /**
     * 菜单状态（0正常  1停用）
     */
    @ApiModelProperty("菜单状态（0正常  1停用）")
    private String status;
    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String perms;
    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 子菜单
     */
    //非表字段
    @ApiModelProperty("子菜单")
    private List<SysMenu> children = new ArrayList<SysMenu>();
}