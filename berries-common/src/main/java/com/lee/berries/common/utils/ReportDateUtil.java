package com.lee.berries.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.http.client.utils.DateUtils;

public class ReportDateUtil {

    public final static String YEAR_FORMAT_KEY = "yyyy";

    public final static String MONTH_FORMAT_KEY = "yyyy-MM";

    public final static String DAY_FORMAT_KEY = "yyyy-MM-dd";

    public final static String TIME_FORMAT_KEY = "yyyy-MM-dd HH:mm:ss";

    public final static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat(YEAR_FORMAT_KEY);

    public final static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat(MONTH_FORMAT_KEY);

    public final static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(DAY_FORMAT_KEY);

    public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(TIME_FORMAT_KEY);

    /**
     * 获取昨天
     *
     * @return
     */
    public final static Date getYesToday() {
        try {
            Date today = DAY_FORMAT.parse(format(new Date()));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            return calendar.getTime();
        } catch (ParseException e) {
            //防御性容错
        }
        return null;
    }

    /**
     * 获取上个月的时间 2018-01格式
     *
     * @return
     */
    public final static String getLastMonth() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        return monthFormat(calendar.getTime());
    }

    /**
     * 获取当月的时间 2018-01格式
     *
     * @return
     */
    public final static String monthFormat(Date date) {
        return MONTH_FORMAT.format(date);
    }

    /**
     * 获取上个月的时间 2018-01格式
     *
     * @return
     */
    public final static String getLastYear() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        return yearFormat(calendar.getTime());
    }

    /**
     * 获取当月的时间 2018-01格式
     *
     * @return
     */
    public final static String yearFormat(Date date) {
        return YEAR_FORMAT.format(date);
    }

    /**
     * 获得一天的开始，例如2018-01-01 00:00:00
     *
     * @return
     */
    public final static Date getDayStart(Date date) {
        try {
            if (date == null) {
                date = new Date();
            }
            Date today = DAY_FORMAT.parse(format(date));
            return today;
        } catch (Exception e) {
            //防御性容错
        }
        return null;
    }

    /**
     * 获得一天的结束，例如2018-01-01 23:59:59
     *
     * @return
     */
    public final static Date getDayEnd(Date date) {
        try {
            if (date == null) {
                date = new Date();
            }
            Date today = DAY_FORMAT.parse(format(date));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            return calendar.getTime();
        } catch (Exception e) {
            //防御性容错
        }
        return null;
    }

    /**
     * 获取日期的格式，到天 2018-01-01
     *
     * @param date
     * @return
     */
    public final static String format(Date date) {
        return DAY_FORMAT.format(date);
    }

    /**
     * 获取日期的格式，到s 2018-01-01 00:00:00
     *
     * @param date
     * @return
     */
    public final static String timeFormat(Date date) {
        return TIME_FORMAT.format(date);
    }

    /**
     * 根据时间返回日期
     *
     * @param dateValue
     * @return
     */
    public final static Date parseDay(String dateValue) {
        try {
            return DAY_FORMAT.parse(dateValue);
        } catch (Exception e) {
            //防御性容错
        }
        return null;
    }

    /**
     * 给定年月日，返回当日的时间范围，当天天的0点和当天的午夜
     *
     * @param dateString yyyy-MM-dd, 2018-04-01
     * @return 时间数组，长度固定2,0=本月第一天0点，1=本月最后一天最后一秒
     */
    public final static Date[] getDayDate(String dateString) {
        try {
            Date[] dates = new Date[2];
            dates[0] = getDayStart(DAY_FORMAT.parse(dateString));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dates[0]);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            Date lastMonthDay = calendar.getTime();
            dates[1] = lastMonthDay;
            return dates;
        } catch (Exception e) {
            //防御性容错
        }
        return null;
    }

    /**
     * 给定年月，返回本月的时间范围，第一天的0点和最后一天的午夜
     *
     * @param dateString yyyy-MM, 2018-04
     * @return 时间数组，长度固定2,0=本月第一天0点，1=本月最后一天最后一秒
     */
    public final static Date[] getMonthDate(String dateString) {
        try {
            dateString += "-01";
            Date[] dates = new Date[2];
            dates[0] = getDayStart(DAY_FORMAT.parse(dateString));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dates[0]);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            Date lastMonthDay = calendar.getTime();
            dates[1] = lastMonthDay;
            return dates;
        } catch (Exception e) {
            //防御性容错
        }
        return null;
    }

    /**
     * 给定年，返回本年的时间范围，第一天的0点和最后一天的午夜
     *
     * @param year yyyy
     * @return 时间数组，长度固定2,0=本月第一天0点，1=本月最后一天最后一秒
     */
    public final static Date[] getYearDate(String year) {
        try {
            String startStr = year + "-01-01 00:00:00";
            String endStr = year + "-12-31 23:59:59";
            Date[] dates = new Date[2];
            dates[0] = TIME_FORMAT.parse(startStr);
            dates[1] = TIME_FORMAT.parse(endStr);
            return dates;
        } catch (Exception e) {
            //防御性容错
        }
        return null;
    }

    public final static Date add(Date date, int unit, int amount) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(unit, amount);
        return calendar.getTime();
    }

    /**
     * 两个日期相差的天数,向上取整
     *
     * @param to
     * @param from
     * @return
     */
    public static int differentDaysByMillisecond(Date from, Date to) {
        Double days = Math.ceil(((to.getTime() - from.getTime()) / (1000d * 3600 * 24)));
        return days.intValue();
    }


    public static void main(String[] args) {
    	System.out.println(parseDay("2018-19-55"));
    	DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(add(new Date(), Calendar.HOUR_OF_DAY, -2));
        System.out.println(TIME_FORMAT.format(getDayEnd(null)));

        Date[] dates = getYearDate("2018");
        System.out.println(TIME_FORMAT.format(dates[0]));
        System.out.println(TIME_FORMAT.format(dates[1]));

        System.out.println(getLastMonth());

        System.out.println("video.meu8".endsWith("video.meu8"));
    }
}
