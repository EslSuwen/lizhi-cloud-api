package com.cqjtu.modules.sys.controller;

import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.R;
import com.cqjtu.common.validator.ValidatorUtils;
import com.cqjtu.modules.sys.entity.SysDictEntity;
import com.cqjtu.modules.sys.service.ISysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/** 数据字典 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
  @Autowired private ISysDictService sysDictService;

  /** 列表 */
  @RequestMapping("/list")
  @RequiresPermissions("sys:dict:list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = sysDictService.queryPage(params);

    return R.ok().put("page", page);
  }

  /** 信息 */
  @RequestMapping("/info/{id}")
  @RequiresPermissions("sys:dict:info")
  public R info(@PathVariable("id") Long id) {
    SysDictEntity dict = sysDictService.selectById(id);

    return R.ok().put("dict", dict);
  }

  /** 保存 */
  @RequestMapping("/save")
  @RequiresPermissions("sys:dict:save")
  public R save(@RequestBody SysDictEntity dict) {
    // 校验类型
    ValidatorUtils.validateEntity(dict);

    sysDictService.insert(dict);

    return R.ok();
  }

  /** 修改 */
  @RequestMapping("/update")
  @RequiresPermissions("sys:dict:update")
  public R update(@RequestBody SysDictEntity dict) {
    // 校验类型
    ValidatorUtils.validateEntity(dict);

    sysDictService.updateById(dict);

    return R.ok();
  }

  /** 删除 */
  @RequestMapping("/delete")
  @RequiresPermissions("sys:dict:delete")
  public R delete(@RequestBody Long[] ids) {
    sysDictService.deleteBatchIds(Arrays.asList(ids));

    return R.ok();
  }
}