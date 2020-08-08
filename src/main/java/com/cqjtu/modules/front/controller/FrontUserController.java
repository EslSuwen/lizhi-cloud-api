package com.cqjtu.modules.front.controller;

import com.cqjtu.common.annotation.SysLog;
import com.cqjtu.common.constants.UserEnum;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.R;
import com.cqjtu.common.validator.ValidatorUtils;
import com.cqjtu.common.validator.group.AddGroup;
import com.cqjtu.common.validator.group.UpdateGroup;
import com.cqjtu.modules.sys.controller.AbstractController;
import com.cqjtu.modules.sys.entity.SysUserEntity;
import com.cqjtu.modules.sys.service.ISysUserRoleService;
import com.cqjtu.modules.sys.service.ISysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 前台用户
 *
 * @author lanxinghua
 * @date 2019年3月17日 上午10:40:10
 */
@RestController
@RequestMapping("/front/user")
public class FrontUserController extends AbstractController {
  @Autowired private ISysUserService sysUserService;
  @Autowired private ISysUserRoleService sysUserRoleService;

  /** 所有用户列表 */
  @RequestMapping("/list")
  @RequiresPermissions("front:user:list")
  public R list(@RequestParam Map<String, Object> params) {
    params.put("type", UserEnum.FRONT.getType());
    PageUtils page = sysUserService.queryPage(params);
    return R.ok().put("page", page);
  }

  /** 保存用户 */
  @SysLog("保存用户")
  @RequestMapping("/save")
  @RequiresPermissions("front:user:save")
  public R save(@RequestBody SysUserEntity user) {
    ValidatorUtils.validateEntity(user, AddGroup.class);
    user.setType(Integer.valueOf(UserEnum.FRONT.getType()));
    sysUserService.save(user);
    return R.ok();
  }

  /** 修改用户 */
  @SysLog("修改用户")
  @RequestMapping("/update")
  @RequiresPermissions("front:user:update")
  public R update(@RequestBody SysUserEntity user) {
    ValidatorUtils.validateEntity(user, UpdateGroup.class);
    user.setType(Integer.valueOf(UserEnum.FRONT.getType()));
    sysUserService.update(user);
    return R.ok();
  }

  /** 删除用户 */
  @SysLog("删除用户")
  @RequestMapping("/delete")
  @RequiresPermissions("front:user:delete")
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
