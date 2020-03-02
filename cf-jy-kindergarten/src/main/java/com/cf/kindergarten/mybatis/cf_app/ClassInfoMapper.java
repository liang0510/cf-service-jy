package com.cf.kindergarten.mybatis.cf_app;

import com.cf.kindergarten.mybatis.cf_app.ClassInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClassInfo record);

    int insertSelective(ClassInfo record);

    ClassInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClassInfo record);

    int updateByPrimaryKey(ClassInfo record);

    void updateBatchClassInfoList(List list);

    String selectCorpByChildId(String id);

    Integer selectClassInfoExistsCount(ClassInfo info);

    void updateClassInfoByChildNum(String id);

}