package com.cf.kindergarten.common;

import com.cf.core.online.OnlineUserManager;
import com.cf.kindergarten.util.DmsConstants;
import com.cf.kindergarten.util.DmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project : cf-core
 * @Package Name : com.cf.demo.common
 * @Description : TODO
 * @Author : chenfeng
 * @Creation Date : 2018年08月23日 13:51
 * @ModificationHistory Who When What
 * _________ ________________ ____________________________________________
 */
@Component
public class CfLoginExtend {

    @Autowired
    private OnlineUserManager onlineUserManager;

    private HashMap licenseVerification(String flagType,String id){
        HashMap res=new HashMap();
        res.put("status", DmsConstants.RESULT_SUCCESS);
        Map<String, Integer> m= onlineUserManager.getOnlineUserCountAll();
        HashMap<String, String> a= DmsUtil.szccflc;
        if("RegisterMaxUser".equals(flagType)&&!("1".equals(id))){
            if(Integer.parseInt( m.get("001")+"")>Integer.parseInt( a.get("RegisterMaxUser")+"")){
                res.put("status",DmsConstants.RESULT_FAIL);
                res.put("msg","在线人数超过PC端许可数,许可数为："+a.get("RegisterMaxUser")+"个,在线人数为："+m.get("001")+"个");
                return res;
            }
        }else if("RegisterMobile".equals(flagType)&&!("1".equals(id))){
            if(Integer.parseInt( m.get("002")+"")>Integer.parseInt( a.get("RegisterMobile")+"")){
                res.put("status",DmsConstants.RESULT_FAIL);
                res.put("msg","在线人数超过APP端许可数,许可数为："+a.get("RegisterMobile")+"个,在线人数为："+m.get("002")+"个");
                return res;
            }
        }
        res.put("msg","");
        return res;
    }

}
