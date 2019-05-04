package com.cedrus.design.strategy;


import com.cedrus.design.strategy.promotion.CashbackStrategy;
import com.cedrus.design.strategy.promotion.CouponStrategy;
import com.cedrus.design.strategy.promotion.PromotionActivity;
import com.cedrus.design.strategy.promotion.PromotionStrategyFactory;
import org.junit.Test;

/**
 * 促销活动
 */
public class PromotionActivityTest {

    @Test
    public void test1() {
        PromotionActivity activity618 = new PromotionActivity(new CouponStrategy());
        PromotionActivity activity1111 = new PromotionActivity(new CashbackStrategy());
        activity618.execute();
        activity1111.execute();
    }

    @Test
    public void test2() {
        PromotionActivity promotionActivity = null;
        String promotionKey = "COUPON";
        if("COUPON".equals(promotionKey)){
            promotionActivity = new PromotionActivity(new CouponStrategy());
        }else if("CASHBACK".equals(promotionKey)){
            promotionActivity = new PromotionActivity(new CashbackStrategy());
        }//......
        promotionActivity.execute();
    }

    @Test
    public void test3() {
        String promotionKey = "GROUPBUY";
        PromotionActivity promotionActivity = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy(promotionKey));
        promotionActivity.execute();
    }

}
