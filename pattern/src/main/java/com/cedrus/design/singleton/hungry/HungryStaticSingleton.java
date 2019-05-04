package com.cedrus.design.singleton.hungry;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class HungryStaticSingleton {
	private HungryStaticSingleton(){}

	private static final HungryStaticSingleton HUNGRY;

	static {
		HUNGRY = new HungryStaticSingleton();
	}

	public static HungryStaticSingleton getInstance(){
		return HUNGRY;
	}
}
