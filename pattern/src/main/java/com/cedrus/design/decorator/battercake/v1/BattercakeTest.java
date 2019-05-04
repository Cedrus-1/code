package com.cedrus.design.decorator.battercake.v1;


public class BattercakeTest {
    public static void main(String[] args) {

        Battercake battercake = new Battercake();
        System.out.println(battercake.getMsg() + ",总价格：" + battercake.getPrice());

        Battercake batterCakeWithEgg = new BattercakeWithEgg();
        System.out.println(batterCakeWithEgg.getMsg() + ",总价格：" + batterCakeWithEgg.getPrice());

        Battercake withEggAndSausage = new BattercakeWithEggAndSausage();
        System.out.println(withEggAndSausage.getMsg() + ",总价格：" + withEggAndSausage.getPrice());

    }

}
