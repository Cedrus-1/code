package com.cedrus.design.proxy.custom;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 用来生成源代码的工具类
 * @author Cedrus
 * @date 2019/2/24
 */
public class MyProxy {

	public static final String ln = "\r\n";


	public static Object newProxyInstance(MyClassLoader classLoader,Class<?>[] interfaces,MyInvocationHandler h){

		// 1、动态生成源代码 .Java文件
		try {

			String src = generateSrc(interfaces);

			// 2、将java文件输出到磁盘
			String filePath = MyProxy.class.getResource("").getPath();
			System.out.println(filePath);
			File file = new File(filePath+"$Proxy0.java");
			FileWriter fw = null;
			fw = new FileWriter(file);
			fw.write(src);
			fw.flush();
			fw.close();

			// 3、把生成的java文件编译成class文件
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null );
			Iterable iterable = manager.getJavaFileObjects(file);

			JavaCompiler.CompilationTask task = compiler.getTask(null,manager,null,null,null,iterable);
			task.call();
			manager.close();

			// 4、把编译生成的class文件加载到jvm中
			Class proxyClass = classLoader.findClass("$Proxy0");
			Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);

			file.delete();
			// 5、返回字节码重组以后的新代理对象
			return c.newInstance(h);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String generateSrc(Class<?>[] interfaces) {
		StringBuffer sb = new StringBuffer();
		sb.append("package com.cedrus.design.proxy.custom;"+ln);
		sb.append("import com.cedrus.design.proxy.statics.Person;"+ln);
		sb.append("import java.lang.reflect.Method;"+ln);
		sb.append("public class $Proxy0 implements "+interfaces[0].getName()+"{"+ln);
			sb.append("MyInvocationHandler h;" +ln);
			sb.append("public $Proxy0(MyInvocationHandler h){"+ln);
				sb.append("this.h=h;"+ln);
			sb.append("}"+ln);

			for(Method m : interfaces[0].getMethods()){
				sb.append("@Override"+ln);
				sb.append("public " + m.getReturnType().getName() + " "+ m.getName() + "(){"+ln);
					sb.append(" try{"+ln);
					sb.append(" Method m = " + interfaces[0].getName()+".class.getMethod(\""+m.getName()+"\",new Class[]{}); "+ln);
					sb.append("this.h.invoke(this,m,null);"+ln);
					sb.append(" }catch(Throwable e){"+ln);
					sb.append("e.printStackTrace();"+ln);
					sb.append("}"+ln);
				sb.append("}");
			}
		sb.append("}"+ln);


		return sb.toString();
	}
}
