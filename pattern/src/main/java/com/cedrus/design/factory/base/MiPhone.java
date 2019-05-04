package com.cedrus.design.factory.base;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class MiPhone implements IPhone {

	@Override
	public IPhone call() {
		System.out.println("用小米手机拨打电话");
		return new MiPhone();
	}

}
