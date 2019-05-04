package com.cedrus.spring.framework.aop.aspect;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author Cedrus
 * @date 2019/5/4
 */

@Data
public abstract class CedrusAbstractAspectAdvice implements CedrusAdvice {

    private Method aspectMethod;
    private Object aspectTarget;

    public CedrusAbstractAspectAdvice(Method aspectMethod,Object aspectTarget){
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;

    }

    protected  Object invokeAdviceMethod(CedrusJoinPoint joinPoint,Object returnValue,Throwable tr)throws Throwable{

        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length == 0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object[] args = new Object[paramTypes.length];

            for (int i = 0; i < paramTypes.length; i++) {
                if(paramTypes[i] == CedrusJoinPoint.class){
                    args[i] = joinPoint;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = tr;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }

            return this.aspectMethod.invoke(aspectTarget,args);
        }

    }
}
