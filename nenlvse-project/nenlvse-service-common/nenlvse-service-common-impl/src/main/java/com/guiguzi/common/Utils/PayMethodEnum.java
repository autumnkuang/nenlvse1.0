package com.guiguzi.common.Utils;


import com.guiguzi.payment.domain.enums.Provider;

/**
 * Created by kqy on 2015/10/11.
 */
public enum PayMethodEnum {
    ALI_PAY(1,Provider.ALIPAY),
    WEIXIN_PAY(2,Provider.WEIXINPAY)
    ;

    private Integer method;
    private Provider provider;

    PayMethodEnum(Integer method, Provider provider) {
        this.method = method;
        this.provider = provider;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * 获取支付提供者
     * @param method
     * @return
     */
    public static Provider getPayProvider(int method){
        for(PayMethodEnum payMethod : PayMethodEnum.values()){
            if(payMethod.getMethod() == method){
                return payMethod.getProvider();
            }
        }

        return null;
    }
}
