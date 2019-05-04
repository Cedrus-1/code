package com.cedrus.spring.framework.aop;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public interface CedrusAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
