package com.cedrus.design.factory.method;

import com.cedrus.design.factory.base.IPhone;

/**
 *
 * @author Cedrus
 * @date 2019/3/10
 */
public interface IPhoneFactory {
	/**
	 * 创建手机
	 * @return IPhone
	 */
	IPhone create();
}
