package com.cf.kindergarten.common;

import com.cf.core.common.annotation.CfNodeBefore;
import com.cf.core.common.method.CommonSave;
import com.cf.core.common.method.CommonSaveBefore;
import com.cf.core.common.model.SeniorResultModel;
import org.springframework.stereotype.Component;

/**
 * @Project : trunk
 * @Package Name : com.cf.demo.common
 * @Description : TODO
 * @Author : chenfeng
 * @Creation Date : 2019年02月16日 15:18
 * @ModificationHistory Who When What
 * _________ ________________ ____________________________________________
 */
@Component
public class CfWorkflowNodeBefore extends CommonSave implements CommonSaveBefore {

    @CfNodeBefore(funCode="",nodeCode="")
    public SeniorResultModel test(SeniorResultModel resultModel){
        return resultModel;
    }
}
