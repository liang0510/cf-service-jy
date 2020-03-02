package com.cf.kindergarten.mybatis.cf_app;

import com.cf.kindergarten.mybatis.cf_app.ClassUserRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassUserRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClassUserRelation record);

    int insertSelective(ClassUserRelation record);

    ClassUserRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClassUserRelation record);

    int updateByPrimaryKey(ClassUserRelation record);

    void deleteClassUserRelation(ClassUserRelation relation);

    List<ClassUserRelation> getClassUserRelationLists(ClassUserRelation relation);

    void insertBatchClassUserRelation(List list);

    List<ClassUserRelation> getClassUserRelationTeacherExists(String id);

    List<ClassUserRelation> getClassUserRelationClassExists(String id);

}