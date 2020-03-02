package com.cf.kindergarten.mybatis.cf_app;

import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.kindergarten.mybatis.cf_app.ChildrenInfo;

import java.util.List;

public interface ChildrenInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChildrenInfo record);

    int insertSelective(ChildrenInfo record);

    ChildrenInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChildrenInfo record);

    int updateByPrimaryKey(ChildrenInfo record);

    Integer selectChildInfoExistsCount(ChildrenInfo info);

    void  deleteChildrenRelaction(String pid);

    List<SysUser> selectChildInfoRelactionUser(String id);

}