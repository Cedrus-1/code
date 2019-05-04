package com.cedrus.design.singleton.lazy;

/**
 * 不需要synchronized关键字
 * 性能最优
 * @author Cedrus
 * @date 2019/3/10
 */
public class LazyInnerClassSingleton {


	/**
	 * 	防止反射入侵单例
	 */
	private LazyInnerClassSingleton(){
		if(LazyHolder.LAZY!=null){
			throw new RuntimeException("不允许构建多个实例");
		}
	}

	/**
	 * 懒汉式单例
	 * LazyHolder里面的逻辑需要外部方法调用的时候才执行
	 * 巧妙的利用了内部类的特性 通过JVM底层执行逻辑，完美的避开了线程安全问题
	 * @return LAZY
	 */
	public static LazyInnerClassSingleton getInstance(){
		return LazyHolder.LAZY;
	}

	private static class LazyHolder{
		private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
	}

}
