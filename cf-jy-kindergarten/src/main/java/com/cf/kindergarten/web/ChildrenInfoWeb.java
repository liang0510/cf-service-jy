package com.cf.kindergarten.web;

import com.cf.core.base.mybatis.cf_base.SysRoleUser;
import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.core.base.service.SysRoleUserService;
import com.cf.core.base.service.SysUserService;
import com.cf.core.base.web.SysBaseWeb;
import com.cf.core.common.model.ModelUtil;
import com.cf.core.common.model.ResultModel;
import com.cf.core.common.session.UserInfo;
import com.cf.kindergarten.mybatis.cf_app.ChildrenInfo;
import com.cf.kindergarten.mybatis.cf_app.TeacherInfo;
import com.cf.kindergarten.service.ChildrenInfoService;
import com.cf.kindergarten.service.ClassInfoService;
import com.cf.kindergarten.util.KindergrartenConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


/**
 * @Project: cf-service
 * @Package: com.cf.kindergarten.web
 * @Description: 班级管理
 * @Author: liangtao
 * @CreateDate: 2020/2/19 10:13
 * @ModificationHistory: （who whatTime doWhat）
 */
@RestController
@RequestMapping("/childrenWeb")
@Api(tags = {"智慧幼儿园-幼儿管理"})
public class ChildrenInfoWeb extends SysBaseWeb {

    private static Logger log = LoggerFactory.getLogger(ChildrenInfoWeb.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private ChildrenInfoService childrenInfoService;

    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 获取幼儿离园信息
     * @param param
     * @return
     */
    @PostMapping(value = "/auditChildrenLeave")
    @ApiOperation(value="获取幼儿离园信息",notes = "获取幼儿离园信息")
    @ApiImplicitParam(name = "param", value = "获取幼儿离园信息", required = true, dataType = "HashMap")
    public ResultModel auditTeacherApply(HttpServletRequest request, @RequestBody HashMap param){

        try {
            UserInfo userInfo = ModelUtil.getUserInfo();
            //类别
            String types =param.get("types").toString();
            //意见
            String auditOpinion =param.get("auditOpinion") +"";
            //时间
            String departureTime =param.get("departureTime") +"";

            //主键
            String id =param.get("id").toString();

            ChildrenInfo info = childrenInfoService.selectByPrimaryKey(id);

            info.setDepartureTypes(types);
            info.setDepartureTime(departureTime);
            info.setOpinion(auditOpinion);
            info.setStatus(KindergrartenConstant.CHILDREN_NO);
            childrenInfoService.updateChildrenInfo(info);

            //更新班级管理中宝贝数量信息
            classInfoService.updateClassInfoByChildNum(info.getClassId());

            return  new ResultModel(SUCCESS_CODE, SUCCESS_MSG);


        } catch (Exception e) {
            log.error(e.getMessage());
            return  new ResultModel(ERROR_CODE, ERROR_MSG);
        }
    }


    /**
     * 获取幼儿入园信息
     * @param param
     * @return
     */
    @PostMapping(value = "/enterTeacherApply")
    @ApiOperation(value="获取幼儿入园信息",notes = "获取幼儿入园信息")
    @ApiImplicitParam(name = "param", value = "获取幼儿入园信息", required = true, dataType = "HashMap")
    public ResultModel enterTeacherApply(HttpServletRequest request, @RequestBody HashMap param){

        try {
            UserInfo userInfo = ModelUtil.getUserInfo();

            //主键
            String id =param.get("id").toString();

            ChildrenInfo info = childrenInfoService.selectByPrimaryKey(id);

            info.setDepartureTypes("");
            info.setDepartureTime("");
            info.setOpinion("");
            info.setStatus(KindergrartenConstant.CHILDREN_YES);
            childrenInfoService.updateChildrenInfo(info);

            //更新班级管理中宝贝数量信息
            classInfoService.updateClassInfoByChildNum(info.getClassId());

            return  new ResultModel(SUCCESS_CODE, SUCCESS_MSG);


        } catch (Exception e) {
            log.error(e.getMessage());
            return  new ResultModel(ERROR_CODE, ERROR_MSG);
        }
    }



    /**
     * 删除幼儿信息
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/removeChildrenInfo")
    @ApiOperation(value = "删除幼儿信息", notes = "删除幼儿信息")
    @ApiImplicitParam(name = "param", value = "删除幼儿信息", required = true, dataType = "HashMap")
    public ResultModel removeTeacherUser(@RequestBody HashMap param) {

        try {
            UserInfo userInfo = ModelUtil.getUserInfo();
            String id = param.get("id").toString();

            ChildrenInfo info = childrenInfoService.selectByPrimaryKey(id);

            if (info != null) {
                log.info("登陆用户："+userInfo.getLoginName()+"用户：" +userInfo.getName() +"进行删除用户操作，删除幼儿：" +info.getName());
                SysUser user = new SysUser();
                user.setLoginName(info.getPhone());
                List<SysUser> sysUserList = childrenInfoService.selectChildInfoRelactionUser(id);
                if (sysUserList != null && sysUserList.size() > 0) {

                    for (int i = 0; i < sysUserList.size(); i++) {
                        SysUser sysUser = sysUserList.get(i);
                        //删除用户权限信息
                        SysRoleUser sysRoleUser = new SysRoleUser();
                        sysRoleUser.setUserId(sysUser.getId());
                        sysRoleUserService.deleteSysRoleUserByUserId(sysRoleUser);
                        //删除登录用户信息
                        sysUserService.deleteSysUserBase(sysUser);
                    }
                }
                //删除党员信息数据
                childrenInfoService.deleteByPrimaryKey(id);
                //删除关联信息
                childrenInfoService.deleteChildrenRelaction(id);
                //更新班级管理中宝贝数量信息
                classInfoService.updateClassInfoByChildNum(info.getClassId());

            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultModel(ERROR_CODE, ERROR_MSG);
        }
        return new ResultModel(SUCCESS_CODE, SUCCESS_MSG);
    }


}