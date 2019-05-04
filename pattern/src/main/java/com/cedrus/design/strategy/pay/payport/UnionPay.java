package com.cedrus.design.strategy.pay.payport;


/**
 *
 * @author Cedrus
 * @date 2019/3/17
 */
public class UnionPay extends Payment {
    @Override
    public String getName() {
        return "银联支付";
    }
    @Override
    protected double queryBalance(String uid) {
        return 120;
    }

}
