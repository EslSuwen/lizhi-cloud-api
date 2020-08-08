package com.cqjtu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.modules.sys.entity.SysRoleEntity;

import java.util.Map;

/** 角色 */
public interface ISysRoleService extends IService<SysRoleEntity> {

  PageUtils queryPage(Map<String, Object> params);

  void save(SysRoleEntity role);

  void update(SysRoleEntity role);

  void deleteBatch(Long[] roleIds);
}
