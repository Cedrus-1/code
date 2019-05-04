package com.cedrus.spring.framework.aop.aspect;

import com.cedrus.spring.framework.aop.intercept.CedrusMethodInterceptor;
import com.cedrus.spring.framework.aop.intercept.CedrusReflectiveMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public class CedrusMethodBeforeAdviceInterceptor extends CedrusAbstractAspectAdvice  implements CedrusMethodInterceptor {

    private CedrusJoinPoint joinPoint;

    public CedrusMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }


    private void before(Method method,Object[] args,Object target) throws Throwable{
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }

    @Override
    public Object invoke(CedrusReflectiveMethodInvocation invocation) throws Throwable {
        this.joinPoint = invocation;
        before(invocation.getMethod(),invocation.getArgs(),invocation.getThis());
        return invocation.proceed();
    }

}
