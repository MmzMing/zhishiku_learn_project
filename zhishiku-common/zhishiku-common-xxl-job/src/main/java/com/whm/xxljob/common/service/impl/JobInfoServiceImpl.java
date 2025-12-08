package com.whm.xxljob.common.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.whm.xxljob.common.config.XxlJobConfig;
import com.whm.xxljob.common.model.XxlJobInfo;
import com.whm.xxljob.common.service.JobInfoService;
import com.whm.xxljob.common.service.JobLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务信息服务实现类
 *
 * @author : 吴华明
 * @since 2025-12-04  13:36
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {
    @Resource
    XxlJobConfig xxlJobConfig;
    @Resource
    JobLoginService jobLoginService;
    /**
     * 根据任务组ID和执行器处理器名称获取任务信息列表
     *
     * @param jobGroupId      任务组ID，用于筛选指定任务组下的任务
     * @param executorHandler 执行器处理器名称，用于筛选指定处理器的任务
     * @return 返回符合条件的任务信息列表
     */
    public List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler) {
        // 构造请求URL，调用XXL-JOB管理端的分页查询接口
        String url = this.xxlJobConfig.getAdminAddresses() + this.xxlJobConfig.getPageList();
        HttpResponse response = HttpRequest.post(url)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .form("triggerStatus", -1)
                .cookie(jobLoginService.login())
                .execute();
        String body = response.body();

        // 解析响应数据，提取任务信息数组并转换为对象列表
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);
        return array.stream().map((o) -> JSONUtil.toBean((JSONObject) o, XxlJobInfo.class)).collect(Collectors.toList());
    }


    /**
     * 添加作业信息
     *
     * @param xxlJobInfo 作业信息对象，包含要添加的作业的详细配置信息
     * @return 返回操作结果，通常为添加成功的作业ID，如果添加失败则返回null或相应的错误码
     */
    public Integer addJobInfo(XxlJobInfo xxlJobInfo) {
        // 构造请求URL，使用管理员地址拼接添加作业的接口路径
        String url = this.xxlJobConfig.getAdminAddresses() + this.xxlJobConfig.getAdd();
        // 调用操作作业信息的方法执行添加操作
        return this.opJobInfo(xxlJobInfo, url);
    }


    /**
     * 更新任务信息
     *
     * @param xxlJobInfo 任务信息对象，包含需要更新的任务配置信息
     * @return 返回操作结果，成功时返回1，失败时返回null或具体的错误码
     */
    public Integer updateJobInfo(XxlJobInfo xxlJobInfo) {
        // 构造更新任务信息的API地址
        String url = this.xxlJobConfig.getAdminAddresses() + this.xxlJobConfig.getUpdate();
        // 调用操作任务信息的通用方法执行更新操作
        return this.opJobInfo(xxlJobInfo, url);
    }


    /**
     * 操作作业信息
     *
     * @param xxlJobInfo 作业信息对象，包含要操作的作业详细信息
     * @param url        目标URL地址，用于发送HTTP请求
     * @return 返回操作结果，成功时返回content字段的整数值，失败时抛出异常
     */
    private Integer opJobInfo(XxlJobInfo xxlJobInfo, String url) {
        // 将作业信息对象转换为参数映射表
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        // 发送POST请求并获取响应
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(jobLoginService.login())
                .execute();
        // 解析响应JSON数据
        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        // 根据响应码判断操作是否成功
        if (code.equals(200)) {
            return Convert.toInt(json.getByPath("content"));
        } else {
            throw new RuntimeException("update jobInfo error!");
        }
    }

}
