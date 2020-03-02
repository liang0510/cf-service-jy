package com.cf.kindergarten.mybatis.cf_app;

import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.kindergarten.mybatis.cf_app.TeacherInfo;

import java.util.List;

public interface TeacherInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(TeacherInfo record);

    int insertSelective(TeacherInfo record);

    TeacherInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TeacherInfo record);

    int updateByPrimaryKeyWithBLOBs(TeacherInfo record);

    int updateByPrimaryKey(TeacherInfo record);


    List<ClassInfo> getClassInfoLists(TeacherInfo teacherInfo);


    List<SysUser> selectTeacherInfoList(SysUser sysUser);


    TeacherInfo getTeacherInfoByUserName(String username);


    void  updateBatchTeacherInfo(List list);

    Integer selectTeacherInfoExistsCount(TeacherInfo info);

}