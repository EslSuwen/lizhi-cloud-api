package com.cqjtu.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.cqjtu.common.annotation.SysLog;
import com.cqjtu.common.constants.SessionHolder;
import com.cqjtu.common.constants.UserEnum;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.R;
import com.cqjtu.common.validator.Assert;
import com.cqjtu.common.validator.ValidatorUtils;
import com.cqjtu.common.validator.group.AddGroup;
import com.cqjtu.common.validator.group.UpdateGroup;
import com.cqjtu.modules.sys.entity.SysUserEntity;
import com.cqjtu.modules.sys.service.ISysUserRoleService;
import com.cqjtu.modules.sys.service.ISysUserService;
import com.cqjtu.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
  @Autowired private ISysUserService sysUserService;
  @Autowired private ISysUserRoleService sysUserRoleService;

  /** 所有用户列表 */
  @RequestMapping("/list")
  @RequiresPermissions("sys:user:list")
  public R list(@RequestParam Map<String, Object> params) {
    // 测试 ThreadLocal,保存登陆信息
    SysUserEntity sysUserEntity = SessionHolder.getSsoSession();
    System.out.println("测试：" + Thread.currentThread().getName() + JSON.toJSONString(sysUserEntity));
    params.put("type", UserEnum.BACK.getType());
    PageUtils page = sysUserService.queryPage(params);
    return R.ok().put("page", page);
  }

  /** 获取登录的用户信息 */
  @RequestMapping("/info")
  public R info() {
    return R.ok().put("user", getUser());
  }

  /** 修改登录用户密码 */
  @SysLog("修改密码")
  @RequestMapping("/password")
  public R password(String password, String newPassword) {
    Assert.isBlank(newPassword, "新密码不为能空");

    // 原密码
    password = ShiroUtils.sha256(password, getUser().getSalt());
    // 新密码
    newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

    // 更新密码
    boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
    if (!flag) {
      return R.error("原密码不正确");
    }

    return R.ok();
  }

  public static void main(String[] args) {
    System.out.println(ShiroUtils.sha256("admin", "YzcmCZNvbXocrsz9dm8e"));
  }

  /** 用户信息 */
  @RequestMapping("/info/{userId}")
  @RequiresPermissions("sys:user:info")
  public R info(@PathVariable("userId") Long userId) {
    SysUserEntity user = sysUserService.selectById(userId);

    // 获取用户所属的角色列表
    List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
    user.setRoleIdList(roleIdList);

    return R.ok().put("user", user);
  }

  /** 保存用户 */
  @SysLog("保存用户")
  @RequestMapping("/save")
  @RequiresPermissions("sys:user:save")
  public R save(@RequestBody SysUserEntity user) {
    ValidatorUtils.validateEntity(user, AddGroup.class);
    user.setType(Integer.valueOf(UserEnum.BACK.getType()));
    sysUserService.save(user);

    return R.ok();
  }

  /** 修改用户 */
  @SysLog("修改用户")
  @RequestMapping("/update")
  @RequiresPermissions("sys:user:update")
  public R update(@RequestBody SysUserEntity user) {
    ValidatorUtils.validateEntity(user, UpdateGroup.class);
    user.setType(Integer.valueOf(UserEnum.BACK.getType()));
    sysUserService.update(user);

    return R.ok();
  }

  /** 删除用户 */
  @SysLog("删除用户")
  @RequestMapping("/delete")
  @RequiresPermissions("sys:user:delete")
  public R delete(@RequestBody Long[] userIds) {
    if (ArrayUtils.contains(userIds, 1L)) {
      return R.error("系统管理员不能删除");
    }

    if (ArrayUtils.contains(userIds, getUserId())) {
      return R.error("当前用户不能删除");
    }

    sysUserService.deleteBatchIds(Arrays.asList(userIds));

    return R.ok();
  }
}
