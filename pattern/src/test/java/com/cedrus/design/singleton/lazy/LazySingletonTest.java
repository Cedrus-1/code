package com.cedrus.design.singleton.lazy;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class LazySingletonTest {

	@Test
	public void test1(){
		LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
		System.out.println(singleton);
	}

	@Test
	public void test2(){

		try {
			Class<?> clazz = LazyInnerClassSingleton.class;
			Constructor c = clazz.getDeclaredConstructor(null);
			c.setAccessible(true);
			Object o1 = c.newInstance();
			Object o2 = LazyInnerClassSingleton.getInstance();
			System.out.println(o1==o2);


		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new ExecutorThread());
		Thread t2 = new Thread(new ExecutorThread());
		t1.start();
		t2.start();
		System.out.println("Execute end");
	}
}
