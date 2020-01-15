package com.cf.kindergarten.util;

import com.alibaba.fastjson.JSON;
import com.cf.core.base.mybatis.cf_base.SysRelevantAuth;
import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.core.common.model.ModelUtil;
import com.cf.core.common.model.ResultModel;
import com.cf.core.util.CommonUtil;
import com.cf.core.util.DateUtil;
import com.cf.core.util.ToolUtil;
import com.cf.core.wechat.WechatUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DMS工具包
 */
public class DmsUtil {
    public static String  callcenterStatus ="0";//热线状态0 是连接失败，1是正常
    public static HashMap<String,String>  lineMap =new HashMap<>();//热线状态0 掉电，1是正常  "line1":"1"
    public static HashMap<String, String> szccflc;
    private static Properties pros = new Properties();
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    public static double R = 6378137.0;//取WGS84标准参考椭球中的地球长半径(单位:m)
    /**
     * 获取以type为开头的时间戳编码
     * @param type
     * @return
     */
    public static String getCode(String type){
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        return type+date;
    }
    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        return date;
    }
    /***
     * method_name: retainDecimals
     * param: [v1, scale]
     * return java.lang.String
     * describe:保留2位小数
     * create_user: SZC
     * create_date: 2019-01-17
     * create_time: 17:50
     **/

    public static String retainDecimals(String v1,int scale)
    {
        if (scale < 0)
        {
            scale=0;
        }
        if(ToolUtil.isEmpty(scale)){
            scale=0;
        }
        v1 = v1.trim();
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        try {
            return b1.divide(new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP).toString();
        }catch(Exception e){
            return BigDecimal.ZERO.toString();
        }
    }



    /**
     *
     * @discription 浮点数加法
     * @param v1
     * @param v2
     * @return
     */
    public static String add(String v1, String v2)
    {
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            v2 = "0.0";
        }
        v1 = v1.trim();
        v2 = v2.trim();
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    /**
     *
     * @discription 浮点数减法，第一个参数减去第二个参数
     * @param v1
     * @param v2
     * @return
     */
    public static String sub(String v1, String v2)
    {
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            v2 = "0.0";
        }
        v1 = v1.trim();
        v2 = v2.trim();
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }

    /**
     *
     * @discription 浮点数乘法
     * @param v1
     * @param v2
     * @return
     */
    public static String mul(String v1, String v2)
    {
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            v2 = "0.0";
        }
        v1 = v1.trim();
        v2 = v2.trim();
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return retainDecimals(b1.multiply(b2).toString(),2);
    }

    /**
     *
     * @discription 除法，按指定位数四舍五入
     * @param v1
     * @param v2
     * @param scale 小数位数（精度）
     * @return
     */
    public static String div(String v1, String v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("精度不能小于0！");
        }
        v1 = v1.trim();
        v2 = v2.trim();
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            return BigDecimal.ZERO.toString();
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        try {
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
        }catch(Exception e){
            return BigDecimal.ZERO.toString();
        }
    }

    /**
     *
     * @discription 除法  不执行小数位处理
     * @param v1
     * @param v2
     * @param
     * @return
     */
    public static String div(String v1, String v2)
    {

        v1 = v1.trim();
        v2 = v2.trim();
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            return BigDecimal.ZERO.toString();
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        try {
            return b1.divide(b2,4, BigDecimal.ROUND_HALF_UP).toPlainString();
        }catch(Exception e){
            return BigDecimal.ZERO.toString();
        }
    }
    /**
     * 描述:数字比较
     * @param
     * @return
     * create_user: xujian
     **/
    public static boolean isDoubleEqual(String v1, String v2){
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            v2 = "0.0";
        }
        v1 = v1.trim();
        v2 = v2.trim();
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        int result = b1.compareTo(b2);
        return result == 0;
    }
    //判断v1是否小于v2
    public static boolean xiaoyu(String v1, String v2){
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            v2 = "0.0";
        }
        v1 = v1.trim();
        v2 = v2.trim();
        Double b1 = Double.parseDouble(v1);
        Double b2 = Double.parseDouble(v2);
        if(b1<=b2){
            return true;
        }else{
            return false;
        }

    }

    //判断v1是否大于v2
    public static boolean dayu(String v1, String v2){
        if(ToolUtil.isEmpty(v1)){
            v1 = "0.0";
        }
        if(ToolUtil.isEmpty(v2)){
            v2 = "0.0";
        }
        v1 = v1.trim();
        v2 = v2.trim();
        Double b1 = Double.parseDouble(v1);
        Double b2 = Double.parseDouble(v2);
        if(b1>=b2){
            return true;
        }else{
            return false;
        }

    }


    /**
     * 根据格式获取当前日期
     * @param pattern yyyy-MM-dd
     * @return
     */
    public static String getCurrentDate(String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return  format.format(new Date());
    }

    /**
     * 获取根据日期获取其他日期
     * @param strData 传入日期
     * @param i  日期差距天数
     * @return
     */
    public static String getDateByDate(String strData,int i) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(strData);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + i);

        return format.format(c.getTime());
    }



    /**
     * 计算日期，date 传入日期，dayAmount 增加天数
     * @param date
     * @param dyAmount
     * @return
     */
    public static Date culculateDay(Date date,int dyAmount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, dyAmount);
        date = calendar.getTime();
        return date;
    }
    /**
     * 计算日期，date 传入日期，dayAmount 增加年数
     * @param date
     * @param dyAmount
     * @return
     */
    public static Date culculateYear(Date date,int dyAmount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, dyAmount);
        calendar.add(calendar.DAY_OF_YEAR, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 计算两天之间相隔天数
     * @param beginDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static long culDateSpace(String beginDate,String endDate) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date beginD= format.parse(beginDate);

        Date endD= format.parse(endDate);

        long day=(endD.getTime()-beginD.getTime())/(24*60*60*1000);
        System.out.println("相隔的天数="+day);
        return day;

    }

    /**
     * 获取当前日期时间
     * @return
     */
    public static String getCurrentDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDay = format.format(new Date());
        return currentDay;
    }

    /**
     * 获取当月第一天
     * @return
     */
    public static String getMonthFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH,1);
        String first = format.format(c.getTime());
        return first;
    }

    /**
     * 获取当月最后一天
     * @return
     */
    public static String getMonthLastDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }
    /***
     * method_name: getFisrtDayOfMonth
     * param: [year, month]
     * return java.lang.String
     * describe:获取某月的第一天
     * create_user: SZC
     * create_date: 2019-05-24
     * create_time: 13:43
     **/

    public static String getFisrtDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }
    /***
     * method_name: getLastDayOfMonth
     * param: [year, month]
     * return java.lang.String
     * describe:获取某月的最后一天
     * create_user: SZC
     * create_date: 2019-05-24
     * create_time: 13:43
     **/

    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }
    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static String getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        String str="";
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
            str = longSdf.format(now);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static String getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        String str="";
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
            str = longSdf.format(now);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取浮点数
     * @param obj
     * @return
     */
    public static Double getDouble(Object obj){
        if(ToolUtil.isEmpty(obj)){
            return 0.0;
        }else{
            obj = (obj+"").trim();
            return Double.parseDouble(obj+"");
        }
    }

    /**
     * @description 封装返回信息,并控制是否显示成功或出错信息
     * @author hh
     * @date 10:05 2018/6/25
     */
    public static ResultModel getReturnModel(HashMap resultMap, Object param, Boolean showSuccess, Boolean showError){
        if(ToolUtil.isEmpty(resultMap)){
            return null;
        }else{
            if(DmsConstants.RESULT_SUCCESS.equals(resultMap.get("status"))){
                if(showSuccess){
                    return ResultModel.success(param);
                }
                else{
                    return ResultModel.successQuery(param);
                }
            }else{
                if(showError){
                    return new ResultModel("-10001",resultMap.get("msg")+"");
                }
                else{
                    return ResultModel.successQuery(resultMap);
                }
            }
        }
    }
    /***
     * method_name: compareStr
     * param: [str1, str2]
     * return int
     * describe:小于、等于或大于 val 时，返回 -1、0 或 1。
     * create_user: SZC
     * create_date: 2018/8/22
     * create_time: 16:31
     **/

    public static int compareStr(String str1,String str2){
        if(ToolUtil.isEmpty(str1)){
            str1 = "0.0";
        }
        if(ToolUtil.isEmpty(str2)){
            str2 = "0.0";
        }
        BigDecimal data1 = new BigDecimal(str1);
        BigDecimal data2 = new BigDecimal(str2);
        return data1.compareTo(data2);
    }
    //读取配置文件
    public static String getProperties(String key,String config){
        InputStream in = null;
        try {
            String path="D:/eap/muban";
            in = new BufferedInputStream(new FileInputStream(path+"/"+config));
            pros.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String value = pros.getProperty(key);
        return value;
    }

    public static Boolean isStringNull(String s) {
        if (s == null || s.trim().equals("") || s.trim().equals("null"))
            return true;
        return false;
    }

    public static Boolean isStringNotNull(String s) {
        return !isStringNull(s);
    }

    public static double toDouble(String o) {
        double res = 0.0;
        try {
            if(DmsUtil.isStringNotNull(o)){
                res = Double.parseDouble(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    /***
     * method_name: fromJsonToValue
     * param: [str, json]
     * return java.lang.String
     * describe:根据传过来的参数获取参数对应Json中的值
     * create_user: SZC
     * create_date: 2018/10/26
     * create_time: 14:55
     **/
    public static String fromJsonToValue(String str,String json) {
        if(ToolUtil.isNotEmpty(json)){
            Map mapType=JSON.parseObject(json);
            for(Object obj : mapType.keySet()){
                if((obj+"").equals(str)){
                    return mapType.get(obj+"")+"";
                }
            }
        }
        return "";
    }

    /***
     * method_name: fromJsonToValue
     * param: [str, json]
     * return java.lang.String
     * describe:根据传过来的参数获取参数对应Json中的值
     * create_user: SZC
     * create_date: 2018/10/26
     * create_time: 14:55
     **/
    public static Map fromJsonToMap(String json) {
        Map mapType=new HashMap();
        if(ToolUtil.isNotEmpty(json)){
            mapType=JSON.parseObject(json);
            return mapType;
        }
        return mapType;
    }
    public static String isNullTo0(String str) {
        if(ToolUtil.isNotEmpty(str)){
            return str;
        }else{
            return "0";
        }
    }

    public static void sendTemplateMessage(String loginName,String loginPassword,String wechatOpenId,String template_id,String functionCode,String id,Map dataMap) {
        if(isStringNotNull(loginName) && isStringNotNull(wechatOpenId)){
            Map wechatMap = new HashMap();
            wechatMap.put("touser",wechatOpenId);
            wechatMap.put("template_id",isStringNull(template_id)?DmsConstants.TEMPLATE_ID:template_id);
            wechatMap.put("url","&functionCode="+functionCode+"&id="+id);
            wechatMap.put("data",dataMap);
            wechatMap.put("loginName", loginName);
            wechatMap.put("loginPassword", loginPassword);
            WechatUtil.sendTemplateMessage(wechatMap);
        }
    }

    public static Map packWechatData(String First,String keyword1,String keyword2,String remark) {
        Map dataMap = new HashMap();
        Map FirstMap = new HashMap();
        FirstMap.put("value", First);
        FirstMap.put("color", "#173177");
        dataMap.put("first", FirstMap);
        Map keyword1Map = new HashMap();
        keyword1Map.put("value", keyword1);
        keyword1Map.put("color", "#173177");
        dataMap.put("keyword1", keyword1Map);
        Map keyword2Map = new HashMap();
        keyword2Map.put("value", isStringNull(keyword2)?ModelUtil.getUserInfo().getName():keyword2);
        keyword2Map.put("color", "#173177");
        dataMap.put("keyword2", keyword2Map);
        Map remarkMap = new HashMap();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        remarkMap.put("value", isStringNull(remark)?df.format(new Date()):remark);
        remarkMap.put("color", "#173177");
        dataMap.put("remark", remarkMap);
        return  dataMap;
    }

    /**
     * @description 插入权限
     * @author lc
     * @param
     * @return
     * @date 2018年12月22日13:54:25
     */
    public static SysRelevantAuth getSysRelevatAuth(String fid,SysUser su,String funcode) {
        SysRelevantAuth sysRelevantAuth = new SysRelevantAuth();
        sysRelevantAuth.setId(CommonUtil.generateUUID());
        sysRelevantAuth.setFid(fid);
        sysRelevantAuth.setType("001");
        sysRelevantAuth.setCreateDate(DateUtil.getDay());
        sysRelevantAuth.setCreateTime(DateUtil.getTime());

        if (ToolUtil.isNotEmpty(su)) {
            sysRelevantAuth.setCorpId(su.getOrgId());
            sysRelevantAuth.setCorpCode(su.getOrgCode());
            sysRelevantAuth.setDeptId(su.getDeptId());
            sysRelevantAuth.setDeptCode(su.getDeptLongCode());
            sysRelevantAuth.setUserId(su.getId());
            sysRelevantAuth.setFunCode(funcode);
        }
        return sysRelevantAuth;
    }
    /***
     * method_name: isCustomerOrStaffmember
     * param: [userType]
     * return java.lang.Boolean
     * describe:验证是厂家还是经销商登陆
     * create_user: SZC
     * create_date: 2018-12-28
     * create_time: 10:30
     **/

    public static Boolean isCustomerOrStaffmember(String userType){
        Boolean flag=false;
        if(DmsConstants.CUSTOMER.equals(userType)||DmsConstants.STAFFMEMBER.equals(userType)||DmsConstants.SUBSIDIARYMANAGER.equals(userType)) {//经销商类型和厂家
            flag=true;
        }
        return flag;
    }
    /***
     * method_name: isCustomerOrStaffmember
     * param: [userType]
     * return java.lang.Boolean
     * describe:验证是厂家
     * create_user: SZC
     * create_date: 2018-12-28
     * create_time: 10:30
     **/
    public static Boolean isStaffmember(String userType){
        Boolean flag=false;
        if(DmsConstants.CUSTOMER.equals(userType)||DmsConstants.STAFFMEMBER.equals(userType)||DmsConstants.SUBSIDIARYMANAGER.equals(userType)) {//经销商类型和厂家
            flag=true;
        }
        return flag;
    }
    /**
     * 描述:获取日期是第几周
     * @param
     * @return
     * create_user: xujian
     * create_date: 2019-01-19
     * create_time: 15:48
     **/
    public static String getWeekByDay(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR)+"";
    }
    /**
     * 描述:获取日期所在的那周周日
     * @param
     * @return
     * create_user: xujian
     * create_date: 2019-01-19
     * create_time: 15:49
     **/
    public static String getSunDayByDay(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.add(Calendar.DATE, 7-dayWeek);
        Date sundayDate = calendar.getTime();
        String sunDay = format.format(sundayDate);
        return sunDay;
    }
    /**
     * 描述:获取日期所在的那周周日
     * @param
     * @return
     * create_user: xujian
     * create_date: 2019-01-19
     * create_time: 15:49
     **/
    public static String getMonthByDay(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return (calendar.get(Calendar.MONTH)+1)+"";
    }

    /**
     * 根据当前日期获得上周一是几号
     * @param date
     * @return
     */
    public static Date getLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得上周二是几号
     * @param date
     * @return
     */
    public static Date getLastWeekTuesday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -6);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得上周三是几号
     * @param date
     * @return
     */
    public static Date getLastWeekWednesday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -5);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得上周四是几号
     * @param date
     * @return
     */
    public static Date getLastWeekThursday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -4);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得上周五是几号
     * @param date
     * @return
     */
    public static Date getLastWeekFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -3);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得上周六是几号
     * @param date
     * @return
     */
    public static Date getLastWeekSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }


    /**
     * 根据当前日期获得上周日是几号
     * @param date
     * @return
     */
    public static Date getLastWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得本周一是几号
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得本周二是几号
     * @param date
     * @return
     */
    public static Date getThisWeekTuesday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得本周三是几号
     * @param date
     * @return
     */
    public static Date getThisWeekWednesday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 2);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得本周四是几号
     * @param date
     * @return
     */
    public static Date getThisWeekThursday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 3);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得本周五是几号
     * @param date
     * @return
     */
    public static Date getThisWeekFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 4);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得本周六是几号
     * @param date
     * @return
     */
    public static Date getThisWeekSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 5);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得本周日是几号
     * @param date
     * @return
     */
    public static Date getThisWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }

    /**
     * 根据当前日期获得下周一是几号
     * @param date
     * @return
     */
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }


    public static String getCurrentBeforeNum(String pattern, int num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, num);
        return dateToString(cal.getTime(), pattern);
    }

    /**
     * 将日期字符串转化为日期
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date stringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return myDate;
    }
    /**
     * 将日期转化为日期字符串
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateString;
    }

    /**
     * 获取SimpleDateFormat
     * @param pattern 日期格式
     * @return
     */
     public static SimpleDateFormat getDateFormat(String pattern) {
         if (pattern != null && pattern.equals("")) {
            throw new RuntimeException("无效日期格式");
         }
         SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
         return dateFormat;
     }



    public static void main(String[] args) {
        System.out.println(DmsUtil.getCurrentBeforeNum("yyyy-MM-dd",0));
        DmsUtil.getSunDayByDay("2019-12-11");
        System.out.println(DmsUtil.getSunDayByDay("2019-12-11"));
        // String str=checkPhone("918012019949");
        // System.out.println(ynMatcher("015862110335","^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$"));
       /* try {
            System.out.println(DmsUtil.dateToStr(DmsUtil.strToDate("2019-04-06 00:00:00","yyyy-MM-dd"),"yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       System.out.print(getFisrtDayOfMonth(2019,2)+"        "+getLastDayOfMonth(2019,3));

    }

    //为一个日期字符串增加秒
    public static String addSecondsForTime(String time,int seconds){
        if(ToolUtil.isEmpty(time)) return seconds+"";
        Date date = DateUtil.parseTime(time);
        if (date == null)
            return seconds+"";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        date = cal.getTime();
        return DateUtil.getTime(date);
    }
    //判断某年的2月份天数
    public static int judgeDay(int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, 2, 1);// year年的3月1日
        c.add(Calendar.DAY_OF_MONTH, -1);//将3月1日往左偏移一天结果是2月的天数
        return c.get(Calendar.DAY_OF_MONTH);
    }
    /***
     * method_name: shardingToMap
     * param: [list, slotPosition]
     * return java.util.Map<java.lang.String,java.util.List<T>>
     * describe:将一个大集合等分成若干个小集合  小集合数量list.size()/slotPosition(向上取整)  存放到MAP里
     * create_user: SZC
     * create_date: 2019-02-13
     * create_time: 23:06
     **/

    public static <T> Map<String, List<T>> shardingToMap(List<T> list, int slotPosition) {

        // 返回数据集合
        Map<String, List<T>> hmap = new HashMap<String, List<T>>();
        int list_size = list.size();
        int thread_exec_count = slotPosition;// 每个线程分的数据数
        int thread_num = 1;// 根据数据量 求出线程数
        int list_remainder_num = 0;// 集合余数
        // 先把list集合进行分片处理
        if (list_size > thread_exec_count) {
            thread_num = list_size / thread_exec_count;
            if (list_size % thread_exec_count > 0) {// 有余数
                // 求集合余数
                list_remainder_num = thread_num * thread_exec_count;
                thread_num++;
            }
        }

        if (thread_num == 1) {
            // 如果1个槽位可以装下，直接返回一个
            hmap.put("1", list);
        }
        int key = 0;
        int j = 1;
        List<T> temp = new ArrayList<>();
        for (T t : list) {
            temp.add(t);
            // 每过一个线程，K就变+1,根据线程来归纳数据
            if (j >= thread_exec_count) {
                // System.out.println("temp:" + temp);
                hmap.put(""+key+"", temp);
                temp = new ArrayList<>();
                j = 0;
                key++;// key++ 相当于在化分大集合
            }
            j++;
        }

        // 余数加入map
        hmap.put("-1", list.subList(list_remainder_num, list_size));
        return hmap;
    }

    /**
     * @description 两个string类型的日期比较大小
     * @author lc
     * @param
     * @return
     * @date 2019年3月5日10:57:13
     */
    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(ToolUtil.isEmpty(DATE1)){
            DATE1="1900-01-01 00:00:00";
        }
        if(ToolUtil.isEmpty(DATE2)){
            DATE2="1900-01-01 00:00:00";
        }
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /***
     * method_name: formatStrToDatetOsTR
     * param: [DATE1]
     * return java.lang.String
     * describe:String的日期格式装成其他的格式
     * create_user: SZC
     * create_date: 2019-04-11
     * create_time: 15:35
     **/

    public static String formatStrToDatetOStr(String DATE1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1=new Date();
        try {
            dt1 = df.parse(DATE1);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return DateUtil.getTime(dt1);
    }
    /***
     * method_name: strToDate
     * param: [str]
     * return java.util.Date
     * describe:str 转Date
     * create_user: SZC
     * create_date: 2019-04-12
     * create_time: 8:29
     **/

    public static Date strToDate(String str,String sf)throws  Exception {
        if(sf==""){
            sf="yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(sf);
        return sdf.parse(str);
    }
    /***
     * method_name: dateToStr
     * param: [dateDate, sf]
     * return java.lang.String
     * describe:Date 转str
     * create_user: SZC
     * create_date: 2019-04-12
     * create_time: 8:29
     **/

    public static String dateToStr(Date dateDate,String sf) {
        if(sf == ""){
            sf = "yyyy-MM-dd";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(sf);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * @description 两个string类型日期之间相差的天数
     * @author lc
     * @param
     * @return
     * @date 2019年3月5日10:59:17
     */
    public static int daysBetween(String smdate,String bdate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        try{
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        }catch(Exception e){
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @description 两个string类型日期相差的小时数
     * @author lc
     * @param
     * @return
     * @date 2019年3月5日10:59:52
     */
    public static int hoursBetween(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        try{
            cal.setTime(sdf.parse(startTime));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(endTime));
            time2 = cal.getTimeInMillis();
        }catch(Exception e){
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     ?? * 获取两个日期相差的月数
     ?? * @param d2? 较大的日期
     ?? * @param d1? 较小的日期
     ?? * @return 如果d1>d2返回 月数差 否则返回0
     ?? */
    public static int getMonthDiff(String d1, String d2){
        int i = 0;
        try {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            //将String日期转换成date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            c1.setTime(date1);
            c2.setTime(date2);
            //判断两个日期的大小
            if(c2.getTimeInMillis() < c1.getTimeInMillis())
                return 0;
            int year1 = c1.get(Calendar.YEAR);
            int year2 = c2.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH);
            int month2 = c2.get(Calendar.MONTH);
            int day1 = c1.get(Calendar.DAY_OF_MONTH);
            int day2 = c2.get(Calendar.DAY_OF_MONTH);
            // 获取年的差值 假设 d1 = 2015-9-30?? d2 = 2015-12-16
            int yearInterval = year2 - year1;
            // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
            if(month2 < month1 || month1 == month2 && day2 < day1)
                yearInterval --;
            // 获取月数差值
            int monthInterval = (month2 + 12) - month1 ;
            if(day2 > day1)
                monthInterval ++;
            monthInterval %= 12;
            i = yearInterval * 12 + monthInterval;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    /***
     * method_name:
     * param:
     * return
     * describe:新增用户
     * create_user: wuhao
     * create_date: 2019/3/14
     * create_time: 16:31
     **/
    public  static String getString(Object s){
        String s1=s+"";
        if("null".equals(s1)){
            s1="";
        }
        return s1;
    }

    public static String removeLast(String s1){
        if(s1.contains("/") || s1.contains(",")){
            s1=s1.substring(0,s1.length()-1);
        }
        return s1;
    }
    /***
     * method_name: compare_date
     * param: [DATE1, DATE2]
     * return int
     * describe:两个字符串日期比较，返回小的
     * create_user: SZC
     * create_date: 2019-03-15
     * create_time: 9:01
     **/

    public static String compareDateToSmail(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        if(ToolUtil.isEmpty(DATE1)){
            return DATE2;
        }
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return DATE2;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return DATE1;
            } else {
                return DATE1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return DATE1;
        }
    }
    /***
     * method_name: compare_date
     * param: [DATE1, DATE2]
     * return int
     * describe:两个字符串日期比较，返回大的
     * create_user: SZC
     * create_date: 2019-03-15
     * create_time: 9:01
     **/
    public static String compareDateToBig(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        if(ToolUtil.isEmpty(DATE1)){
            return DATE2;
        }
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return DATE1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return DATE2;
            } else {
                return DATE1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return DATE1;
        }
    }

    public  static Boolean ynMatcher(String str, String matcher){
        //String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(matcher);
        //正则表达式的匹配器
        Matcher m = p.matcher(str);
        //进行正则匹配
        return m.matches();

    }

    public  static String checkPhone(Object s,String outgoingNumber1,String outgoingNumber2){
        String s1=s+"";
        if("null".equals(s1)){
            s1="";
        }else{

            int i=-1;
            if(ToolUtil.isNotEmpty(outgoingNumber1)){
                i=s1.indexOf(outgoingNumber1);
            }
            System.out.print("------------------"+i);
            if(i==0){
                s1=s1.substring(i+2,s1.length());
            }else{
                i=-1;
                if(ToolUtil.isNotEmpty(outgoingNumber1)){
                    i=s1.indexOf(outgoingNumber2);
                }
                if(i==0){
                    s1=s1.substring(i+1,s1.length());
                }
            }
        }
        return s1;
    }
/***
 * method_name: backMonth
 * param: [monthStr]
 * return java.lang.String
 * describe:根据字典获取月份
 * create_user: SZC
 * create_date: 2019-05-24
 * create_time: 14:01
 **/

    public static String backMonthForDictionary(String monthStr){
        String str="";
        switch (monthStr){
            case "0001":
                str="1";
                break;
            case "0002":
                str="2";
                break;
            case "0003":
                str="3";
                break;
            case "0004":
                str="4";
                break;
            case "0005":
                str="5";
                break;
            case "0006":
                str="6";
                break;
            case "0007":
                str="7";
                break;
            case "0008":
                str="8";
                break;
            case "0009":
                str="9";
                break;
            case "0010":
                str="10";
                break;
            case "0011":
                str="11";
                break;
                default:
                    str="12";
                    break;
        }
        return str;
    }

    /**
     * 时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static int getDaySub(String beginDateStr,String endDateStr){
        int day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;

        try {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
            day=Integer.parseInt(String.valueOf((endDate.getTime()-beginDate.getTime())/(24*60*60*1000)));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return day;
    }


    /**
     * json 转 HashMap param Json String
     */
    public static HashMap JsonToHashMap(String jsonStr) {
        HashMap hashmap = new HashMap();
        try {
            JSONArray jsonArray = new JSONArray(replaceStr(jsonStr));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonJ = jsonArray.getJSONObject(i);
                Iterator localIterator = jsonJ.keys();
                while (localIterator.hasNext()) {
                    String name = localIterator.next() + "";
                    String value = jsonJ.get(name) + "";
                    hashmap.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return hashmap;
    }

    /**
     * 去json中特殊字符
     * .replace("\"", " ")
     * @param str
     * @return
     */
    public static String replaceStr(String str) {
        String newString = null;
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher m = CRLF.matcher(str);
        if (m.find()) {
            str = m.replaceAll(" ");
        }
        newString = str.replace("\\", "/").replace("“"," ").replace("”"," ").trim();
        return newString;
    }
    /**
     * 计算两点之间距离(精确到米)
     * @param lng1 开始点经度
     * @param lat1 开始点纬度
     * @param lng2 结束点经度
     * @param lat2 结束点纬度
     * @return
     */
    public static double getDistance(double lng1,double lat1, double lng2,  double lat2){
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))) * R;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    /***
     * method_name: filterHashMapValueNull
     * param: [param]
     * return java.util.HashMap
     * describe:验证集合中有null转成字符串空
     * create_user: SZC
     * create_date: 2019-06-05
     * create_time: 15:44
     **/

    public static  HashMap filterHashMapValueNull(HashMap param){
        if(ToolUtil.isNotEmpty(param)){
            for (Object key : param.keySet()) {
                if(ToolUtil.isEmpty(param.get(key))){
                    param.put(key,"");
                }
            }
            return  param;
        }else{
            return  param;
        }
    }

    public static String getValueFromJsonObj(JSONObject obj, String key,String def) {
        if (obj.has(key)) {
            String result = obj.get(key).toString();
            if (DmsUtil.isStringNull(result))
                return def;
            else
                return result.trim();
        } else {
            return def;
        }
    }

    public static String generateUpLoadFileName(String prex){
        return prex + UUID.randomUUID().toString();
    }

    public static String toString(Object o) {
        String res = "";
        try {
            if (o == null) {
                res = "";
            } else {
                String s = o + "";
                if (s != null && !"".equals(s) && s != "null" && s.length() > 0
                        && !"null".equals(s)) {
                    res = s.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @description 获取本月的第一天和最后一天
     * @author lc
     * @param
     * @return
     * @date  2019年7月17日13:47:48
     */
    public static HashMap getMonthFristAndEnd() {
        HashMap map = new HashMap();
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        map.put("firstday",firstday);
        map.put("lastday",lastday);
        return map;
    }

    public static String getDate(){
         String temp_str="";
         Date dt = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         temp_str=sdf.format(dt);
         return temp_str;
    }

}
