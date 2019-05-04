package com.cedrus.design.strategy.promotion;

/**
 * 无优惠
 * @author Cedrus
 * @date 2019/3/17
 */
public class EmptyStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("无促销活动");
    }
}
