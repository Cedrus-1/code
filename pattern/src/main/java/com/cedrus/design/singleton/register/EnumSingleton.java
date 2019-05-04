package com.cedrus.design.singleton.register;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public enum  EnumSingleton {
	/**
	 * 实例
	 */
	INSTANCE;

	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static EnumSingleton getInstance(){
		return INSTANCE;
	}

}
