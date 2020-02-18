package com.winterchen.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Created by zy on 2020/2/11.
 */
/*@DisallowConcurrentExecution    //相同定义的jobDetail不能并发执行
@PersistJobDataAfterExecution   //jobDataMap数据保存
@Slf4j
public class TestQuartzJob extends QuartzJobBean {

    public TestQuartzJob(){

    }
    *//**
     * 具体任务
     * @throws JobExecutionException
     *//*
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("springboot整合定时任务Quartz:"+new Date());
        String group = context.getJobDetail().getJobDataMap().get("group").toString();
        String name = context.getJobDetail().getJobDataMap().get("name").toString();
        log.info("执行了task...group:{}, name:{}", group, name);
        // 可在此执行定时任务的具体业务
        // ...
    }

}*/
@Slf4j
public class TestQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("springboot整合定时任务Quartz:"+new Date());
        /*String group = context.getJobDetail().getJobDataMap().get("group").toString();
        String name = context.getJobDetail().getJobDataMap().get("name").toString();*/
        String group = context.getJobDetail().getJobDataMap().get("key1").toString();
        String name = context.getJobDetail().getJobDataMap().get("key2").toString();
        log.info("执行了task...group:{}, name:{}", group, name);
        // 可在此执行定时任务的具体业务
        // ...
    }
}
