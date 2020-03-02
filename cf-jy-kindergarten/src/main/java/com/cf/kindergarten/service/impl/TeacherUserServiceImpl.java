package com.cf.kindergarten.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cf.core.base.mybatis.cf_base.*;
import com.cf.core.base.service.SysToolService;
import com.cf.core.common.model.ModelUtil;
import com.cf.kindergarten.mybatis.cf_app.*;
import com.cf.kindergarten.service.TeacherUserService;
import com.cf.kindergarten.util.KindergrartenConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Project: cf-service
 * @Package: com.cf.kindergarten.service.impl
 * @Description: null
 * @Author: liangtao
 * @CreateDate: 2020/2/19 10:30
 * @ModificationHistory: （who whatTime doWhat）
 */
@Service
public class TeacherUserServiceImpl implements TeacherUserService{

    private static Logger log = LoggerFactory.getLogger(TeacherUserServiceImpl.class);

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Autowired
    private SysCorporationMapper sysCorporationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysToolService sysToolService;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private ChildrenInfoMapper childrenInfoMapper;




    @Autowired
    private ClassUserRelationMapper classUserRelationMapper;

    @Override
    public TeacherInfo getTeacherInfo(String id) {
        return teacherInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTeacherInfo(TeacherInfo partyUser) {
        teacherInfoMapper.updateByPrimaryKeySelective(partyUser);
    }

    @Override
    public void SaveSysUserInfo(String djId) {
        try{
            //根据编号获取党组织信息
            TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(djId);

            if(teacherInfo != null) {

                SysUser userSelect = new SysUser();
                userSelect.setLoginName(teacherInfo.getPhone());  //登录名是 手机号
                SysUser userLogin = sysUserMapper.selectSysUserByLoginName(userSelect);

                if(userLogin != null){
                    //则更新
                    //获取组织编码
                    SysCorporation corporationSelect = new SysCorporation();
                    corporationSelect.setCorpCode(userLogin.getCorpCode());

                    SysCorporation sysCorporation = sysCorporationMapper.getSysOrg(corporationSelect);

                    if (sysCorporation != null) {
                        SysUser sysUser = new SysUser();
                        sysUser.setId(userLogin.getId());
                        sysUser.setUserType(userLogin.getUserType());//企业类型 1 集团管理员   2 子公司管理员 3、职员 4、经销商  5、供应商
                        sysUser.setUserCode(userLogin.getUserCode());//编码
                        sysUser.setUserName(teacherInfo.getName());//名称
                        sysUser.setLoginName(teacherInfo.getPhone());//登录帐号
                        sysUser.setLoginPassword(userLogin.getLoginPassword());//登录密码
                        sysUser.setQuestion(userLogin.getQuestion());//问题
                        sysUser.setAnswer(userLogin.getAnswer());//答案
                        sysUser.setOutKey(userLogin.getOutKey());//外部编码
                        sysUser.setYnLock(userLogin.getYnLock());//是否锁定
                        sysUser.setValidity("");//最终有效日期
                        sysUser.setQq(userLogin.getQq());//QQ
                        sysUser.setWechat(userLogin.getWechat());//微信
                        sysUser.setEmail(userLogin.getEmail());//邮箱
                        sysUser.setMobile(teacherInfo.getPhone());//手机
                        sysUser.setTelephone(userLogin.getTelephone());//固定电话
                        sysUser.setOrgId(sysCorporation.getId());//用户所属单位ID
                        sysUser.setCorpId(sysCorporation.getId());
                        sysUser.setDeptId("");//主管所在部门id
                        sysUser.setAreaId("");//用户所属单位代码
                        sysUser.setOrgCode(sysCorporation.getCorpCode());//用户所在部门代码
                        sysUser.setCorpCode(sysCorporation.getCorpCode());
                        sysUser.setDeptCode(userLogin.getDeptCode());//所在部门代码
                        sysUser.setAreaCode(userLogin.getAreaCode());//用户所属区域代码
                        sysUser.setRegulatoryOrg(sysCorporation.getCorpCode());//监管单位
                        sysUser.setIdCard(teacherInfo.getIdCard());//用户身份证号码
                        sysUser.setHeader(teacherInfo.getPhoto());//用户头像存放URl
                        sysUser.setYnModify(KindergrartenConstant.ONE);//0、不允许1、允许
                        sysUser.setBirthday(teacherInfo.getBirth());//出生日期
                        sysUser.setSex(teacherInfo.getSex());//性别
                        sysUser.setDelFlag(KindergrartenConstant.ZERO);
                        sysUser.setYnEnable(KindergrartenConstant.ONE);//状态0、不可用1、可用
                        sysUser.setGroupId(sysCorporation.getId());//所属组织单位
                        sysUser.setHeader(teacherInfo.getPhoto()); //用户头像存放URl
                        sysUserMapper.updateSysUser(sysUser);

                        //更新teacher_info out_key
                        teacherInfo.setOut_key(sysUser.getId());
                        teacherInfoMapper.updateByPrimaryKeySelective(teacherInfo);


                    }
                }else{
                    //获取组织编码
                    SysCorporation corporationSelect = new SysCorporation();
                    corporationSelect.setCorpCode(teacherInfo.getDepartment());

                    SysCorporation sysCorporation = sysCorporationMapper.getSysOrg(corporationSelect);
                    if (sysCorporation != null) {

                        SysUser sysUser = new SysUser();
                        ModelUtil.setField(sysUser);//设置实体默认值
                        sysUser.setUserType(KindergrartenConstant.STAFF);//企业类型 1 集团管理员   2 子公司管理员 3、职员 4、经销商  5、供应商
                        sysUser.setUserCode("");//编码AACZ
                        sysUser.setUserName(teacherInfo.getName());//名称
                        sysUser.setLoginName(teacherInfo.getPhone());//登录帐号
                        sysUser.setLoginPassword(DigestUtils.md5Hex("123456"));//登录密码
                        sysUser.setQuestion("");//问题
                        sysUser.setAnswer("");//答案
                        sysUser.setOutKey("");//外部编码
                        sysUser.setYnLock("");//是否锁定
                        sysUser.setValidity("");//最终有效日期
                        sysUser.setQq("");//QQ
                        sysUser.setWechat("");//微信
                        sysUser.setEmail("");//邮箱
                        sysUser.setMobile(teacherInfo.getPhone());//手机
                        sysUser.setTelephone("");//固定电话
                        sysUser.setOrgId(sysCorporation.getId());//用户所属单位ID
                        sysUser.setCorpId(sysCorporation.getId());
                        sysUser.setDeptId("");//主管所在部门id
                        sysUser.setAreaId("");//用户所属单位代码
                        sysUser.setOrgCode(sysCorporation.getCorpCode());//用户所在部门代码
                        sysUser.setCorpCode(sysCorporation.getCorpCode());
                        sysUser.setDeptCode("");//所在部门代码
                        sysUser.setAreaCode("");//用户所属区域代码
                        sysUser.setRegulatoryOrg(sysCorporation.getCorpCode());//监管单位
                        sysUser.setIdCard(teacherInfo.getIdCard());//用户身份证号码
                        sysUser.setHeader(teacherInfo.getPhoto()); //用户头像存放URl
                        sysUser.setYnModify(KindergrartenConstant.ONE);//0、不允许1、允许
                        sysUser.setBirthday(teacherInfo.getBirth());//出生日期
                        sysUser.setSex(teacherInfo.getSex());//性别
                        sysUser.setDelFlag(KindergrartenConstant.ZERO);
                        sysUser.setYnEnable(KindergrartenConstant.ONE);//状态0、不可用1、可用
                        sysUser.setGroupId(sysCorporation.getId());//所属组织单位
                        sysUserMapper.insertSysUser(sysUser);
                        //给该用户默认老师权限
                        SysRoleUser sysRoleUser = new SysRoleUser();
                        sysRoleUser.setRoleId("a0630c7ece824f29880db1fdb28971b1");
                        sysRoleUser.setUserId(sysUser.getId());
                        sysRoleUserMapper.insertSysRoleUser(sysRoleUser);


                        teacherInfo.setOut_key(sysUser.getId());
                        teacherInfoMapper.updateByPrimaryKeySelective(teacherInfo);

                    }
                }
                //老师有哪些班级 并要同步到班级信息中
                getTeacherByClass(teacherInfo);
            }

        }catch (Exception e){
            log.info("保存用户异常：" + e.getMessage());
            System.out.println("保存用户异常：" + e.getMessage());

        }

    }

    @Override
    public int removeTeacherInfoById(String id) {
        return teacherInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
       sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public List<SysUser> selectTeacherInfoList(SysUser sysUser) {
        return teacherInfoMapper.selectTeacherInfoList(sysUser);
    }

    @Override
    public List<SysCorporation> getSysCorporationListById(SysCorporation corporation) {
        return sysCorporationMapper.getSysOrgList(corporation);
    }

    @Override
    public void SaveParentSysUserInfo(List<Map> list,String id) {

        try{

                for (int i = 0; i < list.size(); i++) {

                    Map<String,Object> map = list.get(i);
                    SysUser userSelect = new SysUser();
                    userSelect.setLoginName(map.get("phone").toString());  //登录名是 手机号
                    SysUser userLogin = sysUserMapper.selectSysUserByLoginName(userSelect);

                    if (userLogin != null) {
                        //则更新
                        //获取组织编码
                        SysCorporation corporationSelect = new SysCorporation();
                        corporationSelect.setCorpCode(userLogin.getCorpCode());

                        SysCorporation sysCorporation = sysCorporationMapper.getSysOrg(corporationSelect);

                        if (sysCorporation != null) {
                            SysUser sysUser = new SysUser();
                            sysUser.setId(userLogin.getId());
                            sysUser.setUserType(userLogin.getUserType());//企业类型 1 集团管理员   2 子公司管理员 3、职员 4、经销商  5、供应商
                            sysUser.setUserCode(userLogin.getUserCode());//编码
                            sysUser.setUserName(map.get("name").toString());//名称
                            sysUser.setLoginName(map.get("phone").toString());//登录帐号
                            sysUser.setLoginPassword(userLogin.getLoginPassword());//登录密码
                            sysUser.setQuestion(userLogin.getQuestion());//问题
                            sysUser.setAnswer(userLogin.getAnswer());//答案
                            sysUser.setOutKey(userLogin.getOutKey());//外部编码
                            sysUser.setYnLock(userLogin.getYnLock());//是否锁定
                            sysUser.setValidity("");//最终有效日期
                            sysUser.setQq(userLogin.getQq());//QQ
                            sysUser.setWechat(userLogin.getWechat());//微信
                            sysUser.setEmail(userLogin.getEmail());//邮箱
                            sysUser.setMobile(map.get("phone").toString());//手机 idCard
                            sysUser.setTelephone(userLogin.getTelephone());//固定电话
                            sysUser.setOrgId(sysCorporation.getId());//用户所属单位ID
                            sysUser.setCorpId(sysCorporation.getId());
                            sysUser.setDeptId("");//主管所在部门id
                            sysUser.setAreaId("");//用户所属单位代码
                            sysUser.setOrgCode(sysCorporation.getCorpCode());//用户所在部门代码
                            sysUser.setCorpCode(sysCorporation.getCorpCode());
                            sysUser.setDeptCode(userLogin.getDeptCode());//所在部门代码
                            sysUser.setAreaCode(userLogin.getAreaCode());//用户所属区域代码
                            sysUser.setRegulatoryOrg(sysCorporation.getCorpCode());//监管单位
                            if(map.get("idCard") !=null){
                                sysUser.setIdCard(map.get("idCard").toString());//用户身份证号码
                            }
                            if(map.get("photo") !=null){
                                sysUser.setHeader(map.get("photo").toString());//用户头像存放URl
                            }
                            sysUser.setYnModify(KindergrartenConstant.ONE);//0、不允许1、允许
                            if(map.get("birth") !=null){
                                sysUser.setBirthday(map.get("birth").toString());//出生日期
                            }
                            if(map.get("sex") !=null){
                                sysUser.setSex(map.get("sex").toString());//性别
                            }
                            sysUser.setDelFlag(KindergrartenConstant.ZERO);
                            sysUser.setYnEnable(KindergrartenConstant.ONE);//状态0、不可用1、可用
                            sysUser.setGroupId(sysCorporation.getId());//所属组织单位
                            sysUserMapper.updateSysUser(sysUser);


                        }
                    } else {
                        //根据幼儿编号查询对应的班级所属组织编码
                        String corpCode = classInfoMapper.selectCorpByChildId(id);

                        if(corpCode != null){
                            SysCorporation corporationSelect = new SysCorporation();
                            corporationSelect.setCorpCode(corpCode);
                            SysCorporation sysCorporation = sysCorporationMapper.getSysOrg(corporationSelect);
                            if (sysCorporation != null) {

                                SysUser sysUser = new SysUser();
                                ModelUtil.setField(sysUser);//设置实体默认值
                                sysUser.setUserType(KindergrartenConstant.STAFF);//企业类型 1 集团管理员   2 子公司管理员 3、职员 4、经销商  5、供应商
                                sysUser.setUserCode("");//编码AACZ
                                sysUser.setUserName(map.get("name").toString());//名称
                                sysUser.setLoginName(map.get("phone").toString());//登录帐号
                                sysUser.setLoginPassword(DigestUtils.md5Hex("123456"));//登录密码
                                sysUser.setQuestion("");//问题
                                sysUser.setAnswer("");//答案
                                sysUser.setOutKey("");//外部编码
                                sysUser.setYnLock("");//是否锁定
                                sysUser.setValidity("");//最终有效日期
                                sysUser.setQq("");//QQ
                                sysUser.setWechat("");//微信
                                sysUser.setEmail("");//邮箱
                                sysUser.setMobile(map.get("phone").toString());//手机
                                sysUser.setTelephone("");//固定电话
                                sysUser.setOrgId(sysCorporation.getId());//用户所属单位ID
                                sysUser.setCorpId(sysCorporation.getId());
                                sysUser.setDeptId("");//主管所在部门id
                                sysUser.setAreaId("");//用户所属单位代码
                                sysUser.setOrgCode(sysCorporation.getCorpCode());//用户所在部门代码
                                sysUser.setCorpCode(sysCorporation.getCorpCode());
                                sysUser.setDeptCode("");//所在部门代码
                                sysUser.setAreaCode("");//用户所属区域代码
                                sysUser.setRegulatoryOrg(sysCorporation.getCorpCode());//监管单位

                                if(map.get("idCard") !=null){
                                    sysUser.setIdCard(map.get("idCard").toString());//用户身份证号码
                                }
                                if(map.get("photo") !=null){
                                    sysUser.setHeader(map.get("photo").toString());//用户头像存放URl
                                }
                                sysUser.setYnModify(KindergrartenConstant.ONE);//0、不允许1、允许
                                if(map.get("birth") !=null){
                                    sysUser.setBirthday(map.get("birth").toString());//出生日期
                                }
                                if(map.get("sex") !=null){
                                    sysUser.setSex(map.get("sex").toString());//性别
                                }
                                sysUser.setYnModify(KindergrartenConstant.ONE);//0、不允许1、允许
                                sysUser.setDelFlag(KindergrartenConstant.ZERO);
                                sysUser.setYnEnable(KindergrartenConstant.ONE);//状态0、不可用1、可用
                                sysUser.setGroupId(sysCorporation.getId());//所属组织单位
                                sysUserMapper.insertSysUser(sysUser);
                                //给该用户默认家长权限
                                SysRoleUser sysRoleUser = new SysRoleUser();
                                sysRoleUser.setRoleId("8639194c0d5c493e8af4b810a4828347");
                                sysRoleUser.setUserId(sysUser.getId());
                                sysRoleUserMapper.insertSysRoleUser(sysRoleUser);
                            }
                        }
                    }
                }

        }catch (Exception e){
            log.info("保存用户异常：" + e.getMessage());
            System.out.println("保存用户异常：" + e.getMessage());

        }

    }

    @Override
    public Integer selectTeacherInfoExistsCount(TeacherInfo info) {
        return teacherInfoMapper.selectTeacherInfoExistsCount(info);
    }

    @Override
    public void getTeacherByClass(TeacherInfo teacherInfo) {

        //老师有哪些班级 并要同步到班级信息中
        //查询一个老师有多少个班级
        if(!teacherInfo.getDef1().equals("")){

            List<ClassInfo> classInfoList = teacherInfoMapper.getClassInfoLists(teacherInfo);

            List<ClassUserRelation> relationList = new ArrayList<>();

            if(classInfoList != null && classInfoList.size() > 0){

                ClassUserRelation select = new ClassUserRelation();
                select.setTeacherId("('" + teacherInfo.getId() +"')");

                //  List<ClassUserRelation> classUserRelationLists = classUserRelationMapper.getClassUserRelationLists(select);  //查询出原来该老师对应的几个班级

                List<ClassUserRelation> relationAll = classUserRelationMapper.getClassUserRelationTeacherExists(teacherInfo.getId());  //查询出原来和现在该老师对应的几个班级

                for (int i = 0; i < classInfoList.size(); i++) {
                    ClassUserRelation relation = new ClassUserRelation();
                    ModelUtil.setField(relation);//设置实体默认值
                    relation.setClassId(classInfoList.get(i).getId());
                    relation.setTeacherId(teacherInfo.getId());
                    relationList.add(relation);
                    relationAll.add(relation);
                }
                // 先把老师关联班级删除 然后在添加
                ClassUserRelation classUserRelation = new ClassUserRelation();
                classUserRelation.setTeacherId(teacherInfo.getId());
                classUserRelationMapper.deleteClassUserRelation(classUserRelation);


                //批量更新每100插入
                if(relationList != null && relationList.size() > 0){
                    if (relationList.size() < 100) {
                        classUserRelationMapper.insertBatchClassUserRelation(relationList);
                    } else {
                        int len = 100;
                        int size = relationList.size();
                        int count = (size + len - 1) / len;
                        for (int i = 0; i < count; i++) {
                            List<ClassUserRelation> subListOK = relationList.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
                            classUserRelationMapper.insertBatchClassUserRelation(subListOK);
                        }
                    }
                }

                String classId = "";

                if(relationAll != null && relationAll.size() > 0){
                    for (int i = 0; i < relationAll.size(); i++) {
                        if(classId.equals("")){
                            classId = "'" +relationAll.get(i).getClassId()+ "'";
                        }else{
                            classId = classId + "," +"'" +relationAll.get(i).getClassId() + "'";
                        }
                    }
                }

                ClassUserRelation select2 = new ClassUserRelation();
                select2.setClassId("(" + classId +")");

                List<ClassUserRelation> classUserRelationList = classUserRelationMapper.getClassUserRelationLists(select2);

                List<ClassInfo> classInfoList1 = new ArrayList<>();

                if(classUserRelationList != null && classUserRelationList.size() > 0){
                    for (int i = 0; i < relationAll.size(); i++) {

                        List<PersonJson> jsonList = new ArrayList<>();
                        String teacher_name = "";
                        ClassInfo info = new ClassInfo();
                        for (int j = 0; j < classUserRelationList.size(); j++) {

                            if(relationAll.get(i).getClassId().equals(classUserRelationList.get(j).getClassId())){

                                PersonJson json = new PersonJson();
                                json.setId(classUserRelationList.get(j).getTeacherId());
                                json.setName(classUserRelationList.get(j).getDef2());
                                json.setType("person");
                                jsonList.add(json);
                                if(teacher_name.equals("")){
                                    teacher_name = classUserRelationList.get(j).getDef2();
                                }else{
                                    teacher_name = teacher_name + "," +classUserRelationList.get(j).getDef2();
                                }
                            }
                        }
                        info.setId(relationAll.get(i).getClassId());

                        if(jsonList != null && jsonList.size() > 0){
                            info.setClassTeacher(JSONObject.toJSON(jsonList).toString());
                            info.setClassTeacherName(teacher_name);

                        }else{
                            info.setClassTeacher("");
                            info.setClassTeacherName("");
                        }
                        classInfoList1.add(info);
                    }

                    if (classInfoList1.size() < 100) {
                        classInfoMapper.updateBatchClassInfoList(classInfoList1);
                    } else {
                        int len = 100;
                        int size = classInfoList1.size();
                        int count = (size + len - 1) / len;
                        for (int i = 0; i < count; i++) {
                            List<ClassInfo> subListOK = classInfoList1.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
                            classInfoMapper.updateBatchClassInfoList(subListOK);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void removeTeacherByClass(TeacherInfo teacherInfo) {

        if(!teacherInfo.getDef1().equals("")){

            List<ClassInfo> classInfoList = teacherInfoMapper.getClassInfoLists(teacherInfo);

            List<ClassUserRelation> relationAll = new ArrayList<>();

            if(classInfoList != null && classInfoList.size() > 0){

                for (int i = 0; i < classInfoList.size(); i++) {
                    ClassUserRelation relation = new ClassUserRelation();
                    ModelUtil.setField(relation);//设置实体默认值
                    relation.setClassId(classInfoList.get(i).getId());
                    relation.setTeacherId(teacherInfo.getId());
                    relationAll.add(relation);
                }
                // 先把老师关联班级删除 然后在添加
                ClassUserRelation classUserRelation = new ClassUserRelation();
                classUserRelation.setTeacherId(teacherInfo.getId());
                classUserRelationMapper.deleteClassUserRelation(classUserRelation);

                String classId = "";

                if(relationAll != null && relationAll.size() > 0){
                    for (int i = 0; i < relationAll.size(); i++) {
                        if(classId.equals("")){
                            classId = "'" +relationAll.get(i).getClassId()+ "'";
                        }else{
                            classId = classId + "," +"'" +relationAll.get(i).getClassId() + "'";
                        }
                    }
                }

                ClassUserRelation select2 = new ClassUserRelation();
                select2.setClassId("(" + classId +")");

                List<ClassUserRelation> classUserRelationList = classUserRelationMapper.getClassUserRelationLists(select2);

                List<ClassInfo> classInfoList1 = new ArrayList<>();

                if(classUserRelationList != null && classUserRelationList.size() > 0){
                    for (int i = 0; i < relationAll.size(); i++) {

                        List<PersonJson> jsonList = new ArrayList<>();
                        String teacher_name = "";
                        ClassInfo info = new ClassInfo();
                        for (int j = 0; j < classUserRelationList.size(); j++) {

                            if(relationAll.get(i).getClassId().equals(classUserRelationList.get(j).getClassId())){

                                PersonJson json = new PersonJson();
                                json.setId(classUserRelationList.get(j).getTeacherId());
                                json.setName(classUserRelationList.get(j).getDef2());
                                json.setType("person");
                                jsonList.add(json);
                                if(teacher_name.equals("")){
                                    teacher_name = classUserRelationList.get(j).getDef2();
                                }else{
                                    teacher_name = teacher_name + "," +classUserRelationList.get(j).getDef2();
                                }
                            }
                        }
                        info.setId(relationAll.get(i).getClassId());

                        if(jsonList != null && jsonList.size() > 0){
                            info.setClassTeacher(JSONObject.toJSON(jsonList).toString());
                            info.setClassTeacherName(teacher_name);

                        }else{
                            info.setClassTeacher("");
                            info.setClassTeacherName("");
                        }
                        classInfoList1.add(info);
                    }

                    if (classInfoList1.size() < 100) {
                        classInfoMapper.updateBatchClassInfoList(classInfoList1);
                    } else {
                        int len = 100;
                        int size = classInfoList1.size();
                        int count = (size + len - 1) / len;
                        for (int i = 0; i < count; i++) {
                            List<ClassInfo> subListOK = classInfoList1.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
                            classInfoMapper.updateBatchClassInfoList(subListOK);
                        }
                    }
                }
            }
        }

    }
}
