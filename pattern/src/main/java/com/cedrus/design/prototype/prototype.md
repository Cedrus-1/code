### 原型模式 Prototype Pattern

##### 使用场景
* 类初始化消耗资源较多
* new产生的一个对象需要非常繁琐的过程（数据准备，访问权限等）
* 构造函数比较复杂
* 循环体重生产大量对象时
原型模式就是如果快速构建对象的方法总结
简单工厂建getset封装到某个方法中
JDK提供实现Cloneable 接口，实现快速复用

```
    BeanUtils.copy();
    JSON.parseObject();
    //Guava
 ```
