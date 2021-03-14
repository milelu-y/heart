package com.milelu.web.controller.pay;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.ip.IpUtils;
import com.milelu.service.domain.EvalOrder;
import com.milelu.service.service.wechat.WeChatService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/3/12 10:06
 */
@RestController
@RequestMapping("/weChat/pay")
public class WeChatPayController {

    @Autowired
    private WeChatService weChatService;

    @PostMapping("/h5")
    public AjaxResult payH5(@RequestBody EvalOrder evalOrder, HttpServletRequest request) {
        String ipAddr = IpUtils.getIpAddr(request);
        return weChatService.pay(evalOrder, ipAddr);
    }

    @PostMapping("/paySuccess")
    public AjaxResult paySuccess(HttpServletRequest request, HttpServletResponse response) {
        weChatService.paySuccess(request, response);
        return AjaxResult.success();
    }

    @PostMapping("/queryOrder")
    public AjaxResult queryOrder(@RequestBody EvalOrder evalOrder) {
        return weChatService.queryOrder(evalOrder);
    }
}
