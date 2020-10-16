package com.aswqazx.server.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayConfig {

    /**
     * 网关地址 正式环境
     */
    //public static final String URL = "https://openapi.alipay.com/gateway.do";
    /**
     * 网关地址 沙箱 测试环境
     */
    public static final String URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     * APPID
     */
    public static final String ALIPAY_APPID = "";

    /**
     * 私钥
     */
    public static String APP_PRIVATE_KEY = "";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "";

    /**
     * 签名算法类型(根据生成私钥的算法,RSA2或RSA)
     */
    public static final String SIGNTYPE = "RSA2";

    /**
     * 请求数据格式
     */
    public static final String FORMAT = "json";
    /**
     * 编码集
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static final String notify_url = "http://127.0.0.1:9527/#/sys/pay/index";
    /**
     * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
     */
    public static final String return_url = "http://127.0.0.1:9527/#/sys/pay/index";

    /**
     * 统一收单交易创建接口
     */
    private static AlipayClient alipayClient = null;

    /**获得初始化的AlipayClient
     * @return 支付宝客户端
     */
    public static AlipayClient getAlipayClient() {
        if (alipayClient == null) {
            synchronized (AlipayConfig.class) {
                if (null == alipayClient) {
                    alipayClient = new DefaultAlipayClient(URL, ALIPAY_APPID, APP_PRIVATE_KEY, FORMAT, CHARSET,ALIPAY_PUBLIC_KEY,SIGNTYPE);
                }
            }
        }
        return alipayClient;
    }
}
