package com.cqjtu.modules.oss.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.modules.oss.entity.SysOssEntity;

import java.util.Map;

/** 文件上传 */
public interface ISysOssService extends IService<SysOssEntity> {

  PageUtils queryPage(Map<String, Object> params);
}
