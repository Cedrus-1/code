package com.cedrus.spring.framework.aop.aspect;

import com.cedrus.spring.framework.aop.intercept.CedrusMethodInterceptor;
import com.cedrus.spring.framework.aop.intercept.CedrusReflectiveMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public class CedrusAfterThrowingAdviceInterceptor extends CedrusAbstractAspectAdvice  implements CedrusMethodInterceptor {

    private CedrusJoinPoint joinPoint;

    private String throwingName;

    public CedrusAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(CedrusReflectiveMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(mi,null,e.getCause());
            throw e;
        }
    }

    public void setThrowingName(String throwingName){
        this.throwingName = throwingName;
    }


}
