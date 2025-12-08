package com.whm.xxljob.common.core;


import cn.hutool.core.collection.CollUtil;
import com.whm.xxljob.common.annotation.XxlRegister;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.whm.xxljob.common.model.XxlJobGroup;
import com.whm.xxljob.common.model.XxlJobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import com.whm.xxljob.common.service.JobGroupService;
import com.whm.xxljob.common.service.JobInfoService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * xxl-job 自动注册
 *
 * @author : 吴华明
 * @since 2025-12-04  13:30
 */

public class XxlJobAutoRegister implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(XxlJobAutoRegister.class);

    private ApplicationContext applicationContext;
    @Autowired
    private JobGroupService jobGroupService;
    @Autowired
    private JobInfoService jobInfoService;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.addJobGroup();
        this.addJobInfo();
    }

    /**
     * 添加任务组
     * <p>
     * 该方法用于检查并自动注册任务组。首先进行精确检查，如果检查失败，
     * 则尝试自动注册任务组，注册成功后记录相关信息。
     */
    private void addJobGroup() {
        // 检查任务组是否已存在，如果不存在则进行自动注册
        if (!this.jobGroupService.preciselyCheck()) {
            if (this.jobGroupService.autoRegisterGroup()) {
                logger.info("auto register xxl-job group success!");
            }
        }
    }

    /**
     * 添加作业信息
     *
     * <p>该方法的主要功能是扫描Spring容器中所有带有@XxlJob注解的方法，
     * 并根据@XxlRegister注解将作业信息注册到调度中心。</p>
     *
     * <p>执行流程：
     * 1. 获取作业组信息
     * 2. 获取Spring容器中所有Bean的名称
     * 3. 扫描所有Bean中带有@XxlJob注解的方法
     * 4. 对带有@XxlRegister注解的方法进行作业注册处理</p>
     */
    private void addJobInfo() {
        // 获取所有作业组信息，并取第一个作为默认作业组
        List<XxlJobGroup> jobGroups = this.jobGroupService.getJobGroup();
        XxlJobGroup xxlJobGroup = jobGroups.get(0);

        // 获取Spring应用上下文中所有Bean的名称
        String[] beanDefinitionNames = this.applicationContext.getBeanNamesForType(Object.class, false, true);
        // 扫描所有Bean中带有@XxlJob注解的方法
        List<Map.Entry<Method, XxlJob>> annotatedMethods = Arrays.stream(beanDefinitionNames).flatMap((beanDefinitionName) -> {
            Object bean = this.applicationContext.getBean(beanDefinitionName);
            return MethodIntrospector
                    .selectMethods(bean.getClass(), (MethodIntrospector.MetadataLookup<XxlJob>) (method) -> (XxlJob) AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class))
                    .entrySet().stream();
        }).collect(Collectors.toList());

        // 遍历所有带@XxlJob注解的方法，处理带@XxlRegister注解的方法进行作业注册
        annotatedMethods.forEach((methodXxlJobEntry) -> {
            Method executeMethod = methodXxlJobEntry.getKey();
            XxlJob xxlJob = methodXxlJobEntry.getValue();

            //自动注册
            if (executeMethod.isAnnotationPresent(XxlRegister.class)) {
                XxlRegister xxlRegister = executeMethod.getAnnotation(XxlRegister.class);
                List<XxlJobInfo> jobInfo = this.jobInfoService.getJobInfo(xxlJobGroup.getId(), xxlJob.value());

                // 如果已存在相同处理器的作业，则跳过注册
                if (CollUtil.isNotEmpty(jobInfo)) {
                    Optional<XxlJobInfo> first = jobInfo
                            .stream().filter((c) -> c.getExecutorHandler().equals(xxlJob.value()))
                            .findFirst();
                    if (first.isPresent()) {
                        return;
                    }
                }
                XxlJobInfo xxlJobInfo = this.createXxlJobInfo(xxlJobGroup, xxlJob, xxlRegister);
                // 添加新的作业信息
                this.jobInfoService.addJobInfo(xxlJobInfo);
            }
        });
    }


    /**
     * 创建XXL-JOB任务信息对象
     *
     * @param xxlJobGroup 任务分组信息
     * @param xxlJob 任务注解信息
     * @param xxlRegister 注册注解信息
     * @return 构建完成的XXL-JOB任务信息对象
     */
    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, XxlJob xxlJob, XxlRegister xxlRegister) {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc(xxlRegister.jobDesc());
        xxlJobInfo.setAuthor(xxlRegister.author());
        xxlJobInfo.setScheduleType("CRON");
        xxlJobInfo.setScheduleConf(xxlRegister.cron());
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setExecutorHandler(xxlJob.value());
        xxlJobInfo.setExecutorRouteStrategy(xxlRegister.executorRouteStrategy());
        xxlJobInfo.setMisfireStrategy("DO_NOTHING");
        xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setExecutorFailRetryCount(0);
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        xxlJobInfo.setTriggerStatus(xxlRegister.triggerStatus());
        return xxlJobInfo;
    }

}
