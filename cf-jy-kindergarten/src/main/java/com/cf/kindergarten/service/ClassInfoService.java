package com.cf.kindergarten.service;

import com.cf.core.base.mybatis.cf_base.SysCorporation;
import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.kindergarten.mybatis.cf_app.ClassInfo;
import com.cf.kindergarten.mybatis.cf_app.TeacherInfo;

import java.util.List;

/**
 * @Project: cf-service
 * @Package: com.cf.kindergarten.service
 * @Description: null
 * @Author: liangtao
 * @CreateDate: 2020/2/19 10:30
 * @ModificationHistory: （who whatTime doWhat）
 */
public interface ClassInfoService {

    void updateClassInfo(ClassInfo info);

    int deleteByPrimaryKey(String id);

    int insertSelective(ClassInfo record);

    ClassInfo selectByPrimaryKey(String id);

    void updateTeacherId(String id);

    Integer selectClassInfoExistsCount(ClassInfo info);

    void updateClassInfoByChildNum(String id);
}
