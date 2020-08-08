package com.cqjtu.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqjtu.common.annotation.DataFilter;
import com.cqjtu.common.constants.UserEnum;
import com.cqjtu.common.utils.Constant;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.Query;
import com.cqjtu.modules.sys.dao.SysUserDao;
import com.cqjtu.modules.sys.entity.SysDeptEntity;
import com.cqjtu.modules.sys.entity.SysUserEntity;
import com.cqjtu.modules.sys.service.ISysDeptService;
import com.cqjtu.modules.sys.service.ISysUserRoleService;
import com.cqjtu.modules.sys.service.ISysUserService;
import com.cqjtu.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/** 系统用户 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity>
    implements ISysUserService {
  @Autowired private ISysUserRoleService sysUserRoleService;
  @Autowired private ISysDeptService sysDeptService;
  @Autowired private ISysUserService sysUserService;

  @Override
  public void rollbackTest() {
    SysUserEntity user = sysUserService.selectById(1);
    user.setStatus(0);
    sysUserService.update(user);
    System.out.println("a ok ......");
    SysUserServiceImpl sysUserService = new SysUserServiceImpl();
    sysUserService.b();
  }

  @Transactional(rollbackFor = Exception.class)
  protected void b() {
    SysUserEntity user = sysUserService.selectById(2);
    user.setStatus(0);
    sysUserService.update(user);
    System.out.println("b ok ......");
    int i = 1 / 0;
  }

  @Override
  public List<Long> queryAllMenuId(Long userId) {
    return baseMapper.queryAllMenuId(userId);
  }

  @Override
  @DataFilter(subDept = true, user = false)
  public PageUtils queryPage(Map<String, Object> params) {
    String username = (String) params.get("username");
    String userType = (String) params.get("type");
    Page<SysUserEntity> page =
        this.selectPage(
            new Query<SysUserEntity>(params).getPage(),
            new EntityWrapper<SysUserEntity>()
                .like(StringUtils.isNotBlank(username), "username", username)
                .eq(StringUtils.isNotBlank(userType), "type", Integer.valueOf(userType))
                .addFilterIfNeed(
                    params.get(Constant.SQL_FILTER) != null,
                    (String) params.get(Constant.SQL_FILTER)));
    for (SysUserEntity sysUserEntity : page.getRecords()) {
      SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
      String deptName = sysDeptEntity == null ? "" : sysDeptEntity.getName();
      sysUserEntity.setDeptName(deptName);
    }
    return new PageUtils(page);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(SysUserEntity user) {
    user.setCreateTime(new Date());
    if (UserEnum.BACK.getType().equals(user.getType().toString())) {
      // sha256加密
      String salt = RandomStringUtils.randomAlphanumeric(20);
      user.setSalt(salt);
      user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
      user.setType(Integer.valueOf(UserEnum.BACK.getType()));
    } else if (UserEnum.FRONT.getType().equals(user.getType().toString())) {
      user.setType(Integer.valueOf(UserEnum.FRONT.getType()));
    }
    this.insert(user);
    // 保存用户与角色关系
    if (UserEnum.BACK.getType().equals(user.getType().toString())) {
      sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(SysUserEntity user) {
    if (StringUtils.isBlank(user.getPassword())) {
      user.setPassword(null);
    } else {
      if (UserEnum.BACK.getType().equals(user.getType().toString())) {
        SysUserEntity userEntity = this.selectById(user.getUserId());
        user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
      }
    }
    this.updateById(user);
    // 保存用户与角色关系
    if (UserEnum.BACK.getType().equals(user.getType().toString())) {
      sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }
  }

  @Override
  public boolean updatePassword(Long userId, String password, String newPassword) {
    SysUserEntity userEntity = new SysUserEntity();
    userEntity.setPassword(newPassword);
    return this.update(
        userEntity,
        new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
  }
}
