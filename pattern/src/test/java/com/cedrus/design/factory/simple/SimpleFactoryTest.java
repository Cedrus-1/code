package com.cedrus.design.factory.simple;

import com.cedrus.design.factory.base.IPhone;
import com.cedrus.design.factory.base.ApplePhone;
import org.junit.Test;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class SimpleFactoryTest {

	@Test
	public void test1() {
		IPhone cellPhone = new ApplePhone();
		cellPhone.call();
	}

	@Test
	public void test2() {
		PhoneSimpleFactory factory = new PhoneSimpleFactory();
		IPhone phone1 = factory.createByName("ApplePhone");
		IPhone phone2 = factory.createByClassName("com.cedrus.design.factory.base.ApplePhone");
		IPhone phone3 = factory.createByClass(ApplePhone.class);
		phone1.call();
		phone2.call();
		phone3.call();
	}


}
