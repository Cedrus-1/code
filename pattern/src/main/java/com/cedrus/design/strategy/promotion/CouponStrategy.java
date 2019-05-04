package com.cedrus.design.strategy.promotion;

/**
 * 优惠券
 * @author Cedrus
 * @date 2019/3/17
 */
public class CouponStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("领取优惠券,课程的价格直接减优惠券面值抵扣");
    }
}
