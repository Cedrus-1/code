package com.cedrus.spring.framework.aop.intercept;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public interface CedrusMethodInterceptor {

    Object invoke(CedrusReflectiveMethodInvocation invocation) throws Throwable;

}
