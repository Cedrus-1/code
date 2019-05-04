package com.cedrus.design.adapter.loginadapter.v2.adapters;


import com.cedrus.design.adapter.loginadapter.ResultMsg;

/**
 * 在适配器里面，这个接口是可有可无，不要跟模板模式混淆
 * 模板模式一定是抽象类，而这里仅仅只是一个接口
 */
public interface LoginAdapter {
    /**
     * 是否支持
     * @param adapter 适配器
     * @return true
     */
    boolean support(Object adapter);

    /**
     * 登陆方法
     * @param id id
     * @param adapter 适配器
     * @return 登陆信息
     */
    ResultMsg login(String id, Object adapter);

}
