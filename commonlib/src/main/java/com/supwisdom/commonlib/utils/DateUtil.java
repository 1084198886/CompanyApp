package com.supwisdom.commonlib.utils;

import org.apache.commons.lang3.time.FastDateFormat;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/16.
 * @desc 日期格式化
 */

public class DateUtil {
    private static FastDateFormat FORMAT_yyyyMMdd = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyyyMMdd");
    private static FastDateFormat FORMAT_yyMMddHHmmss = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyMMddHHmmss");
    private static FastDateFormat FORMAT_yyMMddHHmmss3 = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyMMdd_HHmmss");
    private static FastDateFormat FORMAT_HHmmss = org.apache.commons.lang3.time.FastDateFormat.getInstance("HHmmss");
    private static FastDateFormat FORMAT_MMdd = org.apache.commons.lang3.time.FastDateFormat.getInstance("MMdd");
    private static FastDateFormat FORMAT_MM_dd = org.apache.commons.lang3.time.FastDateFormat.getInstance("MM-dd");
    private static FastDateFormat FORMAT_yyyyMMddHHmmss = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static FastDateFormat FORMAT_yyyyMMddHHmm = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyyyMMddHHmm");
    private static FastDateFormat FORMAT_MMddHHmmyy = org.apache.commons.lang3.time.FastDateFormat.getInstance("MMddHHmmyy");
    private static FastDateFormat FORMAT_yyyy_MM_dd_HH_mm_ss = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private static FastDateFormat FORMAT_yyyy_MM_dd_HH_mm_ss_SSS = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    private static FastDateFormat FORMAT_HH_mm_ss_SSS = org.apache.commons.lang3.time.FastDateFormat.getInstance("HHmmssSSS");
    private static FastDateFormat FORMAT_yyyy_MM_dd_HH_mm = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    private static FastDateFormat FORMAT_yyyy_MM_dd = org.apache.commons.lang3.time.FastDateFormat.getInstance("yyyy-MM-dd");
    private static FastDateFormat FORMAT_HH_mm_ss = org.apache.commons.lang3.time.FastDateFormat.getInstance("HH:mm:ss");
    private static FastDateFormat FORMAT_HH_mm = org.apache.commons.lang3.time.FastDateFormat.getInstance("HH:mm");

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNow() {
        return FORMAT_yyyy_MM_dd_HH_mm_ss.format(new Date());
    }

    /**
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getNow5() {
        return FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.format(new Date());
    }

    /**
     * @return yyyy-MM-dd HH:mm
     */
    public static String getNow4() {
        return FORMAT_yyyy_MM_dd_HH_mm.format(new Date());
    }

    /**
     * @return yyMMddHHmmss
     */
    public static String getNow2() {
        return FORMAT_yyMMddHHmmss.format(new Date());
    }

    /**
     * @param dateTime yyyyMMddHHmmss
     * @return yyyyMMdd.HHmmss
     */
    public static String getNow4(String dateTime) {
        try {
            String str1 = dateTime.substring(0, 8);
            String str2 = dateTime.substring(8);
            return str1 + "." + str2;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @return yyMMdd_HHmmss
     */
    public static String getNow3() {
        return FORMAT_yyMMddHHmmss3.format(new Date());
    }

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFormat(Date date) {
        return FORMAT_yyyy_MM_dd_HH_mm_ss.format(date);
    }
    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFormat(Long timeInMillis) {
        return FORMAT_yyyy_MM_dd_HH_mm_ss.format(timeInMillis);
    }

    /**
     * @param datetime yyyyMMddHHmmss
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDatetimeFromNoFormat(String datetime) {
        try {
            return datetime.substring(0, 4) + "-" +
                    datetime.substring(4, 6) + "-" +
                    datetime.substring(6, 8) + " " +
                    datetime.substring(8, 10) + ":" +
                    datetime.substring(10, 12) + ":" +
                    datetime.substring(12);
        } catch (Exception ex) {
        }
        return "";
    }

    /**
     * @param datetime yyyyMMddHHmm
     * @return yyyy-MM-dd HH:mm
     */
    public static String getNowDatetimeFromNoFormat3(String datetime) {
        try {
            return datetime.substring(0, 4) + "-" +
                    datetime.substring(4, 6) + "-" +
                    datetime.substring(6, 8) + " " +
                    datetime.substring(8, 10) + ":" +
                    datetime.substring(10);
        } catch (Exception ex) {
        }
        return "";
    }

    /**
     * @param datetime yyyyMMddHHmmss
     * @return yy-MM-dd HH:mm:ss
     */
    public static String getNowDatetimeFromNoFormat2(String datetime) {
        try {
            return datetime.substring(2, 4) + "-" +
                    datetime.substring(4, 6) + "-" +
                    datetime.substring(6, 8) + " " +
                    datetime.substring(8, 10) + ":" +
                    datetime.substring(10, 12) + ":" +
                    datetime.substring(12);
        } catch (Exception ex) {
        }
        return "";
    }

    /**
     * @param date yyyyMMdd
     * @return yyyy-MM-dd
     */
    public static String getNowDateFromNoFormat(String date) {
        try {
            return date.substring(0, 4) + "-" +
                    date.substring(4, 6) + "-" +
                    date.substring(6, 8);
        } catch (Exception ex) {
        }
        return "";
    }

    /**
     * @param date yyyy-MM-dd HH:mm:ss
     * @return yyyyMMddHHmmss
     */
    public static String getNowDateFromNoFormat2(String date) {
        return date.replace("-", "")
                .replace(" ", "")
                .replace(":", "");
    }


    /**
     * @return HH:mm:ss
     */
    public static String getNowTimeWithArg(Date date) {
        try {
            return FORMAT_HH_mm_ss.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param time HHmm
     * @return HH:mm
     */
    public static String formatTime(String time) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(time.substring(0, 2)).append(":")
                    .append(time.substring(2, 4));
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @return MMdd
     */
    public static String getDateMMdd() {
        try {
            return FORMAT_MMdd.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return MM-dd
     */
    public static String getDate_MMdd() {
        try {
            return FORMAT_MM_dd.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return yyyy-MM-dd
     */
    public static String getNowDate() {
        return FORMAT_yyyy_MM_dd.format(new Date());
    }

    /**
     * @return yyyy-MM-dd
     */
    public static String getNowDate2(Date date) {
        try {
            return FORMAT_yyyy_MM_dd.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param dateStr yyyy-MM-dd
     * @return
     */
    public static Date getNowDate(String dateStr) {
        try {
            return FORMAT_yyyy_MM_dd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return HHmmssSSS
     */
    public static String getNowTime1() {
        return FORMAT_HH_mm_ss_SSS.format(new Date());
    }

    /**
     * @return HH:mm:ss
     */
    public static String getNowTime() {
        return FORMAT_HH_mm_ss.format(new Date());
    }

    /**
     * @return format
     */
    public static String getDayofWeek(int offsetWeek, int dayOfWeek, String format) {
        Calendar cal = Calendar.getInstance();
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        String retday;
        cal.add(Calendar.DATE, offsetWeek * 7);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        retday = org.apache.commons.lang3.time.FastDateFormat.getInstance(format).format(cal.getTime());
        return retday;
    }

    /**
     * 计算超时时间，精确到毫秒
     *
     * @param milliSeconds
     * @return L
     */
    public static long getExpireTime(long milliSeconds) {
        return new Date().getTime() + milliSeconds;
    }

    /**
     * @return yyyyMMddHHmmss
     */
    public static String getNowDateTimeNoFormat() {
        return FORMAT_yyyyMMddHHmmss.format(new Date());
    }

    /**
     * @return yyyyMMdd
     */
    public static String getNowDateNoFormat() {
        return FORMAT_yyyyMMdd.format(new Date());
    }

    /**
     * @param date
     * @return yyyyMMdd
     */
    public static String getNowDateNoFormat(Date date) {
        try {
            return FORMAT_yyyyMMdd.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return HHmmss
     */
    public static String getNowTimeNoFormat() {
        return FORMAT_HHmmss.format(new Date());
    }

    /**
     * @param datetime yyyyMMddHHmmss
     * @return ms
     * @desc 将日期格式的字符串转换为长整型
     */
    public static long dateFormatConvertToLong(String datetime) {
        try {
            return FORMAT_yyyyMMddHHmmss.parse(datetime).getTime();
        } catch (ParseException ex) {
            return 0L;
        }
    }

    /**
     * @param time ms
     * @return yyyyMMddHHmmss
     * @desc 将长整型数字转换为日期格式的字符串
     */
    public static String dateLongConvertToFormat(long time) {
        if (time > 0L) {
            return FORMAT_yyyyMMddHHmmss.format(new Date(time));
        }
        return "";
    }

    /**
     * @param time ms
     * @return MMddHHmmyy
     * @desc 将长整型数字转换为日期格式的字符串
     */
    public static String dateLongConvertToFormat2(long time) {
        if (time > 0L) {
            return FORMAT_MMddHHmmyy.format(new Date(time));
        }
        return "";
    }

    /**
     * @param datetime yyyy-MM-dd HH:mm:ss
     * @return
     * @desc 将日期格式的字符串转换为长整型
     */
    public static Date dateFormatConvertToDate(String datetime) {
        try {
            return FORMAT_yyyy_MM_dd_HH_mm_ss.parse(datetime);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * @param daynum 天数
     * @return 返回当前日期的第 daynum 天前的日期  yyyyMMdd
     */
    public static String getDayDateNoFormatBefore(int daynum) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -daynum);
        return FORMAT_yyyyMMdd.format(calendar.getTime());
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay yyyyMMdd
     * @return yyyyMMdd
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        try {
            Calendar c = Calendar.getInstance();
            Date date = FORMAT_yyyyMMdd.parse(specifiedDay);
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day - 1);
            return FORMAT_yyyyMMdd.format(c.getTime());
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 获得指定日期的前一天
     *
     * @param dayBefore 往前推迟的天数
     * @return Date
     */
    public static Date getSomeDayBefore(int dayBefore) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -dayBefore);
        return c.getTime();
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay yyyyMMdd
     * @return yyyyMMdd
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        try {
            Calendar c = Calendar.getInstance();
            Date date = FORMAT_yyyyMMdd.parse(specifiedDay);
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            return FORMAT_yyyyMMdd.format(c.getTime());
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getSpecifiedDayAfter1(String specifiedDay) {
        try {
            Calendar c = Calendar.getInstance();
            Date date = FORMAT_yyyy_MM_dd.parse(specifiedDay);
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            return FORMAT_yyyy_MM_dd.format(c.getTime());
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 分钟转化为未来时间yyyyMMddHHmm
     *
     * @param minute 分钟数
     * @return yyyyMMddHHmm
     */
    public static String format2FutureTime(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return FORMAT_yyyyMMddHHmm.format(calendar.getTime());
    }
    /**
     * @return yyyyMMddHHmm
     */
    public static String getNowDateTimeFormat() {
        return FORMAT_yyyyMMddHHmm.format(new Date());
    }

    /**
     * @param datetime yyyyMMddHHmm
     * @return 毫秒数
     */
    public static long formatDateTime(String datetime) {
        try {
            return FORMAT_yyyyMMddHHmm.parse(datetime).getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
