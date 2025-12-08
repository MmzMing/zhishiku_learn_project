package com.whm.xxljob.common.model;


import lombok.Data;

import java.util.Date;

/**
 * 任务 信息
 *
 * @author : 吴华明
 * @since 2025-12-04  13:32
 */
@Data
public class XxlJobInfo {
    private int id;
    private int jobGroup;
    private String jobDesc;
    private Date addTime;
    private Date updateTime;
    private String author;
    private String alarmEmail;
    private String alarmDing;
    private String scheduleType;
    private String scheduleConf;
    private String misfireStrategy;
    private String executorRouteStrategy;
    private String executorHandler;
    private String executorParam;
    private String executorBlockStrategy;
    private int executorTimeout;
    private int executorFailRetryCount;
    private String glueType;
    private String glueSource;
    private String glueRemark;
    private Date glueUpdatetime;
    private String childJobId;
    private int triggerStatus;
    private long triggerLastTime;
    private long triggerNextTime;
}
