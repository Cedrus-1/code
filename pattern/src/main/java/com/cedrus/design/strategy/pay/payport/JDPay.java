package com.cedrus.design.strategy.pay.payport;

/**
 * @author Cedrus
 * @date 2019/3/17
 */
public class JDPay extends Payment {
    @Override
    public String getName() {
        return "京东白条";
    }
    @Override
    protected double queryBalance(String uid) {
        return 500;
    }
}
