package com.cedrus.design.adapter.loginadapter.v1.service;

import com.cedrus.design.adapter.loginadapter.Member;
import com.cedrus.design.adapter.loginadapter.ResultMsg;

public class SiginService {

    /**
     * 注册方法
     * @param username 用户名
     * @param password 密码
     * @return 注册信息
     */
    public ResultMsg regist(String username, String password){
        return  new ResultMsg(200,"注册成功",new Member());
    }


    /**
     * 登录的方法
     * @param username 用户名
     * @param password 密码
     * @return 登陆信息
     */
    public ResultMsg login(String username,String password){
        return null;
    }

}
