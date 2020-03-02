package com.cf.kindergarten.common;

import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.core.base.service.SysUserService;
import com.cf.core.common.annotation.CfSaveAfter;
import com.cf.core.common.annotation.CfSaveBefore;
import com.cf.core.common.method.CommonSave;
import com.cf.core.common.method.CommonSaveBefore;
import com.cf.core.common.model.ResultModel;
import com.cf.core.common.model.SeniorResultModel;
import com.cf.core.util.ToolUtil;
import com.cf.kindergarten.mybatis.cf_app.ChildrenInfo;
import com.cf.kindergarten.mybatis.cf_app.ClassInfo;
import com.cf.kindergarten.mybatis.cf_app.TeacherInfo;
import com.cf.kindergarten.mybatis.cf_app.TeacherInfoMapper;
import com.cf.kindergarten.service.ChildrenInfoService;
import com.cf.kindergarten.service.ClassInfoService;
import com.cf.kindergarten.service.TeacherUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @Project : cf-core
 * @Package Name : com.cf.core.base.service.impl
 * @Description : 平台保存前操作
 * @Author : chenfeng
 * @Creation Date : 2018年04月13日 16:33
 * @ModificationHistory Who When What
 * @CfSaveBefore 保存前注解，funCode需要注入的功能码
 * SeniorResultModel beforeParams 原始参数 Map<String,Object> map 前端的原始参数 可以通过ID是否空，判断新建、修改
 * SeniorResultModel afterParams 系统操作后的返回值，操作前为NULL
 * SeniorResultModel customParams 用户自定义参数，支持参数传递，操作前->操作中->操作后
 * SeniorResultModel resultModel 返回状态，默认成功
 * return 参数继续传递，beforeParams必须与原始参数格式一致
 * _________ ________________ ____________________________________________
 */
@Component
public class CfFormSaveBefore extends CommonSave implements CommonSaveBefore {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TeacherUserService teacherUserService;

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private ChildrenInfoService childrenInfoService;

    @CfSaveBefore(funCode = "004001")
    public SeniorResultModel repeatTeacherName(SeniorResultModel resultModel) {
        Map<String, Object> beforeParams = resultModel.getBeforeParams();
        List<HashMap> data = (List<HashMap>) resultModel.getBeforeParams().get("data");
        HashMap<String, Object> lists = new HashMap<String, Object>();
        for (int i = 0; i < data.size(); i++) {
            String a = (String) data.get(i).get("tableName");
            if ("teacher_info".equals(a)) {
                lists = (HashMap<String, Object>) data.get(i).get("values");
                break;
            }
        }
        String phone = lists.get("phone") + "";
        if (ToolUtil.isNotEmpty(phone)) {
            TeacherInfo info = new TeacherInfo();
            if(lists.get("id") != null){
                info.setId(lists.get("id").toString());
            }
            info.setPhone(phone);
            Integer count = teacherUserService.selectTeacherInfoExistsCount(info);
            if(count > 0){
                resultModel.setResultModel(new ResultModel("-1", "手机号：" + phone + "已经存在，请修改！"));
                return resultModel;
            }
        }
        return resultModel;
    }

    @CfSaveBefore(funCode = "005002")
    public SeniorResultModel repeatClassName(SeniorResultModel resultModel) {
        Map<String, Object> beforeParams = resultModel.getBeforeParams();
        List<HashMap> data = (List<HashMap>) resultModel.getBeforeParams().get("data");
        HashMap<String, Object> lists = new HashMap<String, Object>();
        for (int i = 0; i < data.size(); i++) {
            String a = (String) data.get(i).get("tableName");
            if ("class_info".equals(a)) {
                lists = (HashMap<String, Object>) data.get(i).get("values");
                break;
            }
        }
        String className = lists.get("className") + "";
        if (ToolUtil.isNotEmpty(className)) {
            ClassInfo info = new ClassInfo();
            if(lists.get("id") != null){
                info.setId(lists.get("id").toString());
            }
            info.setClassName(className);
            Integer count = classInfoService.selectClassInfoExistsCount(info);
            if(count > 0){
                resultModel.setResultModel(new ResultModel("-1", "班级名称：" + className + "已经存在，请修改！"));
                return resultModel;
            }
        }
        return resultModel;
    }


    /**
     * 幼儿人员信息保存
     * @param resultModel
     * @return
     */
    @CfSaveBefore(funCode = "004002")
    public SeniorResultModel repeatChildrenUser(SeniorResultModel resultModel) {

        Map<String, Object> beforeParams = resultModel.getBeforeParams();

        List list = (ArrayList) beforeParams.get("data");

        List<Map> result2 = new ArrayList<Map>();

        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> result  = (Map)list.get(i);
            if (result.get("values") instanceof List) {
                result2  =(List) result.get("values");
            }
        }

        for (int i = 0; i < result2.size(); i++) {

            Map<String,Object> map = result2.get(i);
            String phone = map.get("phone") + "";
            if (ToolUtil.isNotEmpty(phone)) {
                ChildrenInfo info =new ChildrenInfo();
                if(map.get("id") != null){
                    info.setId(map.get("id").toString());
                }
                info.setPhone(phone);
                Integer count = childrenInfoService.selectChildInfoExistsCount(info);
                if(count > 0){
                    resultModel.setResultModel(new ResultModel("-1", "手机号：" + phone + "已经存在，请修改！"));
                    return resultModel;
                }
            }
        }
        return resultModel;
    }



}
