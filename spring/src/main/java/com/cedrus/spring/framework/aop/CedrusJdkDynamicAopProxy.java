package com.cedrus.spring.framework.aop;

import com.cedrus.spring.framework.aop.intercept.CedrusReflectiveMethodInvocation;
import com.cedrus.spring.framework.aop.support.CedrusAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
public class CedrusJdkDynamicAopProxy implements  CedrusAopProxy, InvocationHandler {

    private CedrusAdvisedSupport advised;

    public CedrusJdkDynamicAopProxy(CedrusAdvisedSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        List<Object> chain =  this.advised.getInterceptorAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());

        CedrusReflectiveMethodInvocation methodInvocation = new CedrusReflectiveMethodInvocation(proxy,
                this.advised.getTarget(),method,args,this.advised.getTargetClass(),chain);

        return methodInvocation.proceed();
    }

}
