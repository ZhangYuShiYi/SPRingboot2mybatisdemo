package com.winterchen.quartz.controller;

import com.winterchen.quartz.entity.model.ScheduleJobModel;
import com.winterchen.quartz.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zy on 2020/2/11.
 */
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleTestController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 开启
     * @param model
     * @return
     */
    @PostMapping("start")
    public String startSchedule(@RequestBody ScheduleJobModel model){
        scheduleJobService.startSchedule(model);
        return "ok";
    }

    /**
     * 更新
     * @param model
     * @return
     */
    @PostMapping("update")
    public String scheduleUpdateCorn(@RequestBody ScheduleJobModel model){
        scheduleJobService.scheduleUpdateCorn(model);
        return "ok";
    }

    /**
     * 暂停
     * @param model
     * @return
     */
    @PostMapping("/pause")
    public String schedulePause(@RequestBody ScheduleJobModel model){
        scheduleJobService.schedulePause(model);
        return "ok";
    }

    /**
     * 恢复
     * @param model
     * @return
     */
    @PostMapping("/resume")
    public String scheduleResume(@RequestBody ScheduleJobModel model){
        scheduleJobService.scheduleResume(model);
        return "ok";
    }

    /**
     * 删除一个定时任务
     * @param model
     * @return
     */
    @PostMapping("/delete")
    public String scheduleDelete(@RequestBody ScheduleJobModel model){
        scheduleJobService.scheduleDelete(model);
        return "ok";
    }

    /**
     * 删除所有定时任务
     * @param model
     * @return
     */
    @PostMapping("deleteAll")
    public String scheduleDeleteAll(@RequestBody ScheduleJobModel model){
        scheduleJobService.scheduleDeleteAll();
        return "ok";
    }

}
