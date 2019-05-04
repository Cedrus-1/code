# 代理模式
代理模式（Proxy Pattern）的定义也非常简单，是指为其他对象提供一种代理，以控制对这个对象的访问。
代理对象在客服端和目标对象之间起到中介作用，代理模式属于结构型设计模式。
使用代理模式主要有两个目的：一保护目标对象，二增强目标对象。

## 1. 静态代理
 对某个类的方法进行增强或者扩展，只能代理某个特定的类
## 2. 动态代理
动态代理和静态对比基本思路是一致的，只不过动态代理功能更加强大，随着业务的扩展适应性更强
### jdk动态代理
* 被代理对象必须要实现接口，
* 代理类 实现InvocationHandler 接口，重写invoke方法
* 注：这里被代理对象的接口个数不能超过65535个，官方解释
~~~
     * <li>The resulting proxy class must not exceed any limits imposed
     * on classes by the virtual machine.  For example, the VM may limit
     * the number of interfaces that a class may implement to 65535; in
     * that case, the size of the {@code interfaces} array must not
     * exceed 65535.
     * </ul>
~~~
#### 字节码重组 过程
1. 拿到被代理对象的引用，并且获取到它的所有的接口，反射获取。
2. JDKProxy类重新生成一个新的类、同时新的类要实现被代理类所有实现的所有的接口。
3. 动态生成Java代码，把新加的业务逻辑方法由一定的逻辑代码去调用（在代码中体现）。
4. 编译新生成的Java代码.class
5. 再重新加载到JVM中运行。
---
JDK中有一个规范，在ClassPath下只要是$开头的class文件一般都是自动生成的

### CGlib动态代理
* 被代理类无需实现接口
* 代理类实现MethodInterceptor 接口，重写intercept方法


# CGLib和JDK动态代理对比
1. JDK动态代理是实现了被代理对象的接口，CGLib是继承了被代理对象。
2. JDK和CGLib都是在运行期生成字节码， JDK是直接写Class字节码， CGLib使用ASM框架写Class字节码，Cglib代理实现更复杂，生成代理类比JDK效率低。
3. JDK调用代理方法，是通过反射机制调用， CGLib是通过FastClass机制直接调用方法，CGLib执行效率更高。
