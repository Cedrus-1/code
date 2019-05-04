package com.cedrus.design.strategy.pay.payport;

/**
 * @author Cedrus
 * @date 2019/3/17
 */
public class WechatPay extends Payment {
    @Override
    public String getName() {
        return "微信支付";
    }
    @Override
    protected double queryBalance(String uid) {
        return 256;
    }

}
