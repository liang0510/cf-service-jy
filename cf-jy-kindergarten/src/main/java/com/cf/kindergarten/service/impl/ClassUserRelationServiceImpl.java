package com.cf.kindergarten.service.impl;

import com.cf.kindergarten.mybatis.cf_app.ClassInfoMapper;
import com.cf.kindergarten.mybatis.cf_app.ClassUserRelation;
import com.cf.kindergarten.mybatis.cf_app.ClassUserRelationMapper;
import com.cf.kindergarten.service.ClassUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassUserRelationServiceImpl implements ClassUserRelationService {

    @Autowired
    private ClassUserRelationMapper classUserRelationMapper;

    @Override
    public void deleteClassUserRelation(ClassUserRelation relation) {
        classUserRelationMapper.deleteClassUserRelation(relation);
    }

    @Override
    public List<ClassUserRelation> getClassUserRelationLists(ClassUserRelation relation) {
        return    classUserRelationMapper.getClassUserRelationLists(relation);
    }


}
