package com.cedrus.design.factory.simple;

import com.cedrus.design.factory.base.IPhone;
import com.cedrus.design.factory.base.ApplePhone;
import com.cedrus.design.factory.base.MiPhone;

/**
 * 简单工厂模式
 * 工厂类的职责相对过重，增加新的产品时需要修改工厂类的判断逻辑，违背开闭原则
 * 不易于扩展过于复杂的产品结构
 * @author Cedrus
 * @date 2019/3/10
 */
public class PhoneSimpleFactory {

	/**
	 * 第一种方式，传递名称，根据名称判断来创建
	 * @param name 手机名称
	 * @return IPhone
	 */
	public IPhone createByName(String name){
		if("ApplePhone".equals(name)){
			return new ApplePhone();
		}else if("Mi".equals(name)){
			return new MiPhone();
		}
		return null;
	}

	/**
	 * 第二种方式，传递类名，通过反射方式创建
	 * @param className 类名
	 * @return IPhone
	 */
	public IPhone createByClassName(String className){
		try {
			if(className!=null && !"".equals(className)){
				return (IPhone)Class.forName(className).newInstance();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 第三种方式，传递class，反射创建
	 * @param clazz class
	 * @return IPhone
	 */
	public IPhone createByClass(Class clazz){
		try {
			if(clazz!=null ){
				return (IPhone)clazz.newInstance();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
