package com.cqjtu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqjtu.modules.sys.entity.SysLogEntity;
import com.cqjtu.common.utils.PageUtils;

import java.util.Map;

/** 系统日志 */
public interface ISysLogService extends IService<SysLogEntity> {

  PageUtils queryPage(Map<String, Object> params);

  boolean deleteAll();
}
