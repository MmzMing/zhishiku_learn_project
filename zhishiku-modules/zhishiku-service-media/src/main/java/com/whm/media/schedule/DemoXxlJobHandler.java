package com.whm.media.schedule;


import com.whm.xxljob.common.annotation.XxlRegister;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 注解任务Demo
 *
 * @author : 吴华明
 * @since 2025-12-04  14:31
 */
@Slf4j
@Component
public class DemoXxlJobHandler {

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler1")
    @XxlRegister(
            cron = "0 0/5 * * * ?",
            author = "whm",
            jobDesc = "Demo测试")
    public void demoJobHandler1() {
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        String params = XxlJobHelper.getJobParam();
        XxlJobHelper.log("Demo任务开始执行，参数：当前序号 = {}, 总分片数 = {}, 参数 = {}", shardIndex, shardTotal, params);
        log.info("Demo任务开始执行，参数：当前序号 = {}, 总分片数 = {}, 参数 = {}", shardIndex, shardTotal, params);

    }
}
