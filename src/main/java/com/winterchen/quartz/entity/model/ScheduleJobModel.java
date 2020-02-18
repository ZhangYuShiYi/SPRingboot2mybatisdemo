package com.winterchen.quartz.entity.model;

import lombok.Data;

/**
 * Created by zy on 2020/2/11.
 */
@Data
public class ScheduleJobModel {

    private Integer id;

    private String groupName;

    private String jobName;

    private String cron;
}
