package com.cqjtu.modules.front.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.modules.front.entity.UserFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户与文件对应关系
 *
 * @author lanxinghua
 * @email lanxinghua@2dfire.com
 * @date 2019-03-17 21:38:15
 */
public interface UserFileService extends IService<UserFileEntity> {

  PageUtils queryPage(Map<String, Object> params);

  /**
   * 获取用户拥有的文件
   *
   * @param userId
   * @return
   */
  List<UserFileEntity> getFilesByUserId(Long userId);
}
