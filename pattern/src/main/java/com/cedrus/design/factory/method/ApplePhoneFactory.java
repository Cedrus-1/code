package com.cedrus.design.factory.method;

import com.cedrus.design.factory.base.ApplePhone;
import com.cedrus.design.factory.base.IPhone;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class ApplePhoneFactory implements IPhoneFactory {

	@Override
	public IPhone create(){
		return new ApplePhone();
	}
}
