package com.cqjtu.modules.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/** 定时任务日志 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

  PageUtils queryPage(Map<String, Object> params);
}
