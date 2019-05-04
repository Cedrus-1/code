package com.cedrus.spring.framework.aop.intercept;

import com.cedrus.spring.framework.aop.aspect.CedrusJoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public class CedrusReflectiveMethodInvocation implements CedrusJoinPoint {

    private Object proxy;
    private Object target;
    private Method method;
    private Object[] args;
    private Class<?> targetClass;
    private List<Object> interceptorAndDynamicMethodMatchers;
    private Map<String, Object> userAttributes;

    /**
     * 定义一个索引，记录当前拦截器的位置
     */
    private int currentInterceptorIndex = -1;


    public CedrusReflectiveMethodInvocation(Object proxy,Object target, Method method, Object[] args, Class<?> targetClass,
                                            List<Object> interceptorAndDynamicMethodMatchers){
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.args = args;
        this.targetClass = targetClass;
        this.interceptorAndDynamicMethodMatchers = interceptorAndDynamicMethodMatchers;

    }



    public Object proceed() throws Throwable{
        if(this.currentInterceptorIndex == this.interceptorAndDynamicMethodMatchers.size()-1){
            return this.method.invoke(this.target,this.args);
        }

        Object interceptorInterceptionAdvice = this.interceptorAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);

        if(interceptorInterceptionAdvice instanceof CedrusMethodInterceptor){

            CedrusMethodInterceptor methodInterceptor = (CedrusMethodInterceptor)interceptorInterceptionAdvice;

            return methodInterceptor.invoke(this);
        }else{
            return proceed();
        }

    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<String,Object>();
            }
            this.userAttributes.put(key, value);
        }
        else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);

    }


}
