package com.milelu.web.controller.pay;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.ip.IpUtils;
import com.milelu.service.service.wechat.WeChatService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public AjaxResult payH5(HttpServletRequest request) {
        String ipAddr = IpUtils.getIpAddr(request);
        return weChatService.pay(ipAddr);
    }

    @PostMapping("/paySuccess")
    public AjaxResult paySuccess() {
        return AjaxResult.success();
    }
}
