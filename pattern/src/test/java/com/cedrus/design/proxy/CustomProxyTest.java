package com.cedrus.design.proxy;


import com.cedrus.design.proxy.cglib.CglibMeipo;
import com.cedrus.design.proxy.cglib.ZhangSan;
import com.cedrus.design.proxy.custom.CustomMeipo;
import com.cedrus.design.proxy.jdk.JDK58;
import com.cedrus.design.proxy.jdk.Girl;
import com.cedrus.design.proxy.statics.Father;
import com.cedrus.design.proxy.statics.Person;
import com.cedrus.design.proxy.statics.Son;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @author Cedrus
 * @date 2019/2/24
 */
public class CustomProxyTest {

	@Test
	public void staicProxyTest() {
		//只能帮儿子找对象，不能帮其他人找对象
		Father father = new Father(new Son());

		father.findLove();
	}

	@Test
	public  void customProxyTest() {
		try {
			Person object = (Person)new CustomMeipo().getInstance(new Girl());
			System.out.println(object.getClass());
			object.findLove();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public  void JDKProxyTest() {
		try {
			Person object = (Person)new JDK58().getInstance(new Girl());
			System.out.println(object.getClass());
			object.findJob();
			//原理：
			// 1、拿到被代理对象的引用，并且获取到他的所有接口，反射获取
			// 2、JDK Proxy 类重新生成一个新的类、同时新的类要实现被代理类所实现的所有接口
			// 3、动态生成Java代码，把新加的业务逻辑方法 由一定的逻辑代码去调用（代码中体现）
			// 4、编译新生成的Java代码 .class文件
			// 5、在重新加载到JVM中运行
			// 以上这个过程就叫做字节码重组
			//JDK中有个规范，只要是$ 开头的，一般都是自动生成的
			//通过反编译工具可以查看源代码

			byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
			FileOutputStream os = new FileOutputStream("D:\\BaiduNetdiskDownload\\$Proxy0.class");
			os.write(bytes);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void CglibTest(){
		//jdk是采用读取接口的信息
		// cglib覆盖父类方法
		// 目的都是生成一个新的类，去实现增强代码逻辑的功能
		//jdk Proxy 对于用户而言，必须要有I个接口去实现，目标类相对复杂
		// cglib可以代理一个任意普通的类，没有任何要求
		// cglib生成代理逻辑更复杂，效率低，但是调用效率更高，
//		生成一个
		ZhangSan obj = (ZhangSan)new CglibMeipo().getInstance(ZhangSan.class);
		obj.findLove();
		System.out.println(obj.getClass());



	}

}
