package com.cf.kindergarten.common.schedule;

import com.cf.core.common.annotation.CfTask;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Project : cf-core
 * @Package Name : com.cf.demo.common.schedule
 * @Description : TODO
 * @Author : chenfeng
 * @Creation Date : 2018年06月28日 15:53
 * @ModificationHistory Who When What
 * _________ ________________ ____________________________________________
 */
@DisallowConcurrentExecution
@CfTask
@Component
public class Job1 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

}
