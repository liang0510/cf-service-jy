package com.cf.kindergarten.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cf.core.base.mybatis.cf_base.*;
import com.cf.core.base.service.SysToolService;
import com.cf.core.common.model.ModelUtil;
import com.cf.kindergarten.mybatis.cf_app.*;
import com.cf.kindergarten.service.ClassInfoService;
import com.cf.kindergarten.util.KindergrartenConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassInfoServiceImpl  implements ClassInfoService {

    private static Logger log = LoggerFactory.getLogger(ClassInfoServiceImpl.class);

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Autowired
    private ClassUserRelationMapper classUserRelationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysToolService sysToolService;

    @Override
    public void updateClassInfo(ClassInfo info) {
        classInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return classInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(ClassInfo record) {
        return classInfoMapper.insertSelective(record);
    }

    @Override
    public ClassInfo selectByPrimaryKey(String id) {
        return classInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTeacherId(String id) {
        try{
            //根据编号获取党组织信息
            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(id);

            if(classInfo != null) {

                //查询一个班级有多少个老师
                List<SysUser> sysUserList =  sysToolService.transformPersonSelector(classInfo.getClassTeacher());

                List<ClassUserRelation> relationList = new ArrayList<>();

                String teacher_id = "";

                List<ClassUserRelation> relationAll = classUserRelationMapper.getClassUserRelationClassExists(classInfo.getId());  //查询出原来该班级有几个老师

                for (int i = 0; i <sysUserList.size() ; i++) {

                    TeacherInfo staff = teacherInfoMapper.getTeacherInfoByUserName(sysUserList.get(i).getLoginName());

                    if(staff != null){
                        ClassUserRelation relation = new ClassUserRelation();
                        ModelUtil.setField(relation);//设置实体默认值
                        relation.setClassId(id);
                        relation.setTeacherId(staff.getId());
                        relationList.add(relation);
                        relationAll.add(relation);  //把现在的也加上
                    }
                }

                // 先把班级关联老师删除 然后在添加
                ClassUserRelation classUserRelation = new ClassUserRelation();
                classUserRelation.setClassId(classInfo.getId());
                classUserRelationMapper.deleteClassUserRelation(classUserRelation);

                //批量更新每100插入
                if(relationList != null && relationList.size() > 0){
                    if (relationList.size() < 100) {
                        classUserRelationMapper.insertBatchClassUserRelation(relationList);
                    } else {
                        int len = 100;
                        int size = relationList.size();
                        int count = (size + len - 1) / len;
                        for (int i = 0; i < count; i++) {
                            List<ClassUserRelation> subListOK = relationList.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
                            classUserRelationMapper.insertBatchClassUserRelation(subListOK);
                        }
                    }
                }

                if(relationAll != null && relationAll.size() > 0){
                    for (int i = 0; i < relationAll.size(); i++) {
                        if(teacher_id.equals("")){
                            teacher_id = "'" +relationAll.get(i).getTeacherId()+ "'";
                        }else{
                            teacher_id = teacher_id + "," +"'" +relationAll.get(i).getTeacherId() + "'";
                        }
                    }

                    ClassUserRelation select = new ClassUserRelation();
                    select.setTeacherId("(" + teacher_id +")");
                    List<ClassUserRelation> lists = classUserRelationMapper.getClassUserRelationLists(select);

                    List<TeacherInfo> TeacherInfoLists = new ArrayList<>();
                        if(lists != null && lists.size() > 0){

                            for (int i = 0; i < relationAll.size(); i++) {

                                TeacherInfo teacherInfo = new TeacherInfo();
                                String class_list = "";
                                for (int j = 0; j < lists.size(); j++) {
                                    ClassUserRelation userRelation = lists.get(j);
                                    if(relationAll.get(i).getTeacherId().equals(userRelation.getTeacherId())){
                                        if(class_list.equals("")){
                                            class_list = userRelation.getClassId();
                                        }else{
                                            class_list = class_list + "," +userRelation.getClassId();
                                        }
                                    }
                                }
                                teacherInfo.setId(relationAll.get(i).getTeacherId());
                                teacherInfo.setDef1(class_list);
                                TeacherInfoLists.add(teacherInfo);
                            }

                            if (TeacherInfoLists.size() < 100) {
                                teacherInfoMapper.updateBatchTeacherInfo(TeacherInfoLists);
                            } else {
                                int len = 100;
                                int size = TeacherInfoLists.size();
                                int count = (size + len - 1) / len;
                                for (int i = 0; i < count; i++) {
                                    List<TeacherInfo> subListOK = TeacherInfoLists.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
                                    teacherInfoMapper.updateBatchTeacherInfo(subListOK);
                                }
                            }
                        }
                    }
            }

        }catch (Exception e){
            log.info("保存用户异常：" + e.getMessage());
            System.out.println("保存用户异常：" + e.getMessage());

        }
    }

    @Override
    public Integer selectClassInfoExistsCount(ClassInfo info) {
        return  classInfoMapper.selectClassInfoExistsCount(info);
    }

    @Override
    public void updateClassInfoByChildNum(String id) {
        classInfoMapper.updateClassInfoByChildNum(id);
    }

}
