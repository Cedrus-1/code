package com.cedrus.design.strategy.promotion;

/**
 * 优惠活动
 * @author Cedrus
 * @date 2019/3/17
 */
public class PromotionActivity {
    private PromotionStrategy promotionStrategy;

    public PromotionActivity(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void execute(){
        promotionStrategy.doPromotion();
    }

}
