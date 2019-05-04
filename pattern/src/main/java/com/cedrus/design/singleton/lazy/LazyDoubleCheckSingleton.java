package com.cedrus.design.singleton.lazy;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class LazyDoubleCheckSingleton {
	private LazyDoubleCheckSingleton(){}

	private volatile static LazyDoubleCheckSingleton LAZY;

	/**
	 * 适中的方案
	 * 对简单懒汉模式改进
	 * @return LAZY
	 */
	public static LazyDoubleCheckSingleton getInstance(){
		if(LAZY==null){
			synchronized(LazyDoubleCheckSingleton.class){
				if(LAZY==null){
					LAZY = new LazyDoubleCheckSingleton();
					//CPU执行的时候 会转换成JVM指令执行 （第二步和第三步顺序可能颠倒）
					// 1、分配内存给这个对象
					// 2、初始化对象
					// 3、将初始化好的对象和内存地址建立关联和赋值
					// 4、用户初次访问
				}
			}
		}
		return LAZY;
	}


}
