package com.cf.kindergarten.service;

import com.cf.kindergarten.mybatis.cf_app.ClassInfo;
import com.cf.kindergarten.mybatis.cf_app.ClassUserRelation;

import java.util.List;

public interface ClassUserRelationService {

    void deleteClassUserRelation(ClassUserRelation relation);

    List<ClassUserRelation> getClassUserRelationLists(ClassUserRelation relation);

}
