package com.cedrus.design.factory.abstracts;

import com.cedrus.design.factory.base.IPhone;
import com.cedrus.design.factory.base.IPad;

/**
 * 要求所有的子工厂都要实现这个工厂
 * 一个品牌的抽象
 *
 * 不符合开闭原则
 * 扩展性特别强
 * @author Cedrus
 * @date 2019/3/10
 */
public interface IPhoneFactory {
	IPhone createPhone();

	IPad createPad();

}
