package com.cedrus.design.adapter.loginadapter.v2.adapters;

import com.cedrus.design.adapter.loginadapter.ResultMsg;


public interface RegistAdapter {
    boolean support(Object adapter);
    ResultMsg login(String id, Object adapter);
}
