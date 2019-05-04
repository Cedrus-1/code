package com.cedrus.design.factory.abstracts;

import com.cedrus.design.factory.base.IPhone;
import com.cedrus.design.factory.base.MiPhone;
import com.cedrus.design.factory.base.IPad;
import com.cedrus.design.factory.base.MiPad;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class MiFactory implements IPhoneFactory {
	@Override
	public IPhone createPhone() {
		return new MiPhone();
	}

	@Override
	public IPad createPad() {
		return new MiPad();
	}
}
