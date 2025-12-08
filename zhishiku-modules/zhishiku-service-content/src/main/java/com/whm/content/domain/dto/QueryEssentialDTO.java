package com.whm.content.domain.dto;


import lombok.Data;

/**
 * 课程查询条件
 *
 * @author 吴华明
 * @date 2025/9/6 14:42
 */
@Data
public class QueryEssentialDTO {
    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 笔记名称
     */
    private String noteName;

    /**
     * 发布状态
     */
    private String status;
}
