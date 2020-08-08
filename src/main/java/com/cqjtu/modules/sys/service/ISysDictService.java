package com.cqjtu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.modules.sys.entity.SysDictEntity;

import java.util.Map;

/** 数据字典 */
public interface ISysDictService extends IService<SysDictEntity> {

  PageUtils queryPage(Map<String, Object> params);
}
