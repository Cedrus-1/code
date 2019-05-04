package com.cedrus.design.singleton.lazy;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class ExecutorThread implements Runnable{
	@Override
	public void run() {
		LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
		System.out.println(Thread.currentThread().getName() + " : "+ singleton);
	}
}
