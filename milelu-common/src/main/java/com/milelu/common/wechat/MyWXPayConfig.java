package com.milelu.common.wechat;

import com.milelu.common.constant.WechatConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author MILELU
 * @date 2021/3/12 10:30
 */
public class MyWXPayConfig extends WXPayConfig {

    InputStream inputStream = null;

    private MyWXPayConfig() {
        if (inputStream == null) {
            //从微信商户平台下载的安全证书存放的目录
            String certPath = "apiclient_cert.p12";
            Resource resource = new ClassPathResource(certPath);
            try {
                inputStream = resource.getInputStream();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public String getAppID() {
        return WechatConstant.APP_ID;
    }

    @Override
    public String getMchID() {
        return WechatConstant.MCH_ID;
    }

    @Override
    public String getKey() {
        return WechatConstant.MCH_KEY;
    }

    @Override
    public InputStream getCertStream() {
        return inputStream;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

    /**
     * 静态内部类保证现成安全
     */
    private static class SingleWxPayConfig {
        /**
         * 静态对象初始化，由JVM保证线程安全
         */
        private static MyWXPayConfig instance = new MyWXPayConfig();
    }

    /**
     * 单例调用
     *
     * @return
     */
    public static MyWXPayConfig getInstance() {
        return SingleWxPayConfig.instance;
    }
}
