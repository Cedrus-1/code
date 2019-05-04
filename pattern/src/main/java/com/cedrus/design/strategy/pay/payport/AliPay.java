package com.cedrus.design.strategy.pay.payport;

/**
 * @author Cedrus
 * @date 2019/3/17
 */
public class AliPay extends Payment {

    @Override
    public String getName() {
        return "支付宝";
    }
    @Override
    protected double queryBalance(String uid) {
        return 900;
    }

}
