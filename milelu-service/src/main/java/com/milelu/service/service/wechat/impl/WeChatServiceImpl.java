package com.milelu.service.service.wechat.impl;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.wechat.MyWXPayConfig;
import com.milelu.common.wechat.WXPay;
import com.milelu.service.service.wechat.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * @author MILELU
 * @date 2021/3/12 10:16
 */
@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Override
    public AjaxResult pay(String ip) {
        Map<String, String> params = new HashMap<>(16);
        params.put("body", "腾讯充值中心-QQ会员充值");
        params.put("out_trade_no", "ORDER" + getId());
        params.put("spbill_create_ip", ip);
        params.put("total_fee", "1");
        params.put("trade_type", "MWEB");
        log.info("支付参数" + params);
        log.info("================分割线===============");
        final String SUCCESS_NOTIFY = "https://mana.cn.utools.club/weChat/pay/paySuccess";
//        boolean uesSandbox=false;
        try {
            MyWXPayConfig myWXPayConfig = MyWXPayConfig.getInstance();
            WXPay wxPay = new WXPay(myWXPayConfig, SUCCESS_NOTIFY, false, false);
            Map<String, String> map = wxPay.unifiedOrder(wxPay.fillRequestData(params), 15000, 15000);
            String mwebUrl = map.get("mweb_url");
            String redirectUrl = "http%3a%2f%2fmilelu.test.utools.club";
//            String redirectUrl = "mana.cn.utools.club";
//            map.put("referer", redirectUrl);
            mwebUrl += String.format("&redirect_url=%s", redirectUrl);
            map.put("mweb_url", mwebUrl);
            return AjaxResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxResult.error();
    }
}
