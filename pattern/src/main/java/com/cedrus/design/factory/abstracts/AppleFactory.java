package com.cedrus.design.factory.abstracts;

import com.cedrus.design.factory.base.ApplePhone;
import com.cedrus.design.factory.base.IPhone;
import com.cedrus.design.factory.base.ApplePad;
import com.cedrus.design.factory.base.IPad;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class AppleFactory implements IPhoneFactory {
	@Override
	public IPhone createPhone() {
		return new ApplePhone();
	}

	@Override
	public IPad createPad() {
		return new ApplePad();
	}
}
