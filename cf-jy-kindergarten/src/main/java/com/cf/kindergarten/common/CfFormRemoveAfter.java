package com.cf.kindergarten.common;

import com.cf.core.common.method.CommonSave;
import com.cf.core.common.method.CommonSaveAfter;
import org.springframework.stereotype.Component;

/**
 * @Project : cf-core
 * @Package Name : com.cf.core.base.service.impl
 * @Description : 平台保存后操作
 * @Author : chenfeng
 * @Creation Date : 2019年02月15日 13:57
 * @ModificationHistory Who When What
 * @CfRemoveAfter 删除后注解，funCode需要注入的功能码
 * SeniorResultModel beforeParams 原始参数 Map<String,Object> map 前端的原始参数 可以通过ID是否空，判断新建、修改
 * SeniorResultModel afterParams 系统操作后的返回值，含id主表编码，根据业务需求扩充
 * SeniorResultModel customParams 用户自定义参数，支持参数传递，操作前->操作中->操作后
 * return 仅用于判断方法执行成功/失败，可以不返回
 * _________ ________________ ____________________________________________
 */
@Component
public class CfFormRemoveAfter extends CommonSave implements CommonSaveAfter {

}
