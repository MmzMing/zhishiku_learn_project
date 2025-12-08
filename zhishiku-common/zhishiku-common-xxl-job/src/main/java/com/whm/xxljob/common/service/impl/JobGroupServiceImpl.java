package com.whm.xxljob.common.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.whm.xxljob.common.config.XxlJobConfig;
import com.whm.xxljob.common.model.XxlJobGroup;
import com.whm.xxljob.common.service.JobGroupService;
import com.whm.xxljob.common.service.JobLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 任务分组服务实现类
 *
 * @author : 吴华明
 * @since 2025-12-04  13:36
 */
@Component
public class JobGroupServiceImpl implements JobGroupService {
    private Logger logger = LoggerFactory.getLogger(JobGroupServiceImpl.class);

    @Resource
    XxlJobConfig xxlJobConfig;

    @Resource
    JobLoginService jobLoginService;


    /**
     * 获取任务分组列表
     *
     * @return 任务分组列表
     */
    public List<XxlJobGroup> getJobGroup() {
        // 构造请求URL，调用XXL-JOB管理端的任务分组列表接口
        String url = xxlJobConfig.getAdminAddresses() + xxlJobConfig.getGroupPageList();
        HttpResponse response = HttpRequest.post(url)
                .cookie(jobLoginService.login())
                .form("appname", this.xxlJobConfig.getAppname())
                .form("title", this.xxlJobConfig.getTitle())
                .execute();
        JSONArray array = JSONUtil.parse(response.body()).getByPath("data", JSONArray.class);
        return array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobGroup.class))
                .collect(Collectors.toList());

    }


    /**
     * 自动注册执行器组到XXL-JOB调度中心
     * <p>
     * 该方法通过调用XXL-JOB调度中心的API接口，将当前应用注册为一个执行器组。
     * 支持自动注册和手动录入两种地址模式，并根据配置进行相应的参数校验和传输。
     *
     * @return boolean 注册成功返回true，失败返回false
     */
    public boolean autoRegisterGroup() {
        // 构造注册请求URL和基础表单参数
        String url = this.xxlJobConfig.getAdminAddresses() + this.xxlJobConfig.getGroupSave();
        HttpRequest httpRequest = HttpRequest.post(url)
                .form("appname", this.xxlJobConfig.getAppname())
                .form("title", this.xxlJobConfig.getTitle());
        Integer addressType = this.xxlJobConfig.getAddressType();
        httpRequest.form("addressType", addressType);
        // 当地址类型为手动录入模式时，校验并添加地址列表参数
        if (addressType.equals(1)) {
            if (StrUtil.isBlank(this.xxlJobConfig.getAddressList())) {
                throw new RuntimeException("手动录入模式下,执行器地址列表不能为空");
            }

            httpRequest.form("addressList", this.xxlJobConfig.getAddressList());
        }
        // 发送注册请求并解析响应结果
        HttpResponse response = httpRequest.cookie(jobLoginService.login()).execute();
        Object code = JSONUtil.parse(response.body()).getByPath("code");
        return code.equals(200);
    }


    /**
     * 精确检查任务配置是否存在
     *
     * @return boolean 检查结果，true表示存在匹配的任务配置，false表示不存在
     */
    public boolean preciselyCheck() {
        // 获取所有任务组列表
        List<XxlJobGroup> jobGroup = this.getJobGroup();

        // 在任务组中查找匹配当前应用名和标题的配置项
        Optional<XxlJobGroup> has = jobGroup.stream()
                .filter((xxlJobGroup) -> xxlJobGroup.getAppname().equals(this.xxlJobConfig.getAppname())
                        && xxlJobGroup.getTitle().equals(this.xxlJobConfig.getTitle()))
                .findAny();

        // 返回匹配结果
        return has.isPresent();
    }

}
