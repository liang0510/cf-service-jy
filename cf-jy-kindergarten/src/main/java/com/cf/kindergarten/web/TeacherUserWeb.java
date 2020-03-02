package com.cf.kindergarten.web;


import com.cf.core.base.mybatis.cf_base.*;
import com.cf.core.base.service.SysRoleUserService;
import com.cf.core.base.service.SysUserService;
import com.cf.core.base.web.SysBaseWeb;
import com.cf.core.common.model.ModelUtil;
import com.cf.core.common.model.ResultModel;
import com.cf.core.common.session.UserInfo;
import com.cf.core.util.ToolUtil;
import com.cf.kindergarten.mybatis.cf_app.TeacherInfo;
import com.cf.kindergarten.service.TeacherUserService;
import com.cf.kindergarten.util.KindergrartenConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Project: cf-service
 * @Package: com.cf.kindergarten.web
 * @Description: 老师人员信息
 * @Author: liangtao
 * @CreateDate: 2020/2/19 10:13
 * @ModificationHistory: （who whatTime doWhat）
 */
@RestController
@RequestMapping("/teacherUser")
@Api(tags = {"智慧幼儿园-老师人员信息"})
public class TeacherUserWeb extends SysBaseWeb {

    private static Logger log = LoggerFactory.getLogger(TeacherUserWeb.class);

    @Autowired
    private TeacherUserService teacherUserService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleUserService sysRoleUserService;


    @PostMapping(value = "/getSysCorporationListById")
    @ApiOperation("获取单位列表")
    @ApiImplicitParam(
            name = "sysCorporation",
            value = "组织对象",
            required = true,
            dataType = "SysCorporation"
    )
    public ResultModel getSysCorporationListById(HttpServletRequest request, @RequestBody HashMap param) {
        List<SysCorporation> lists = new ArrayList<>();
        try {
            UserInfo userInfo = ModelUtil.getUserInfo();
            if (!ToolUtil.isEmpty(userInfo) && !ToolUtil.isEmpty(userInfo.getCorpCode())) {
                SysCorporation sysCorporation = new SysCorporation();
                String corpCode = "";
                if(userInfo.getCorpCode().length() > 10){
                    corpCode = userInfo.getCorpCode().substring(0,10);
                }else{
                    corpCode = userInfo.getCorpCode();
                }
                sysCorporation.setCorpCode(corpCode);
                lists =  teacherUserService.getSysCorporationListById(sysCorporation);
                return ResultModel.successQuery(lists);
            } else {
                return ResultModel.successQuery(lists);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultModel.successQuery(lists);
        }
    }


    @PostMapping(value = "/getSysUserByCorp")
    @ApiOperation(value="根据单位编号查询用户信息",notes = "返回参数说明：是一个List 对象 <br/>" +
            "id（编号）<br/>" +
            "userName（姓名）<br/>" +
            "corpCode（单位编号）<br/>" +
            "corpName（单位名称）<br/>" +
            "idCard（身份证号码）")
    @ApiImplicitParam(name = "param", value ="{<br/>" +
            "\"corpCode\":单位编号<br/>" +
            "}",required = false, dataType = "HashMap")
    public ResultModel getSysUserByCorp(HttpServletRequest request,@RequestBody HashMap param) {

        try {
            if(param.get("corpCode") != null && !param.get("corpCode").equals("")){

                String corpCode = param.get("corpCode").toString();

                Map<String, Object> resultMap = new HashMap<>();
                SysUser user = new SysUser();
                user.setCorpCode(corpCode);
                List<SysUser> userList = teacherUserService.selectTeacherInfoList(user);
                return new ResultModel().successQuery(userList);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultModel(ERROR_CODE, ERROR_MSG,ERROR_CODE);
        }
        return new ResultModel(SUCCESS_CODE, SUCCESS_MSG, SUCCESS_CODE);
    }


    /**
     * 审核功能
     * @param param
     * @return
     */
    @PostMapping(value = "/auditTeacherApply")
    @ApiOperation(value="获取离职信息",notes = "获取离职信息")
    @ApiImplicitParam(name = "param", value = "获取离职信息", required = true, dataType = "HashMap")
    public ResultModel auditTeacherApply(HttpServletRequest request, @RequestBody HashMap param){

        try {
            UserInfo userInfo = ModelUtil.getUserInfo();
            //状态
            String departureTime =param.get("departureTime").toString();
            //意见
            String departureReason =param.get("departureReason") +"";
            //主键
            String id =param.get("id").toString();

            TeacherInfo info = teacherUserService.getTeacherInfo(id);
            info.setDepartureTime(departureTime);
            info.setDepartureReason(departureReason);
            info.setIncumbency(KindergrartenConstant.ZERO);
            //老师有哪些班级 并删除要同步到班级信息
            teacherUserService.removeTeacherByClass(info);
            teacherUserService.updateTeacherInfo(info);
            return  new ResultModel(SUCCESS_CODE, SUCCESS_MSG);


        } catch (Exception e) {
            log.error(e.getMessage());
            return  new ResultModel(ERROR_CODE, ERROR_MSG);
        }
    }

    /**
     * 删除职工用户信息
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/removeTeacherUser")
    @ApiOperation(value = "删除职工用户信息", notes = "删除职工用户信息")
    @ApiImplicitParam(name = "param", value = "删除职工用户信息", required = true, dataType = "HashMap")
    public ResultModel removeTeacherUser(@RequestBody HashMap param) {

        try {

            UserInfo userInfo = ModelUtil.getUserInfo();

            String id = param.get("id").toString();
            TeacherInfo info = teacherUserService.getTeacherInfo(id);

            if (info != null) {
                log.info("身份证号码："+userInfo.getIdCard()+"用户：" +userInfo.getName() +"进行删除用户操作，删除用户：" +info.getName() + " 身份证号:" +info.getIdCard() + "手机号:" +info.getPhone());
                SysUser user = new SysUser();
                user.setLoginName(info.getPhone());
                SysUser sysUser = sysUserService.selectSysUserByLoginName(user);
                if (sysUser != null) {
                    //删除用户权限信息
                    SysRoleUser sysRoleUser = new SysRoleUser();
                    sysRoleUser.setUserId(sysUser.getId());
                    sysRoleUserService.deleteSysRoleUserByUserId(sysRoleUser);
                    //删除登录用户信息
                    sysUserService.deleteSysUserBase(sysUser);
                }

                //老师有哪些班级 并删除要同步到班级信息
                teacherUserService.removeTeacherByClass(info);
                //删除党员信息数据
                teacherUserService.removeTeacherInfoById(id);

            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultModel(ERROR_CODE, ERROR_MSG);
        }
        return new ResultModel(SUCCESS_CODE, SUCCESS_MSG);
    }
}