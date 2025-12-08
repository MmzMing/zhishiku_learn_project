package com.whm.common.core.enums;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 日期转换枚举
 *
 * @author 吴华明
 */
public enum TimeUnitEnum {
    /**
     * ScpRunStatusEnum
     */
    SECOND("SEC", "秒") {
        @Override
        public Date addTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetSecond(date, resubmitInterval);
        }

        @Override
        public Date subTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetSecond(date, -resubmitInterval);
        }
    },
    MINUTE("MIN", "分钟") {
        @Override
        public Date addTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetMinute(date, resubmitInterval);
        }

        @Override
        public Date subTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetMinute(date, -resubmitInterval);
        }
    },
    HOUR("HR", "小时") {
        @Override
        public Date addTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetHour(date, resubmitInterval);
        }

        @Override
        public Date subTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetHour(date, -resubmitInterval);
        }
    },
    DAY("DAY", "天") {
        @Override
        public Date addTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetDay(date, resubmitInterval);
        }

        @Override
        public Date subTime(Date date, Integer resubmitInterval) {
            return DateUtil.offsetDay(date, -resubmitInterval);
        }
    },


    ;

    private final String code;
    private final String info;

    TimeUnitEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static TimeUnitEnum getEnumByCode(String code) {
        TimeUnitEnum[] values = values();
        for (TimeUnitEnum itemTypeEnum : values) {
            if (itemTypeEnum.getCode().equals(code)) {
                return itemTypeEnum;
            }
        }
        return TimeUnitEnum.DAY;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    /**
     * 添加时间
     *
     * @param date             源时间
     * @param resubmitInterval 时间间隔
     * @return 添加后的时间
     */
    public abstract Date addTime(Date date, Integer resubmitInterval);

    /**
     * 减少时间
     *
     * @param date             源时间
     * @param resubmitInterval 时间间隔
     * @return 减少后的时间
     */
    public abstract Date subTime(Date date, Integer resubmitInterval);
}