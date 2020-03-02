package com.cf.kindergarten.web;

import com.cf.core.base.service.SysRoleUserService;
import com.cf.core.base.service.SysUserService;
import com.cf.core.base.web.SysBaseWeb;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Project: cf-service
 * @Package: com.cf.kindergarten.web
 * @Description: 班级管理
 * @Author: liangtao
 * @CreateDate: 2020/2/19 10:13
 * @ModificationHistory: （who whatTime doWhat）
 */
@RestController
@RequestMapping("/classWeb")
@Api(tags = {"智慧幼儿园-班级管理"})
public class ClassInfoWeb extends SysBaseWeb {

    private static Logger log = LoggerFactory.getLogger(ClassInfoWeb.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleUserService sysRoleUserService;


}