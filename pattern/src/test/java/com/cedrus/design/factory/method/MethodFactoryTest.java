package com.cedrus.design.factory.method;

import com.cedrus.design.factory.base.ApplePhone;
import org.junit.Test;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class MethodFactoryTest {

	@Test
	public void test1(){
		IPhoneFactory factory = new ApplePhoneFactory();
		ApplePhone applePhone = (ApplePhone)factory.create();
		applePhone.call();
	}
}
