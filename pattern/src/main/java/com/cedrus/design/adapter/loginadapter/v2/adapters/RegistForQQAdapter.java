package com.cedrus.design.adapter.loginadapter.v2.adapters;

import com.cedrus.design.adapter.loginadapter.ResultMsg;

public class RegistForQQAdapter implements RegistAdapter,LoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return false;
    }
    @Override
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}