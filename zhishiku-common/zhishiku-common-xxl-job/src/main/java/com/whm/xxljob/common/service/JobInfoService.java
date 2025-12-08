package com.whm.xxljob.common.service;

import com.whm.xxljob.common.model.XxlJobInfo;

import java.util.List;

/**
 * 接口定义
 *
 * @author : 吴华明
 * @since 2025-12-04  13:36
 */
public interface JobInfoService {
    List<XxlJobInfo> getJobInfo(Integer var1, String var2);

    Integer addJobInfo(XxlJobInfo var1);

    Integer updateJobInfo(XxlJobInfo var1);
}

