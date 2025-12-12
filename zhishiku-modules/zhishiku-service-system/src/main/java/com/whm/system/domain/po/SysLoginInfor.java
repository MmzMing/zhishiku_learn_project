package com.whm.system.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.whm.common.mybatis.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 系统服务_系统访问记录
 *
 * @author ：吴华明
 * @since : 2025-12-12 13:59:50
 */
@Data
@TableName("sys_login_infor")
@ApiModel(value = "sys_login_infor对象", description = "系统服务_系统访问记录")
public class SysLoginInfor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id ;

     /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userName ;
    /**
     * 登录ip地址
     */
    @ApiModelProperty("登录ip地址")
    private String ipAddr ;
    /**
     * 登录状态（0成功  1失败）
     */
    @ApiModelProperty("登录状态（0成功  1失败）")
    private String status ;
    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    private String msg ;
    /**
     * 访问时间
     */
    @ApiModelProperty("访问时间")
    private LocalDateTime accessTime ;
}