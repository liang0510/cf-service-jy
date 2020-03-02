package com.cf.kindergarten.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Project: cf
 * @Package: com.cf.xwxx.util
 * @Description: 日期格式转换工具类
 * @Author: 黄继波
 * @CreateDate: 2018/3/1 17:21
 * @ModificationHistory: （who whatTime doWhat）
 */

public class DateFormatUtil {

    private static final String[] arr = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    public static String getMothString(int i) {
        return arr[i - 1];
    }

    /**
     * @method_name: getActualMaximum
     * @param: [date]
     * @describe:根据时间获取当月有多少天数
     * @create_user: 周刘成
     * @create_date: 2018/3/16 0016  下午 1:42
     * @return: int
     **/
    public static int getActualMaximum(Date date) {

        return dateToLocalDateTime(date).getMonth().length(dateToLocalDate(date).isLeapYear());
    }

    /**
     * @method_name: dateToLocalDate
     * @param: [date]
     * @describe:Date 转 LocalDateTime
     * @create_user: 周刘成
     * @create_date: 2018/3/16 0016  下午 1:42
     * @return: java.time.LocalDate
     **/
    public static LocalDate dateToLocalDate(Date date) {

        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * @method_name: dateToLocalDateTime
     * @param: [date]
     * @describe: Date 转 LocalDateTime
     * @create_user: 周刘成
     * @create_date: 2018/3/16 0016  下午 1:42
     * @return: java.time.LocalDateTime
     **/
    public static LocalDateTime dateToLocalDateTime(Date date) {

        long nanoOfSecond = (date.getTime() % 1000) * 1000000;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, (int) nanoOfSecond, ZoneOffset.of("+8"));
        return localDateTime;
    }


    public static long dateToTimestamp(Date date) {
        /**
         * description: 日期类型 转换成 时间戳 ：Wed Jan 17 17:11:52 GMT+08:00 2018   to   1516180312769
         * method_name: dateToTimestamp
         * param : [date]
         * return: long
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:49
         */
        return date.getTime();
    }

    public static Date timestampToDate(long lt) {
        /**
         * description: 时间戳 转换成 日期类型：1516180312769   to   Wed Jan 17 17:11:52 GMT+08:00 2018
         * method_name: timestampToDate
         * param : [lt]
         * return: java.util.Date
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:53
         */
        return new Date(lt);
    }

    public static String dateTo_String(Date date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   2018-01-17 17:11:52
         * method_name: dateTo_String
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:53
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }

    public static Date _stringToDate(String dateStr) {
        /**
         * description: 字符串型 转换成 日期类型：2018-01-17 17:11:52    to    Wed Jan 17 17:11:52 GMT+08:00 2018
         * method_name: _stringToDate
         * param : [dateStr]
         * return: java.util.Date
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:53
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateTo_Str(Date date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   2018-01-17
         * method_name: dateTo_Str
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        return time;
    }

    /**
     * 获取每月最后一天
     * @return
     */
    public static String dateLastDayTo_Str() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String time = sdf.format(calendar.getTime());
        return time;
    }

    public static String dateToMonth_Str(Date date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   2018-01-17
         * method_name: dateTo_Str
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String time = sdf.format(date);
        return time;
    }

    public static String dateToYear_Str(Date date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   2018-01-17
         * method_name: dateTo_Str
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String time = sdf.format(date);
        return time;
    }

    public static String StrToYear_Str(String date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   2018-01-17
         * method_name: dateTo_Str
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar now =Calendar.getInstance();
        try {
            now.setTime(sdf.parse(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(now.getTime());
    }

    public static String StrToMonth_Str(String date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   2018-01-17
         * method_name: dateTo_Str
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar now =Calendar.getInstance();
        try {
            now.setTime(sdf.parse(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(now.getTime());
    }

    public static String dateToMonths_Str(Date date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   2018-01-17
         * method_name: dateTo_Str
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String time = sdf.format(date);
        return time;
    }

    public static Date _strToDate(String dateStr) {
        /**
         * description: 字符串型 转换成 日期类型：2018-01-17    to    Wed Jan 17 17:11:52 GMT+08:00 2018
         * method_name: _strToDate
         * param : [dateStr]
         * return: java.util.Date
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToString(Date date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   20180117171152
         * method_name: dateToString
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(date);
        return time;
    }

    public static String dateToString2(Date date) {
        /**
         * description: 日期类型 转换成 字符串型：Wed Jan 17 17:11:52 GMT+08:00 2018   to   20180117171152
         * method_name: dateToString
         * param : [date]
         * return: java.lang.String
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(date);
        return time;
    }

    public static Date stringToDate(String dateStr) {
        /**
         * description: 字符串型 转换成 日期类型：220180117171152    to    Wed Jan 17 17:11:52 GMT+08:00 2018
         * method_name: stringToDate
         * param : [dateStr]
         * return: java.util.Date
         * create_user: 黄继波
         * create_date: 2018/3/7
         * create_time: 09:54
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 得到几天前的时间
     * @param time
     * @param day
     * @return
     */
    public static String getDateBefore(String time,int day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now =Calendar.getInstance();
        try {
            now.setTime(sdf.parse(time));
            now.set(Calendar.DATE,now.get(Calendar.DATE)-day);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(now.getTime());
    }

    /**
     * 得到几天后的时间
     * @param time
     * @param day
     * @return
     */
    public static String getDateAfter(String time,int day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now =Calendar.getInstance();
        try {
            now.setTime(sdf.parse(time));
            now.set(Calendar.DATE,now.get(Calendar.DATE)+day);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(now.getTime());
    }


    public static String addDay(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*try {
            Date date1 = sdf.parse(sdf.format(calendar.getTime()));
            return  date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return sdf.format(calendar.getTime());
    }


    /**
     * 活跃时间内
     * 活跃时间是指每天6:00—8:30、12:00—14:00、20:00—22:30。
     * @return
     */
    public static  boolean ActiveTime(){

        Date now = new Date();
        int hours = now.getHours();
        int minutes = now.getMinutes();

        if ((hours == 8 && minutes <= 30) || (hours >= 6 && hours < 8))
        {
            return true;
        }
        else if(hours >= 12 && hours < 14){
            return true;
        }
        else if((hours == 22 && minutes <= 30) || (hours >= 20 && hours < 22)){
            return true;
        }
        return false;
    }


    public static String addYear(Date date,int year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,year);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*try {
            Date date1 = sdf.parse(sdf.format(calendar.getTime()));
            return  date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return sdf.format(calendar.getTime());
    }

    public static String addYears(String time,int year){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(time));
            calendar.add(Calendar.YEAR,year);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String currentDayOne(){

        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String first = sdf.format(c.getTime());
        return first;
    }



    /**
     * 得到上个月
     * @return
     */
    public static String getMonthBefore(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar now =Calendar.getInstance();
        try {
            now.setTime(date);
            now.set(Calendar.MONTH,now.get(Calendar.MONTH) -1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sdf.format(now.getTime());
    }

    /**
     * 一周时间
     * @return
     */
    public static List<String> getDayMinus7() {

        List<String> list = new LinkedList<>();
        LocalDate localDate = null;
        for (int i = 0; i < 7; i++) {
            localDate = LocalDate.now().minusDays(i);
            list.add(localDate.toString());
        }
        return list;
    }


    /**
     * 获取月份每一天
     * @return
     */
    public static List<String> getDayMonthAll(String date) {

        List<String> list = new LinkedList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar now =Calendar.getInstance();
        try {
            now.setTime(sdf.parse(date));
            int day = now.getActualMaximum(Calendar.DATE);
            for (int i = 1; i <= day; i++) {
                if(i < 10){
                    list.add(date + "-0" +i);
                }else{
                    list.add(date +"-" +i);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }



    /**
     * 得到上个月
     * @return
     */
    public static String perThridMouthTimeBefore(int num){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar now =Calendar.getInstance();
        try {
            now.setTime(new Date());
            now.set(Calendar.MONTH,now.get(Calendar.MONTH) - num);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sdf.format(now.getTime());
    }


    public static void main(String[] args) {

        String time = getDateAfter("2019-03-10",50);
        System.out.println(time);
        String last = dateLastDayTo_Str();
        String currentTime = DateFormatUtil.dateTo_Str(new Date()); //当前时间
        System.out.println(currentTime.compareTo(time));
        System.out.println(currentTime.compareTo(last));
        System.out.println(addYears("2019-05-01",-1));

        System.out.println(addDay(new Date(),-1));
        System.out.println("111:" +getDateBefore(DateFormatUtil.dateTo_Str(new Date()),3));

        Date str1 = _strToDate("2019-07-07");

        Date str2 = _strToDate("2019-07-08");

        System.out.println(str1.before(str2));
        System.out.println(getDayMinus7());

        System.out.println(getDayMonthAll("2019-08"));


    }


}
