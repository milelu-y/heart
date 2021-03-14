package com.milelu.service.service.wechat;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.service.domain.EvalOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MILELU
 * @date 2021/3/12 10:16
 */
public interface WeChatService {
    AjaxResult pay(EvalOrder order,String ip);

    String paySuccess(HttpServletRequest request, HttpServletResponse response);

    AjaxResult queryOrder(EvalOrder evalOrder);
}
