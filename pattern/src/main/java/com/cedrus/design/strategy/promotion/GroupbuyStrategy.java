package com.cedrus.design.strategy.promotion;

/**
 * 拼团优惠
 * @author Cedrus
 * @date 2019/3/17
 */
public class GroupbuyStrategy implements PromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("拼团，满20人成团，全团享受团购价");
    }
}
