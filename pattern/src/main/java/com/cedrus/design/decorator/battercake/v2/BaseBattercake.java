package com.cedrus.design.decorator.battercake.v2;


public class BaseBattercake extends Battercake {
    @Override
    protected String getMsg(){
        return "煎饼";
    }
    @Override
    public int getPrice(){
        return 5;
    }
}
