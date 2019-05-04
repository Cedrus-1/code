package com.cedrus.design.singleton.lazy;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class LazySimpleSingleton {
	private LazySimpleSingleton(){}

	private static LazySimpleSingleton LAZY;

	/**
	 * JDK1.6 过后对synchronized性能优化了不少
	 * 不可避免还是存在一些性能问题
	 * @return LAZY
	 */
	public synchronized static LazySimpleSingleton getInstance(){
		if(LAZY==null){
			LAZY = new LazySimpleSingleton();
		}
		return LAZY;
	}


}
