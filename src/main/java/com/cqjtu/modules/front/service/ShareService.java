package com.cqjtu.modules.front.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqjtu.common.utils.PageUtils;
import com.cqjtu.modules.front.entity.ShareEntity;

import java.util.Map;

/**
 * 分享表
 *
 * @author lanxinghua
 * @email lanxinghua@2dfire.com
 * @date 2019-03-17 21:38:15
 */
public interface ShareService extends IService<ShareEntity> {

  PageUtils queryPage(Map<String, Object> params);
}
