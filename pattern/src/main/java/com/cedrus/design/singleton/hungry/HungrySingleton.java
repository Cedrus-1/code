package com.cedrus.design.singleton.hungry;

/**
 * 缺点：浪费内存空间
 * @author Cedrus
 * @date 2019/3/10
 */
public class HungrySingleton {
	private HungrySingleton(){}

	private static final HungrySingleton HUNGRY = new HungrySingleton();

	public static HungrySingleton getInstance(){
		return HUNGRY;
	}
}
