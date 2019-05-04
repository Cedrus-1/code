package com.cedrus.design.singleton.seriable;

import java.io.Serializable;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class SeriableSingleton implements Serializable {
	private SeriableSingleton(){}

	private static final SeriableSingleton INSTANCE = new SeriableSingleton();

	public static SeriableSingleton getInstance(){
		return INSTANCE;
	}

	private Object readResolve(){
		return INSTANCE;
	}
}
