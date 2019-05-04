## 思路整理

SpringMVC 入口
---
web.xml -> DispatcherServlet
---

~~~ java
init();
service();
destroy();
~~~

### 实现思路

#### 1. 初始化阶段
1. 配置web.xml :DispatcherServlet
2. 设定init-param : contextConfigLocation=classpath:application.xml
3. 设定url-pattern: /*
4. 配置Annotation : @Controller  @Service @Autowired....

#### 初始化阶段
1. 调用init() 方法 : 加载配置文件
2. IOC容器初始化：Map<String,Object>
3. 扫描相关的类：scan-package="com.cedrus"
4. 创建实例化并保存到容器：通过反射将实例化放入IOC容器（IOC）
5. 进行DI注入：扫描IOC容器，将没有赋值的属性自动赋值（D I）
6. 初始化HandlerMapping：将一个URL和一个Method进行一对一的关联映射Map<String,Object>（MVC）

#### 运行阶段
1. 调用doPost()/doGet(): web容器调用doPost/doGet方法，获得request、response对象
2. 匹配HandlerMapping：从request对象获取用户输入的url，找到其对应的Method
3. 反射调用Method.invoke(): 利用反射调用方法并返回结果
4. response.getWriter().write() :将结果返回输出到浏览器


