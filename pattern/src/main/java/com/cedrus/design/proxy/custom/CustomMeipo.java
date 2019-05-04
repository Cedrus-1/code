package com.cedrus.design.proxy.custom;


import com.cedrus.design.proxy.statics.Person;

import java.lang.reflect.Method;

/**
 * @author Cedrus
 * @date 2019/2/24
 */
public class CustomMeipo implements MyInvocationHandler {
	/**
	 * 	被代理的对象，将引用保存下来
	 */
	private Object target;

	public Object getInstance(Object target) throws Exception{
		this.target = target;

		Class clazz = target.getClass();

		//用来生成一个新的对象（字节码重组实现）
		return MyProxy.newProxyInstance(new MyClassLoader(),clazz.getInterfaces(),this);

	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		before();
		method.invoke(this.target,args);
		after();
		return null;
	}
	private void before(){
		System.out.println("------------我是媒婆：我要给你找对象，现在已经拿到你的需求");
		System.out.println("------------开始物色");
	}

	private void after(){
		System.out.println("------------如果合适的话，就准备办事");
	}

}
