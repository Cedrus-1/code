package com.cedrus.design.factory.method;

import com.cedrus.design.factory.base.IPhone;
import com.cedrus.design.factory.base.MiPhone;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class MiPhoneFactory implements IPhoneFactory {

	@Override
	public IPhone create(){
		return new MiPhone();
	}
}
