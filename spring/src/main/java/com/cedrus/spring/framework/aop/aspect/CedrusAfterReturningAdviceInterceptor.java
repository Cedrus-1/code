package com.cedrus.spring.framework.aop.aspect;

import com.cedrus.spring.framework.aop.intercept.CedrusMethodInterceptor;
import com.cedrus.spring.framework.aop.intercept.CedrusReflectiveMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public class CedrusAfterReturningAdviceInterceptor extends CedrusAbstractAspectAdvice implements CedrusMethodInterceptor {

    private CedrusJoinPoint joinPoint;

    public CedrusAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void  afterReturning(Object retVal,Method method,Object[] args,Object target) throws Throwable{
        super.invokeAdviceMethod(joinPoint,retVal,null);
    }

    @Override
    public Object invoke(CedrusReflectiveMethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArgs(),mi.getThis());
        return retVal;
    }
}
