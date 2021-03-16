package com.milelu.service.service.wechat.impl;

import com.milelu.common.constant.WechatConstant;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.wechat.*;
import com.milelu.service.domain.EvalOrder;
import com.milelu.service.mapper.EvalOrderMapper;
import com.milelu.service.service.evaluate.EvalOrderService;
import com.milelu.service.service.wechat.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * @author MILELU
 * @date 2021/3/12 10:16
 */
@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    EvalOrderService evalOrderService;
    @Autowired
    EvalOrderMapper evalOrderMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult pay(EvalOrder order, String ip) {
        Map<String, String> params = new HashMap<>(16);
        order.setId(getId());
        String orderNum = "ORDER" + getDataId() + new Random().nextInt(1000);
        order.setOrderNum(orderNum);
        BigDecimal bigDecimal = new BigDecimal("9.9");
        order.setOrderMoney(bigDecimal);
        order.setPaymentMoney(bigDecimal);
        order.setPaymentMethod(5);
        order.setOrderStatus(0);//支付中
        evalOrderService.insertEvalOrder(order);
        params.put("body", "测试订单");
        params.put("out_trade_no", orderNum);
        params.put("spbill_create_ip", ip);
        params.put("total_fee", "1");
        params.put("trade_type", "MWEB");
        log.info("支付参数" + params);
        log.info("================分割线===============");
        final String SUCCESS_NOTIFY = "https://mana.cn.utools.club/weChat/pay/paySuccess";
        try {
            MyWXPayConfig myWXPayConfig = MyWXPayConfig.getInstance();
            WXPay wxPay = new WXPay(myWXPayConfig, SUCCESS_NOTIFY, false, false);
            Map<String, String> map = wxPay.unifiedOrder(wxPay.fillRequestData(params), 15000, 15000);
            map.put("orderNum", orderNum);
            return AjaxResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxResult.error();
    }


    public static String getDataId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        return format;
    }


    @Override
    public String paySuccess(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/xml");
        //返回给微信的处理结果
        String result = null;
        //读取xml
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer buffer = null;
        try {
            buffer = new StringBuffer();
            inputStream = request.getInputStream();
            String s;
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = bufferedReader.readLine()) != null) {
                buffer.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return validAndUpdateOrder(result, buffer);
    }

    private String validAndUpdateOrder(String result, StringBuffer buffer) {
        try {
            Map<String, String> map = WXPayUtil.xmlToMap(buffer.toString());
            String mchKey = WechatConstant.MCH_KEY;
            boolean signatureValid = WXPayUtil.isSignatureValid(map, mchKey, WXPayConstants.SignType.HMACSHA256);
            if (signatureValid) {
                log.info("验证通过");
                result = setXml("SUCCESS", "OK");
            } else {
                result = setXml("FAIL", "NO");
            }
            if ("SUCCESS".equals(map.get("result_code"))) {
                //判断订单号是否重复
                EvalOrder evalOrder = evalOrderService.selectEvalOrderByOrderNum(map.get("out_trade_no"));
                if (CommonUtils.BeNotNull(evalOrder)) {
                    if (evalOrder.getOrderStatus() == 0) {
                        evalOrder.setOrderStatus(1);
                        evalOrderService.updateEvalOrder(evalOrder);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public AjaxResult queryOrder(EvalOrder evalOrder) {
        EvalOrder evalOrders = evalOrderMapper.selectEvalOrder(evalOrder);
        if (CommonUtils.BeNotNull(evalOrders)) {
            Integer orderStatus = evalOrders.getOrderStatus();
            if (orderStatus == 1) {
                return AjaxResult.success(evalOrders);
            }
        } else {
            return AjaxResult.error("暂无订单！");
        }
        return AjaxResult.error("订单错误，请联系负责人！");
    }


    /**
     * 通过xml 发给微信消息
     */
    public static String setXml(String return_code, String return_msg) {
        return "<xml> <return_code><![CDATA[" + return_code + "]]></return_code> <return_msg><![CDATA[" + return_msg + "]]></return_msg> </xml>";
    }
}
