package com.cf.kindergarten.common;


import com.cf.core.base.service.SysUserService;
import com.cf.core.common.annotation.CfSaveAfter;
import com.cf.core.common.method.CommonSave;
import com.cf.core.common.method.CommonSaveAfter;
import com.cf.core.common.model.ResultModel;
import com.cf.core.common.model.SeniorResultModel;
import com.cf.core.util.ToolUtil;
import com.cf.kindergarten.service.ClassInfoService;
import com.cf.kindergarten.service.TeacherUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Project : cf-core
 * @Package Name : com.cf.core.base.service.impl
 * @Description : 平台保存后操作
 * @Author : chenfeng
 * @Creation Date : 2018年04月13日 16:33
 * @ModificationHistory Who When What
 * @CfSaveAfter 保存后注解，funCode需要注入的功能码
 * SeniorResultModel beforeParams 原始参数 Map<String,Object> map 前端的原始参数 可以通过ID是否空，判断新建、修改
 * SeniorResultModel afterParams 系统操作后的返回值，含id主表编码，根据业务需求扩充
 * SeniorResultModel customParams 用户自定义参数，支持参数传递，操作前->操作中->操作后
 * return 仅用于判断方法执行成功/失败，可以不返回
 * _________ ________________ ____________________________________________
 */
@Component
public class CfFormSaveAfter extends CommonSave implements CommonSaveAfter {
    @Autowired
    private SysUserService sys;

    @Autowired
    private TeacherUserService teacherUserService;

    @Autowired
    private ClassInfoService classInfoService;


    /**
     * 人员信息保存
     * @param resultModel
     * @return
     */
    @CfSaveAfter(funCode = "004001")
    public SeniorResultModel SaveTeacherUser(SeniorResultModel resultModel){

        Map<String, Object> beforeParams = resultModel.getBeforeParams();
        Map<String, Object> afterParams = resultModel.getAfterParams();
        if(ToolUtil.isEmpty(afterParams))return resultModel;
        for (String s : afterParams.keySet()){
            System.out.println("key:"+s+"|value:"+afterParams.get(s));
        }

        String id = afterParams.get("id").toString();

        teacherUserService.SaveSysUserInfo(id);


        return resultModel;
    }


    /**
     * 班级信息保存
     * @param resultModel
     * @return
     */
    @CfSaveAfter(funCode = "005002")
    public SeniorResultModel SaveClassInfo(SeniorResultModel resultModel){

        Map<String, Object> beforeParams = resultModel.getBeforeParams();
        Map<String, Object> afterParams = resultModel.getAfterParams();
        if(ToolUtil.isEmpty(afterParams))return resultModel;
        for (String s : afterParams.keySet()){
            System.out.println("key:"+s+"|value:"+afterParams.get(s));
        }

        String id = afterParams.get("id").toString();

        classInfoService.updateTeacherId(id);


        return resultModel;
    }

    /**
     * 幼儿人员信息保存
     * @param resultModel
     * @return
     */
    @CfSaveAfter(funCode = "004002")
    public SeniorResultModel SaveChildrenUser(SeniorResultModel resultModel){

        Map<String, Object> beforeParams = resultModel.getBeforeParams();
        Map<String, Object> afterParams = resultModel.getAfterParams();
        if(ToolUtil.isEmpty(afterParams))return resultModel;

        List list = (ArrayList) beforeParams.get("data");

        List<Map> result2 = new ArrayList<Map>();

        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> result  = (Map)list.get(i);
            if (result.get("values") instanceof List) {
                result2  =(List) result.get("values");
            }
        }
        String id = afterParams.get("id").toString();

        teacherUserService.SaveParentSysUserInfo(result2,id);

        return resultModel;
    }


}

