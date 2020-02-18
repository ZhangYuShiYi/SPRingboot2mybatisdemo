package com.winterchen.quartz.dao;

import com.winterchen.quartz.entity.po.ScheduleJobPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zy on 2020/2/11.
 */
@Repository
public interface ScheduleJobDaoRepository extends JpaRepository<ScheduleJobPo, Integer>, JpaSpecificationExecutor<ScheduleJobPo> {
    public ScheduleJobPo findByIdAndStatus(Integer id, Integer status);

    public List<ScheduleJobPo> findAllByStatus(Integer status);

    public List<ScheduleJobPo> findByGroupNameAndJobNameAndStatus(String groupName, String jobName, Integer status);

    public List<ScheduleJobPo> findAllByStatusInOrderByCreateTimeDesc(List<Integer> statusList);
}
