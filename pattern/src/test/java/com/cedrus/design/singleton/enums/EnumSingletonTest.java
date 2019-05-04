package com.cedrus.design.singleton.enums;

import com.cedrus.design.singleton.register.EnumSingleton;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class EnumSingletonTest {

	@Test
	public void test1(){
		EnumSingleton s1 = null;
		EnumSingleton s2 = EnumSingleton.getInstance();
		s2.setData(new Object());

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("Seriable.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(s2);
			oos.flush();
			oos.close();

			FileInputStream fis = new FileInputStream("Seriable.obj");
			ObjectInputStream ois = new ObjectInputStream(fis);
			s1 = (EnumSingleton) ois.readObject();
			ois.close();

			System.out.println(s1==s2);

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void test2(){

		try {
			Class clazz = EnumSingleton.class;
			Constructor c = clazz.getDeclaredConstructor(String.class,int.class);
			c.setAccessible(true);
			EnumSingleton obj = (EnumSingleton)c.newInstance("tom",666);
			System.out.println(obj);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
