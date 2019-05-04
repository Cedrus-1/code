package com.cedrus.design.proxy.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Cedrus
 * @date 2019/3/11
 */
public class OrderServiceDynamicProxy implements InvocationHandler{

	Object proxy ;

	public Object getInstance(Object proxy) {
		this.proxy = proxy;
		Class clazz = proxy.getClass();
		//用来生成一个新的对象（字节码重组实现）
		return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		method.invoke(this.proxy,args);
		after();
		return null;
	}

	private void before(){

	}

	private void after(){
		System.out.println("------------如果合适的话，就准备办事");
	}
}
