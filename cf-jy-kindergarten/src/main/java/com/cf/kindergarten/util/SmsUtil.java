package com.cf.kindergarten.util;


import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Description :
 * @Author : xujian
 * @Creation Date : 2018-10-27 9:39
 * _________ ________________
 */
@Component
public class SmsUtil {

    // 短信应用SDK AppID
    // 1400开头
    @Value("${guest.appid}")
   private static int appid = 1400154366;// 江苏增宇
    // private static int appid = 1400184505;//麒麟家居
    // 短信应用SDK AppKey
    @Value("${guest.appkey}")
    private static String appkey = "974b434f2685449489114c819d53551d";// 江苏增宇
    //private static String appkey = "bf5d09c30e7862c935abf64c40014951";//麒麟家居
    // 需要发送短信的手机号码
    private static String[] phoneNumbers = {"15862110334"};

    // 短信模板ID，需要在短信应用中申请
    // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    @Value("${guest.templateId}")
    private static  int templateId = 218199; // 江苏增宇
    // private static  int templateId = 275753;  //麒麟家居
    //templateId7839对应的内容是"您的验证码是: {1}"
    // 签名
    // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
   // private static String smsSign = "麒麟家居";
    @Value("${guest.smsSign}")
    private static String smsSign = "江苏才子";

    /**
     * 描述:给多人发送短信
     * @param phoneNumber：电话号码 params：参数
     * @return
     * create_user: xujian
     * create_date: 2018-10-27
     * create_time: 10:54
     **/
    public static HashMap sendMsgForOne(String phoneNumber,String[] params){
        HashMap resultMap = new HashMap();

        try {
            //数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
           // String[] params = {"9"};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
        }

        return resultMap;
    }
    /**
     * 描述:给多人发送短信
     * @param phoneNumbers：电话号码 params：参数
     * @return 
     * create_user: xujian
     * create_date: 2018-10-27
     * create_time: 10:54
     **/
    public static HashMap sendMsgForAll(String[] phoneNumbers,String[] params){
        HashMap resultMap = new HashMap();
        try {
            //数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
         //   String[] params = {"9"};
            SmsMultiSender ssender = new SmsMultiSender(appid, appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsMultiSenderResult result = ssender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
        }

        return resultMap;
    }

    public static void main(String[] args) {
        String[] str = {"1"};
        SmsUtil.sendMsgForOne("15862110334",str);
        //SmsUtil.sendMsgForAll(phoneNumbers,str);
    }
}
