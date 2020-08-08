package com.cqjtu.modules.oss.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.Query;
import com.cqjtu.modules.oss.dao.SysOssDao;
import com.cqjtu.modules.oss.entity.SysOssEntity;
import com.cqjtu.modules.oss.service.ISysOssService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity>
    implements ISysOssService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    Page<SysOssEntity> page = this.selectPage(new Query<SysOssEntity>(params).getPage());
    return new PageUtils(page);
  }
}
