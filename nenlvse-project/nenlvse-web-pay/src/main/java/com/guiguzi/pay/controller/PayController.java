package com.guiguzi.pay.controller;

import com.guiguzi.pay.core.service.PayCallbackService;
import com.guiguzi.payment.domain.enums.PaySourceWay;
import com.guiguzi.payment.domain.enums.Provider;
import com.guiguzi.payment.domain.utils.HttpUtils;
import com.guiguzi.payment.domain.vo.PayConfigVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kqy on 2015/10/11.
 */
@Controller
@RequestMapping("/payment")
public class PayController {
    private static Logger logger = Logger.getLogger(PayController.class);

    @Autowired
    private PayCallbackService payCallbackService;

    /**
     * 支付宝回调结果通知
     *
     * @return
     */
    @RequestMapping("/alipayNotifyResult")
    public void alipayNotifyResult(HttpServletRequest request, HttpServletResponse response) {

        logger.info("//==========================接收到支付宝异步通知 开始===================================//");
        String resultIno = HttpUtils.readRequestParam(request);

        logger.info("//==========================接收到请求参数===================================//");
        logger.info(resultIno);
        logger.info("//===========================================================================//");

        if (StringUtils.isEmpty(resultIno)) {
            HttpUtils.writeResponse(response, com.guiguzi.payment.alipay.enums.ReturnResultEnum.failure.name(),"utf-8");
            return;
        }
        try {
            Object result = payCallbackService.payNotifyResult(resultIno,new PayConfigVo(Provider.ALIPAY, PaySourceWay.MOBILE_FAST_PAY));
            HttpUtils.writeResponse(response, result, "utf-8");
        } catch (Exception e) {
            HttpUtils.writeResponse(response, com.guiguzi.payment.alipay.enums.ReturnResultEnum.failure.name(),"utf-8");
        }

    }

    /**
     * 支付宝退款回调
     * @param request
     * @param response
     */
    @RequestMapping("/alipayRefundResult")
    public void alipayRefundResult(HttpServletRequest request, HttpServletResponse response) {

        logger.info("//==========================接收到支付宝异步通知 开始===================================//");
        String resultIno = HttpUtils.readRequestParam(request);
        //String resultIno = "{\"batch_no\":\"2015103100000013108820\",\"notify_id\":\"efa59abd0377b265ce3cf3e4f3033bbn6c\",\"notify_time\":\"2015-11-01 00:24:32\",\"notify_type\":\"batch_refund_notify\",\"result_details\":\"2015102700001000100079847105^0.01^SUCCESS\",\"sign\":\"nmXvHeNGwPWGQOw1jzWDFfi96uBIugA7MrogloDgo49NW9wcHVxpqKVwOzrcwxkCaUV/AmnvszOI12n8FbCdwR9oGEdm8A3df6WGxlKe20L6QrepwtL6BXX1wKJ2aeVTpnHWXLTcXiDeC/YR59bXnZBuaD1cXmcAC0HIAgGtBrc=\",\"sign_type\":\"RSA\",\"success_num\":\"1\"}";
        logger.info("//==========================接收到请求参数===================================//");
        logger.info(resultIno);
        logger.info("//===========================================================================//");

        if (StringUtils.isEmpty(resultIno)) {
            HttpUtils.writeResponse(response, com.guiguzi.payment.alipay.enums.ReturnResultEnum.failure.name(),"utf-8");
            return;
        }
        try {
            Object result = payCallbackService.refundNotifyResult(resultIno,new PayConfigVo(Provider.ALIPAY));
            HttpUtils.writeResponse(response, result, "utf-8");
        } catch (Exception e) {
            HttpUtils.writeResponse(response, com.guiguzi.payment.alipay.enums.ReturnResultEnum.failure.name(),"utf-8");
        }

    }


    @RequestMapping("/weixinpayNotifyResult")
    public void weixinpayNotifyResult(HttpServletRequest request, HttpServletResponse response) {

        logger.info("//==========================接收到微信异步通知 开始===================================//");
        String resultIno = HttpUtils.readRequestParamXml(request);
        logger.info("//==========================接收到请求参数===================================//");
        logger.info(resultIno);
        logger.info("//===========================================================================//");

        if (StringUtils.isEmpty(resultIno)) {
            HttpUtils.writeResponse(response, "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[接收数据为空]]></return_msg></xml>","utf-8");
            return;
        }

        try {
            Object result = payCallbackService.payNotifyResult(resultIno, new PayConfigVo(Provider.WEIXINPAY, PaySourceWay.MOBILE_FAST_PAY));
            HttpUtils.writeResponse(response, result, "utf-8");
        } catch (Exception e) {
            HttpUtils.writeResponse(response, "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![处理失败]]></return_msg></xml>","utf-8");
        }

    }

}
