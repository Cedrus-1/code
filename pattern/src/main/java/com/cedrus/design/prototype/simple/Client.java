package com.cedrus.design.prototype.simple;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class Client {

	public Prototype startClone(ConcertPrototypeA a){
		return a.copy();
	}
}
