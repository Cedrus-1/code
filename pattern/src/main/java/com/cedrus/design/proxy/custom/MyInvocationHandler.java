package com.cedrus.design.proxy.custom;

import java.lang.reflect.Method;

/**
 * @author Cedrus
 * @date 2019/2/24
 */
public interface MyInvocationHandler {

	Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
