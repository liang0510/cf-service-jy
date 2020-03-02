package com.cf.kindergarten.service.impl;

import com.cf.core.base.mybatis.cf_base.SysUser;
import com.cf.kindergarten.mybatis.cf_app.ChildrenInfo;
import com.cf.kindergarten.mybatis.cf_app.ChildrenInfoMapper;
import com.cf.kindergarten.mybatis.cf_app.ClassInfo;
import com.cf.kindergarten.mybatis.cf_app.ClassInfoMapper;
import com.cf.kindergarten.service.ChildrenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenInfoServiceImpl implements ChildrenInfoService {

    @Autowired
    private ChildrenInfoMapper childrenInfoMapper;

    @Override
    public Integer selectChildInfoExistsCount(ChildrenInfo info) {
        return childrenInfoMapper.selectChildInfoExistsCount(info);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return childrenInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(ChildrenInfo record) {
        return childrenInfoMapper.insertSelective(record);
    }

    @Override
    public ChildrenInfo selectByPrimaryKey(String id) {
        return childrenInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateChildrenInfo(ChildrenInfo info) {
        childrenInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public void deleteChildrenRelaction(String pid) {
        childrenInfoMapper.deleteChildrenRelaction(pid);
    }

    @Override
    public List<SysUser> selectChildInfoRelactionUser(String id) {
        return childrenInfoMapper.selectChildInfoRelactionUser(id);
    }

}
