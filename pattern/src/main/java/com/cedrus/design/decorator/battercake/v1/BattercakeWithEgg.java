package com.cedrus.design.decorator.battercake.v1;


public class BattercakeWithEgg extends Battercake{
    @Override
    protected String getMsg() {
        return super.getMsg() + "+1个鸡蛋";
    }

    @Override
    //加一个鸡蛋加1块钱
    public int getPrice() {
        return super.getPrice() + 1;
    }
}
