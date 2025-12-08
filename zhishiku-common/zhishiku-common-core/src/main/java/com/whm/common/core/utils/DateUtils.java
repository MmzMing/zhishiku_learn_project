package com.whm.common.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.whm.common.core.constant.DateConstant;
import com.whm.common.core.domain.dto.DateDTO;
import com.whm.common.core.enums.TimeUnitEnum;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 *
 * @author 吴华明
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils{


    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(DateConstant.YYYY_MM_DD);
    }

    public static String getTime() {
        return dateTimeNow(DateConstant.YYYY_MM_DD_HH_MM_SS);
    }

    public static String dateTimeNow() {
        return dateTimeNow(DateConstant.YYYYMMDDHHMMSS);
    }

    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static String dateTime(final Date date) {
        return parseDateToStr(DateConstant.YYYY_MM_DD, date);
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), DateConstant.parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 Date ==> LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 增加 Date ==> LocalDate
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 新增时间
     *
     * @param sourceDate   原时间
     * @param timeInterval 时间间隔
     * @param unitEnum     时间单位枚举
     * @return 新时间
     */
    public static Date addDate(Date sourceDate, Integer timeInterval, TimeUnitEnum unitEnum) {
        return TimeUnitEnum.getEnumByCode(unitEnum.getCode()).addTime(sourceDate, timeInterval);
    }

    /**
     * 减少时间
     *
     * @param sourceDate   原时间
     * @param timeInterval 时间间隔
     * @param unitEnum     时间单位枚举
     * @return 新时间
     */
    public static Date subDate(Date sourceDate, Integer timeInterval, TimeUnitEnum unitEnum) {
        return TimeUnitEnum.getEnumByCode(unitEnum.getCode()).subTime(sourceDate, timeInterval);
    }

    /**
     * 比较日期，获取小的日期
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 小的日期
     */
    public static Date compareDateGetMin(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        return date1.compareTo(date2) < 0 ? date1 : date2;
    }

    /**
     * 比较日期，获取大的日期
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 大的日期
     */
    public static Date compareDateGetMax(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        return date1.compareTo(date2) > 0 ? date1 : date2;
    }

    /**
     * 校验是否是yyyymm格式的年月
     *
     * @param str
     * @return
     */
    public static boolean checkIsYearMonth(String str) {
        if (null == str || str.trim().length() <= 0) {
            return false;
        }
        Pattern p = Pattern.compile(DateConstant.MONTH_REGEX);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 获取下一个工作日
     *
     * @param date     当前日期 yyyy-MM-dd
     * @param restDays 休息日列表
     * @return
     */
    public static Date getNextWorkingDay(Date date, List<Integer> restDays) {
        // 获取明天的日期
        Date nextDay = DateUtil.offsetDay(date, 1);

        while (isRestDay(nextDay, restDays)) {
            // 如果明天是休息日，则继续往后推一天
            nextDay = DateUtil.offsetDay(nextDay, 1);
        }
        return nextDay;
    }

    /**
     * 获取上一个工作日
     *
     * @param date     当前日期 yyyy-MM-dd
     * @param restDays 休息日列表
     * @return
     */
    public static Date getPreviousWorkingDay(Date date, List<Integer> restDays) {
        if (restDays == null || restDays.isEmpty()) {
            // 如果休息日列表为空，则返回前一天
            return DateUtil.offsetDay(date, -1);
        }
        // 获取前一天的日期
        Date previousDay = DateUtil.offsetDay(date, -1);
        while (isRestDay(previousDay, restDays)) {
            // 如果前一天是休息日，则继续往前推一天
            previousDay = DateUtil.offsetDay(previousDay, -1);
        }
        return previousDay;
    }

    /**
     * 是否为休息日
     *
     * @param date     日期
     * @param restDays 休息日列表
     * @return 是否为休息日
     */
    public static boolean isRestDay(Date date, List<Integer> restDays) {
        int dayOfWeek = DateUtil.dayOfWeek(date);
        if (CollectionUtil.isEmpty(restDays)) {
            return false;
        }
        return restDays.contains(dayOfWeek);
    }


    public static List<String> getDays(String startTime, String endTime) {
        // 日期集合
        List<String> dateList = new ArrayList<>();
        try {
            Date start = DateConstant.FORMAT.parse(startTime);
            Date end = DateConstant.FORMAT.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            // 日期加1(包含结束)
            tempEnd.add(Calendar.DATE, +1);
            while (tempStart.before(tempEnd)) {
                dateList.add(DateConstant.FORMAT.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception ignored) {
            // ignore
        }

        return dateList;
    }

    /**
     * 计算当前时间 往后推 2个时间每天的时间List
     *
     * @param amount 往后推几月
     * @return
     */
    public static List<DateDTO> getDays(Date time, Integer amount) {
        // 日期集合
        List<DateDTO> dateList = new ArrayList<>();
        //标识时间是第几周

        try {
            Date start = time;
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Date currentDate = time;
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(currentDate);
            tempEnd.add(Calendar.MONTH, amount);
            // 日期加1(包含结束)
            tempEnd.add(Calendar.DATE, +1);
            while (tempStart.compareTo(tempEnd) < 0) {
                DateDTO dateDTO = new DateDTO();
                // 获取星期几的值（1 表示星期天，2 表示星期一，以此类推）
                dateDTO.setDayOfWeek(tempStart.get(Calendar.DAY_OF_WEEK));
                dateDTO.setDate(tempStart.getTime());
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
                dateList.add(dateDTO);
            }

        } catch (Exception ignored) {
            // ignore
        }
        String flag = "";
        int index = 1;
        for (DateDTO dateDTO : dateList) {
            LocalDate toLocalDate = dateDTO.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<LocalDate> mondays = getMondays(toLocalDate);
            for (int i = 0; i < mondays.size(); i++) {
                if (toLocalDate.equals(mondays.get(i))) {
                    index = i + 1;
                    flag = DateConstant.DATE_FORMAT.format(dateDTO.getDate());
                }
            }
            dateDTO.setWeek(flag + DateConstant.FLAG + index);
        }
        return dateList;
    }

    public static String theDayBefore() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return DateConstant.FORMAT.format(time.getTime());

    }

    public static List<LocalDate> getMondays(LocalDate today) {
        List<LocalDate> mondays = new ArrayList<>();
        LocalDate firstMonday = today.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        LocalDate nextMonday = firstMonday;
        while (nextMonday.getMonth() == today.getMonth()) {
            mondays.add(nextMonday);
            nextMonday = nextMonday.plusWeeks(1);
        }
        return mondays;
    }
}
