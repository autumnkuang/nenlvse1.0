package com.guiguzi.pay.core.servcie.impl;

import com.guiguzi.pay.core.service.PayCallbackService;
import com.guiguzi.payment.domain.enums.PayOrderStatus;
import com.guiguzi.payment.domain.service.PayService;
import com.guiguzi.payment.domain.vo.NotifyResult;
import com.guiguzi.payment.domain.vo.PayConfigVo;
import com.guiguzi.payment.exception.PayException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kqy on 2015/10/12.
 */
@Service
public class PayCallbackServiceImpl implements PayCallbackService {
    private static Logger logger = Logger.getLogger(PayCallbackServiceImpl.class);

    @Autowired
    private PayService payService;

    /**
     * 支付结果回调通知
     * @param resultInfo
     * @param payConfigVo
     * @return 回调结果
     */
    @Transactional
    @Override
    public Object payNotifyResult(String resultInfo, PayConfigVo payConfigVo) {
        try {

            NotifyResult notifyResult =  payService.handleNotifyPayOrderStatus(resultInfo,payConfigVo);
            if(notifyResult.getStatus().equals(PayOrderStatus.NOTIFY_FAILURE.getCode())){
                return notifyResult.getPayResult();
            }


            if(PayOrderStatus.PAY_SUCCESS.getCode().equals(notifyResult.getStatus())){
                //TODO 支付成功，业务代码

            }

            return notifyResult.getPayResult();
        } catch (PayException e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * 退款结果回调通知
     *
     * @param resultInfo
     * @param payConfigVo
     * @return  回调结果
     */
    @Transactional
    @Override
    public Object refundNotifyResult(String resultInfo, PayConfigVo payConfigVo) {


        try {
           NotifyResult notifyResult = payService.handleNotifyRefundOrderStatus(resultInfo, payConfigVo);
            if(notifyResult.getStatus().equals(PayOrderStatus.NOTIFY_FAILURE.getCode())){
                return notifyResult.getPayResult();
            }


            if(PayOrderStatus.REFUND_SUCCESS.getCode().equals(notifyResult.getStatus())){
                //TODO 退款成功，业务代码

            }else if(PayOrderStatus.REFUND_FAILURE.getCode().equals(notifyResult.getStatus())){
                //TODO 退款失败，业务代码

            }else{

            }

            return notifyResult.getPayResult();
        }catch (PayException e){
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
