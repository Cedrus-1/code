package com.cedrus.design.strategy.promotion;

/**
 * 返现活动
 * @author Cedrus
 * @date 2019/3/17
 */
public class CashbackStrategy implements PromotionStrategy {

    @Override
    public void doPromotion() {
        System.out.println("返现促销,返回的金额转到支付宝账号");
    }
}
