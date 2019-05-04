package com.cedrus.spring.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public interface CedrusJoinPoint {

    Object getThis();

    Object[] getArgs();

    Method getMethod();

    void setUserAttribute(String key, Object value);
    Object getUserAttribute(String key);
}
