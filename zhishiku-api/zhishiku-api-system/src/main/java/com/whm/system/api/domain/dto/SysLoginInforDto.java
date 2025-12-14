package com.whm.system.api.domain.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 远程调用_系统访问记录
 *
 * @author : 吴华明
 * @since 2025-12-14  14:17
 */
@Data
public class SysLoginInforDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userName;
    /**
     * 登录ip地址
     */
    @ApiModelProperty("登录ip地址")
    private String ipAddr;
    /**
     * 登录状态（0成功  1失败）
     */
    @ApiModelProperty("登录状态（0成功  1失败）")
    private String status;
    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    private String msg;
    /**
     * 访问时间
     */
    @ApiModelProperty("访问时间")
    private LocalDateTime accessTime;

}

