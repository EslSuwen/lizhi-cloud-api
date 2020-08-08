package com.cqjtu.modules.oss.controller;

import com.cqjtu.common.exception.BizException;
import com.cqjtu.common.group.AliyunGroup;
import com.cqjtu.common.group.QcloudGroup;
import com.cqjtu.common.group.QiniuGroup;
import com.cqjtu.modules.oss.service.ISysOssService;
import com.cqjtu.modules.sys.service.ISysConfigService;
import com.google.gson.Gson;
import com.cqjtu.common.utils.ConfigConstant;
import com.cqjtu.common.utils.Constant;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.common.utils.R;
import com.cqjtu.common.validator.ValidatorUtils;
import com.cqjtu.modules.oss.cloud.CloudStorageConfig;
import com.cqjtu.modules.oss.cloud.OSSFactory;
import com.cqjtu.modules.oss.entity.SysOssEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/** 文件上传 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
  @Autowired private ISysOssService sysOssService;
  @Autowired private ISysConfigService sysConfigService;

  private static final String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

  /** 列表 */
  @RequestMapping("/list")
  @RequiresPermissions("sys:oss:all")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = sysOssService.queryPage(params);
    return R.ok().put("page", page);
  }

  /** 云存储配置信息 */
  @RequestMapping("/config")
  @RequiresPermissions("sys:oss:all")
  public R config() {
    CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
    return R.ok().put("config", config);
  }

  /** 保存云存储配置信息 */
  @RequestMapping("/saveConfig")
  @RequiresPermissions("sys:oss:all")
  public R saveConfig(@RequestBody CloudStorageConfig config) {
    // 校验类型
    ValidatorUtils.validateEntity(config);
    if (config.getType() == Constant.CloudService.QINIU.getValue()) {
      // 校验七牛数据
      ValidatorUtils.validateEntity(config, QiniuGroup.class);
    } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
      // 校验阿里云数据
      ValidatorUtils.validateEntity(config, AliyunGroup.class);
    } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
      // 校验腾讯云数据
      ValidatorUtils.validateEntity(config, QcloudGroup.class);
    }
    sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
    return R.ok();
  }

  /** 上传文件 */
  @RequestMapping("/upload")
  @RequiresPermissions("sys:oss:all")
  public R upload(@RequestParam("file") MultipartFile file) throws Exception {
    if (file.isEmpty()) {
      throw new BizException("上传文件不能为空");
    }
    // 上传文件
    String suffix =
        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
    // 保存文件信息
    SysOssEntity ossEntity = new SysOssEntity();
    ossEntity.setUrl(url);
    ossEntity.setCreateDate(new Date());
    sysOssService.insert(ossEntity);
    return R.ok().put("url", url);
  }

  /** 删除 */
  @RequestMapping("/delete")
  @RequiresPermissions("sys:oss:all")
  public R delete(@RequestBody Long[] ids) {
    sysOssService.deleteBatchIds(Arrays.asList(ids));
    return R.ok();
  }
}
