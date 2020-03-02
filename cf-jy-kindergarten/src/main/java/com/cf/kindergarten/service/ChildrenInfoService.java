package com.cf.kindergarten.service;

import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.kindergarten.mybatis.cf_app.ChildrenInfo;
import com.cf.kindergarten.mybatis.cf_app.ClassInfo;

import java.util.List;

public interface ChildrenInfoService {

    Integer selectChildInfoExistsCount(ChildrenInfo info);

    int deleteByPrimaryKey(String id);

    int insertSelective(ChildrenInfo record);

    ChildrenInfo selectByPrimaryKey(String id);

    void updateChildrenInfo(ChildrenInfo info);

    void  deleteChildrenRelaction(String pid);

    List<SysUser> selectChildInfoRelactionUser(String id);
}
