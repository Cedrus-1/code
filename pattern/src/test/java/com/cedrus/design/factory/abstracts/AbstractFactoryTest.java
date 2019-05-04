package com.cedrus.design.factory.abstracts;

import org.junit.Test;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class AbstractFactoryTest {

	@Test
	public void test(){
		IPhoneFactory factory = new AppleFactory();
		factory.createPad().watchVideo();
		factory.createPhone().call();


	}
}
