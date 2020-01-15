package com.cf.kindergarten.util;

import com.cf.core.common.model.ResultModel;
import com.cf.core.util.DateUtil;
import com.cf.core.util.ToolUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * DMS工具包
 */
public class CrmUtil {

    /**
     * 获取以type为开头的时间戳编码
     * @param type
     * @return
     */
    public static String getCode(String type){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        return type+date;
    }


    /**
     *
     * @discription 浮点数加法
     * @param v1
     * @param v2
     * @return
     */
    public static String add(String v1, String v2) {
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
        return b1.add(b2).toPlainString();
    }

    /**
     *
     * @discription 浮点数减法，第一个参数减去第二个参数
     * @param v1
     * @param v2
     * @return
     */
    public static String sub(String v1, String v2) {
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
        return b1.subtract(b2).toPlainString();
    }

    /**
     *
     * @discription 浮点数乘法
     * @param v1
     * @param v2
     * @return
     */
    public static String mul(String v1, String v2) {
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
        return b1.multiply(b2).toPlainString();
    }

    /**
     *
     * @discription 除法，按指定位数四舍五入
     * @param v1
     * @param v2
     * @param scale 小数位数（精度）
     * @return
     */
    public static String div(String v1, String v2, int scale) {
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
            return BigDecimal.ZERO.toPlainString();
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        try {
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
        }catch(Exception e){
            return BigDecimal.ZERO.toPlainString();
        }
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
     * 获取当月第一天
     * @return
     */
    public static String getMonthFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
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

    public static String getYearMonth(){
        return DateUtil.formatDate(new Date(),"yyyyMM");
    }

    public static Boolean isStringNull(String s) {
        if (s == null || s.trim().equals("") || s.trim().equals("null"))
            return true;
        return false;
    }

    public static Boolean isStringNotNull(String s) {
        return !isStringNull(s);
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

    public static int toInt(Object o) {
        int res = 0;
        try {
            if(ToolUtil.isNotEmpty(o)){
               res = Integer.parseInt(o+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static double toDouble(String o) {
        double res = 0.0;
        try {
            if(CrmUtil.isStringNotNull(o)){
                res = Double.parseDouble(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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
            if(CrmConstant.RESULT_SUCCESS.equals(resultMap.get("status"))){
                if(showSuccess) return ResultModel.success(param);
                else return ResultModel.successQuery(param);
            }else{
                if(showError) return new ResultModel("-10001",resultMap.get("msg")+"");
                else return ResultModel.successQuery(resultMap);
            }
        }
    }


    /**
     * @description 封装返回信息
     * @author hh
     * @date 10:05 2018/6/25
     */
    public static ResultModel getReturnModel(HashMap resultMap,Object param){
        if(ToolUtil.isEmpty(resultMap)){
            return null;
        }else{
            if(CrmConstant.RESULT_SUCCESS.equals(resultMap.get("status"))){
                return ResultModel.success(param);
            }else{
                return new ResultModel("-10001",resultMap.get("msg")+"");
            }
        }
    }

  /**
   * @description 判断浮点数是否相等
   * @author hh
   * @param
   * @return
   * @date 9:44 2018-09-26
   */
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

    //过滤HTML标签
    public static String Html2Text(Object inputString){
        String htmlStr = inputString+""; //含html标签的字符串
        String textStr ="";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try{
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签

            textStr = htmlStr;
        }catch(Exception e){
            e.printStackTrace();
        }
        return textStr;//返回文本字符串
    }

    public static Double getDaySub(String beginDateStr, String endDateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

       return (endDate.getTime() - beginDate.getTime()) / 86400000.0;
    }

    public static boolean compareTime(String s, String e) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date a = null;
        Date b = null;
        try {
            a = sdf.parse(s);
            b = sdf.parse(e);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return a.getTime()>=b.getTime();
    }
   /***
    * method_name: organizationalCondition
    * param: [param, departmentObject对象名,person对应查询条件中人员的字段, depart对应查询条件中部门的字段]
    * return java.util.HashMap
    * describe:
    * create_user: SZC
    * create_date: 2019-05-20
    * create_time: 14:34
    **/

    public static HashMap organizationalCondition(HashMap param,String departmentObject,String person,String depart) {
        String departStr=param.get(departmentObject)+"";
        if(ToolUtil.isNotEmpty(departStr)){//组织查询条件封装
            Map departmentObjectMap=DmsUtil.fromJsonToMap(departStr);
            String property=departmentObjectMap.get("property")+"";
            if(ToolUtil.isNotEmpty(property)){
                if("person".equals(property)){
                    if(ToolUtil.isNotEmpty(person)){
                        param.put(person,departmentObjectMap.get("code")+"");
                    }
                }else{
                    if(ToolUtil.isNotEmpty(depart)){
                        param.put(depart,departmentObjectMap.get("code")+"");
                    }
                }
            }
        }
        return param;
    }


}
