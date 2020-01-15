package com.cf.kindergarten.common;

import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.core.base.service.SysUserService;
import com.cf.core.common.annotation.CfSaveBefore;
import com.cf.core.common.method.CommonSave;
import com.cf.core.common.method.CommonSaveBefore;
import com.cf.core.common.model.ResultModel;
import com.cf.core.common.model.SeniorResultModel;
import com.cf.core.util.ToolUtil;
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

    @CfSaveBefore(funCode = "001900130001")
    public SeniorResultModel repeatUserLoginName(SeniorResultModel resultModel) {
        Map<String, Object> beforeParams = resultModel.getBeforeParams();
        List<HashMap> data = (List<HashMap>) resultModel.getBeforeParams().get("data");
        HashMap<String, Object> lists = new HashMap<String, Object>();
        for (int i = 0; i < data.size(); i++) {
            String a = (String) data.get(i).get("tableName");
            if ("bs_person".equals(a)) {
                lists = (HashMap<String, Object>) data.get(i).get("values");
                break;
            }
        }
        String loginName = lists.get("loginName") + "";
        if (ToolUtil.isNotEmpty(loginName)) {
            if (ToolUtil.isNotEmpty(lists.get("userId") + "")) {
                //账号没有更改就不判断是否重复
                SysUser sysUser = new SysUser();
                sysUser.setId(lists.get("userId") + "");
                SysUser user = sysUserService.selectSysUser(sysUser);
                if (loginName.equals(user.getLoginName())) {

                } else {
                    SysUser sysUser2 = new SysUser();
                    sysUser2.setLoginName(loginName);
                    SysUser resultUser = sysUserService.selectSysUserByLoginName(sysUser2);
                    if (ToolUtil.isNotEmpty(resultUser)) {
                        resultModel.setResultModel(new ResultModel("-1", "账号：" + loginName + "已经存在，请修改！"));
                        return resultModel;
                    }
                }
            } else {
                SysUser sysUser2 = new SysUser();
                sysUser2.setLoginName(loginName);
                SysUser resultUser = sysUserService.selectSysUserByLoginName(sysUser2);
                if (ToolUtil.isNotEmpty(resultUser)) {
                    resultModel.setResultModel(new ResultModel("-1", "账号：" + loginName + "已经存在，请修改！"));
                    return resultModel;
                }
            }
        }
        return resultModel;
    }

}
