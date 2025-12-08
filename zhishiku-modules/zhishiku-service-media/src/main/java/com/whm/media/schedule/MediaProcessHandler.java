package com.whm.media.schedule;


import com.whm.media.service.MediaProcessService;
import com.whm.xxljob.common.annotation.XxlRegister;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务审核视频
 *
 * @author : 吴华明
 * @since 2025-12-04  22:07
 */
@Slf4j
@Component
public class MediaProcessHandler {

    @Autowired
    MediaProcessService mediaProcessService;
    @XxlJob("mediaVideoProcessJobHandler")
    @XxlRegister(
            cron = "0 0 0/1 * * ?",
            author = "whm",
            jobDesc = "定时任务审核视频")
    public void mediaVideoProcessJobHandler() {
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        log.info("(定时任务审核视频)任务开始，参数：当前序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
        mediaProcessService.mediaProcessProcessCode(shardIndex, shardTotal);
        log.info("(定时任务审核视频)任务结束，参数：当前序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
    }
}
