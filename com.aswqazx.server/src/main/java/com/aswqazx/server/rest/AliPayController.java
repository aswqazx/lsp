package com.aswqazx.server.rest;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.aswqazx.server.config.AlipayConfig;
import com.aswqazx.server.entity.ResultInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/alipay")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AliPayController {

    //手机网站支付
    @PostMapping(value = "/wapPay")
    public ResultInfo wapPay(@RequestBody String param) {
        ResultInfo resultParam = new ResultInfo();
        try {
            String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
            log.info("outTradeNo == " + outTradeNo);
            // 获得初始化的AlipayClient
            AlipayClient alipayClient = AlipayConfig.getAlipayClient();
            // 创建API对应的request
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
            // 付款成功后,页面重定向通知
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            // 服务器通知
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setSubject("测试");
            model.setOutTradeNo(outTradeNo);
            model.setTimeoutExpress("90m");
            model.setTotalAmount("1.00");
            model.setProductCode("QUICK_WAP_PAY");
            alipayRequest.setBizModel(model);
            // 调用SDK生成表单
            String form = alipayClient.pageExecute(alipayRequest).getBody();
            log.info(form);
            resultParam.setCode(1);
            resultParam.setMessage("成功");
            resultParam.setData(form);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultParam;
    }

    //web扫码支付
    @PostMapping(value = "/webPay")
    public ResultInfo webPay(@RequestBody String param) {
        ResultInfo resultParam = new ResultInfo();
        try {
            String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
            log.info("outTradeNo == " + outTradeNo);
            // 获得初始化的AlipayClient
            AlipayClient alipayClient = AlipayConfig.getAlipayClient();
            // 创建API对应的request
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            // 付款成功后,页面重定向通知
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            // 服务器通知
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setSubject("测试");
            model.setOutTradeNo(outTradeNo);
            model.setTimeoutExpress("90m");
            model.setTotalAmount("1.00");
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            alipayRequest.setBizModel(model);
            // 调用SDK生成表单
            String form = alipayClient.pageExecute(alipayRequest).getBody();
            log.info(form);
            resultParam.setCode(1);
            resultParam.setMessage("成功");
            resultParam.setData(form);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultParam;
    }

    // APP支付
    @PostMapping(value = "/appPay")
    public ResultInfo appPay(@RequestBody String param) {
        ResultInfo resultParam = new ResultInfo();
        try {
            String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
            log.info("outTradeNo == " + outTradeNo);
            // 获得初始化的AlipayClient
            AlipayClient alipayClient = AlipayConfig.getAlipayClient();
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setSubject("test");
            model.setOutTradeNo(outTradeNo);
            model.setTimeoutExpress("30m");
            model.setTotalAmount("1.00");
            model.setProductCode("QUICK_MSECURITY_PAY");
            alipayRequest.setBizModel(model);

            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse alipayResponse = alipayClient.execute(alipayRequest);
            // 就是orderString可以直接给客户端请求，无需再做处理。
            log.info(alipayResponse.getBody());
            log.info(alipayResponse.getCode());
            resultParam.setCode(1);
            resultParam.setMessage("成功");
            resultParam.setData(alipayResponse.getBody());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultParam;
    }

    @PostMapping(value = "/refund")
    public ResultInfo refund(@RequestBody String param) {
        ResultInfo resultParam = new ResultInfo();
        try {
            // 获得初始化的AlipayClient
            AlipayClient alipayClient = AlipayConfig.getAlipayClient();
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            AlipayTradeRefundModel model=new AlipayTradeRefundModel();
            // 商户订单号
            model.setOutTradeNo("111-111-1111");
            // 退款金额
            model.setRefundAmount("1.00");
            // 退款原因
            model.setRefundReason("正常退款");
            alipayRequest.setBizModel(model);

            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeRefundResponse alipayResponse = alipayClient.execute(alipayRequest);
            // 就是orderString可以直接给客户端请求，无需再做处理。
            log.info(alipayResponse.getCode());
            log.info(alipayResponse.getSubCode());
            log.info(alipayResponse.getSubMsg());
            if ("10000".equals(alipayResponse.getCode())) {
                resultParam.setCode(1);
                resultParam.setMessage("退款成功");
            } else {
                resultParam.setCode(2);
                resultParam.setMessage("退款失败，" + alipayResponse.getSubMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultParam;
    }
}
