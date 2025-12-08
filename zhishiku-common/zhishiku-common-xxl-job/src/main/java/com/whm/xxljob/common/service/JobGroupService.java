package com.whm.xxljob.common.service;

import com.whm.xxljob.common.model.XxlJobGroup;

import java.util.List;

/**
 * 接口定义
 *
 * @author : 吴华明
 * @since 2025-12-04  13:36
 */
public interface JobGroupService {
    List<XxlJobGroup> getJobGroup();

    boolean autoRegisterGroup();

    boolean preciselyCheck();
}
