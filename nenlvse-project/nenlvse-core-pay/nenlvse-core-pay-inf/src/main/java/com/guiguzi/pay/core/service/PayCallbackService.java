package com.guiguzi.pay.core.service;

import com.guiguzi.payment.domain.vo.PayConfigVo;

/**
 * Created by kqy on 2015/10/12.
 */
public interface PayCallbackService {

    /**
     * 支付结果回调通知
     * @param resultInfo
     * @param payConfigVo
     * @return
     */
    public Object payNotifyResult(String resultInfo,PayConfigVo payConfigVo);


    /**
     * 退款结果回调通知
     * @param resultInfo
     * @param payConfigVo
     * @return
     */
    public Object refundNotifyResult(String resultInfo,PayConfigVo payConfigVo);
}
