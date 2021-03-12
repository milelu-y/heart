package com.milelu.service.service.wechat;

import com.milelu.common.core.domain.AjaxResult;

/**
 * @author MILELU
 * @date 2021/3/12 10:16
 */
public interface WeChatService {
    AjaxResult pay(String ip);
}
