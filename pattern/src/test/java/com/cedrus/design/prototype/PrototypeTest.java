package com.cedrus.design.prototype;

import com.cedrus.design.prototype.deep.QiTianDaSheng;
import com.cedrus.design.prototype.simple.Client;
import com.cedrus.design.prototype.simple.ConcertPrototypeA;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class PrototypeTest {

	@Test
	public  void simplePrototypeTest(){
		ConcertPrototypeA concertPrototypeA = new ConcertPrototypeA();
		concertPrototypeA.setAge(18);
		concertPrototypeA.setName("cedrus");
		List hobbies = new ArrayList();
		concertPrototypeA.setHobbies(hobbies);

		Client client = new Client();
		ConcertPrototypeA copy = (ConcertPrototypeA)client.startClone(concertPrototypeA);

		System.out.println(concertPrototypeA.getHobbies());
		System.out.println(copy.getHobbies());
		System.out.println(concertPrototypeA.getHobbies() == copy.getHobbies());
	}

	@Test
	public void deepPrototypeTest(){
		QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
		try {
			QiTianDaSheng clone = (QiTianDaSheng)qiTianDaSheng.clone();
			System.out.println(qiTianDaSheng.jinGuBang == clone.jinGuBang);
			System.out.println(clone);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}
