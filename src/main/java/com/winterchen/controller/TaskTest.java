package com.winterchen.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by zy on 2019/11/5.
 */

/*@Component*/
public class TaskTest {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // 定义每过5秒执行任务
    @Scheduled(fixedRate = 5000)
    //@Scheduled(cron = "4-40 * * * * ?")    cron表达式地址：http://cron.qqe2.com  ；springboot定时任务支持6位，不支持年
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }

}
