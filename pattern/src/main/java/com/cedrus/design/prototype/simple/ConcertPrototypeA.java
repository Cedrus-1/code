package com.cedrus.design.prototype.simple;

import java.util.List;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class ConcertPrototypeA implements Prototype {
	private int age;
	private String name;
	private List hobbies;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getHobbies() {
		return hobbies;
	}

	public void setHobbies(List hobbies) {
		this.hobbies = hobbies;
	}

	@Override
	public Prototype copy() {
		ConcertPrototypeA concertPrototypeA = new ConcertPrototypeA();
		concertPrototypeA.setAge(this.age);
		concertPrototypeA.setName(this.name);
		concertPrototypeA.setHobbies(this.hobbies);
		return concertPrototypeA;
	}
}
