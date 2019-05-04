package com.cedrus.spring.framework.aop;

import com.cedrus.spring.framework.aop.support.CedrusAdvisedSupport;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public class CedrusCglibAopProxy implements  CedrusAopProxy{
    public CedrusCglibAopProxy(CedrusAdvisedSupport config) {
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
