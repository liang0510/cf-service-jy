package com.cf.kindergarten.service;

import com.cf.core.base.mybatis.cf_base.SysCorporation;
import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.kindergarten.mybatis.cf_app.TeacherInfo;

import java.util.List;
import java.util.Map;

/**
 * @Project: cf-service
 * @Package: com.cf.kindergarten.service
 * @Description: null
 * @Author: liangtao
 * @CreateDate: 2020/2/19 10:30
 * @ModificationHistory: （who whatTime doWhat）
 */
public interface TeacherUserService {


    TeacherInfo getTeacherInfo(String id);

    void updateTeacherInfo(TeacherInfo partyUser);

    /**
     * 根据教师编号保存对应的系统用户信息
     * @param djId
     */
    void SaveSysUserInfo(String djId);

    int removeTeacherInfoById(String id);

    void updateSysUser(SysUser sysUser);

    List<SysUser> selectTeacherInfoList(SysUser sysUser);

    List<SysCorporation> getSysCorporationListById(SysCorporation corporation);

    void SaveParentSysUserInfo(List<Map> list,String id);

    Integer selectTeacherInfoExistsCount(TeacherInfo info);

    /**
     * 获取老师对应的班级并同步班级信息
     * @param info
     */
    void getTeacherByClass(TeacherInfo info);


    void removeTeacherByClass(TeacherInfo info);

}
