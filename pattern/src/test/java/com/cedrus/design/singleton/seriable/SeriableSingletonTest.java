package com.cedrus.design.singleton.seriable;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class SeriableSingletonTest {

	@Test
	public void test1(){
		SeriableSingleton s1 = null;
		SeriableSingleton s2 = SeriableSingleton.getInstance();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("Seriable.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(s2);
			oos.flush();
			oos.close();

			FileInputStream fis = new FileInputStream("Seriable.obj");
			ObjectInputStream ois = new ObjectInputStream(fis);
			s1 = (SeriableSingleton) ois.readObject();
			ois.close();

			System.out.println(s1==s2);

		}catch (Exception e){
			e.printStackTrace();
		}


	}
}
