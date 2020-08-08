package com.cqjtu.modules.front.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.Query;

import com.cqjtu.modules.front.dao.UserFileDao;
import com.cqjtu.modules.front.entity.UserFileEntity;
import com.cqjtu.modules.front.service.UserFileService;

@Service("userFileService")
public class UserFileServiceImpl extends ServiceImpl<UserFileDao, UserFileEntity>
    implements UserFileService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    Page<UserFileEntity> page =
        this.selectPage(
            new Query<UserFileEntity>(params).getPage(), new EntityWrapper<UserFileEntity>());

    return new PageUtils(page);
  }

  @Override
  public List<UserFileEntity> getFilesByUserId(Long userId) {
    return this.selectList(
        new EntityWrapper<UserFileEntity>().eq(userId != null, "user_id", userId));
  }
}
