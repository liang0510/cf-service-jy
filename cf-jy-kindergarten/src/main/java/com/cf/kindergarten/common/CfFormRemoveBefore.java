package com.cf.kindergarten.common;

import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.core.base.service.SysUserService;
import com.cf.core.common.annotation.CfRemoveBefore;
import com.cf.core.common.method.CommonSave;
import com.cf.core.common.method.CommonSaveBefore;
import com.cf.core.common.model.SeniorResultModel;
import com.cf.core.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project : cf-core
 * @Package Name : com.cf.core.base.service.impl
 * @Description : 平台删除前操作
 * @Author : chenfeng
 * @Creation Date : 2019年02月15日 13:53
 * @ModificationHistory Who When What
 * @CfRemoveBefore 删除前注解，funCode需要注入的功能码
 * SeniorResultModel beforeParams 原始参数 Map<String,Object> map 前端的原始参数
 * SeniorResultModel afterParams 系统操作后的返回值，操作前为NULL
 * SeniorResultModel customParams 用户自定义参数，支持参数传递，操作前->操作中->操作后
 * SeniorResultModel resultModel 返回状态，默认成功
 * return 参数继续传递，beforeParams必须与原始参数格式一致
 * _________ ________________ ____________________________________________
 */
@Component
public class CfFormRemoveBefore extends CommonSave implements CommonSaveBefore {

    @Autowired
    private SysUserService sysUserService;


    @CfRemoveBefore(funCode = "009")
    public SeniorResultModel test(SeniorResultModel resultModel){
        System.out.println("保存前");
        Map<String, Object> map = resultModel.getBeforeParams();
        map.put("beforTest","beforTest");
        if(ToolUtil.isEmpty(map))return resultModel;
        for (String s : map.keySet()){
            System.out.println("key:"+s+"|value:"+map.get(s));
        }
        Map customMap = new HashMap();
        customMap.put("test","test");

        //接口调用测试
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("cf");
        customMap.put("user",sysUserService.selectSysUser(sysUser));

        resultModel.setCustomParams(customMap);
        //
        //resultModel.setResultModel(ResultModel.error());
        return resultModel;
    }

}
