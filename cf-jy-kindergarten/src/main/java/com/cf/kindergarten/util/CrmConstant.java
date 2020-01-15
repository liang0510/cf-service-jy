package com.cf.kindergarten.util;

public class CrmConstant {
    /*成功失败标识*/
    public static String RESULT_FAIL = "0"; //失败
    public static String RESULT_SUCCESS = "1"; //成功
    /*aa_data表基础数据*/

    //编码规则类型
    public static String CODERULE_TYPE_MANUAL = "1";//纯手工
    public static String CODERULE_TYPE_SERIAL = "2";//纯流水
    public static String CODERULE_TYPE_SYSTEMRULE = "3";//系统规则
    public static String CODERULE_PREFIX_TYPE_CORP = "1";//所属公司编码
    public static String CODERULE_PREFIX_TYPE_DEPT = "2";//所属部门编码
    public static String CODERULE_PREFIX_TYPE_PERSON = "3";//人员编码
    public static String CODERULE_PREFIX_TYPE_FIXED = "4";//固定值
    public static String CODERULE_PREFIX_TYPE_YMD= "5";//年月日
    public static String CODERULE_PREFIX_TYPE_YM = "6";//年月
    public static String CODERULE_PREFIX_TYPE_Y = "7";//年

    //字典
    public static String CONTRACT_PROP_YS = "0001";//合同性质应收
    public static String CONTRACT_TYPE_YF = "0002";//合同性质应付
    public static String MARKETACTIVITY_CORPROLE_ZBDW = "0001";//主办单位
    public static String MARKETACTIVITY_CORPROLE_XBDW = "0002";//协办单位
    public static String MARKETACTIVITY_CORPROLE_CBDW = "0004";//承办单位


    //工作项目类型
    public static String ACTIVITY_PROJECT_TYPE_IMPLEMENT = "bf07b53fce534972bdf58ae08df6ab42"; //实施类型
    public static String ACTIVITY_PROJECT_TYPE_INSTALL = "f085c5d91c8848288e33f93f4f338fd7"; //安装类型
}
