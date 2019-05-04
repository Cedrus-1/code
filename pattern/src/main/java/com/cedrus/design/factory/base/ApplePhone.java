package com.cedrus.design.factory.base;

public class ApplePhone implements IPhone {

	@Override
	public IPhone call() {
		System.out.println("用苹果电话拨打电话");
		return new ApplePhone();
	}

}
