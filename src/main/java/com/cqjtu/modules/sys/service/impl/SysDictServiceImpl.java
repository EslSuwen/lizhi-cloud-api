package com.cqjtu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.Query;
import com.cqjtu.modules.sys.dao.SysDictDao;
import com.cqjtu.modules.sys.entity.SysDictEntity;
import com.cqjtu.modules.sys.service.ISysDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity>
    implements ISysDictService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    String name = (String) params.get("name");
    Page<SysDictEntity> page =
        this.selectPage(
            new Query<SysDictEntity>(params).getPage(),
            new EntityWrapper<SysDictEntity>().like(StringUtils.isNotBlank(name), "name", name));
    return new PageUtils(page);
  }
}
