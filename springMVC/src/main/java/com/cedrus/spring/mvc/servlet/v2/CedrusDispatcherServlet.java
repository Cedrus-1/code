package com.cedrus.spring.mvc.servlet.v2;

import com.cedrus.spring.mvc.annotation.*;
import lombok.Data;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
public class CedrusDispatcherServlet extends HttpServlet {

    /**
     * application.properties
     */
    private Properties contextConfig = new Properties();
    /**
     * 扫描到的所有的类名
     */
    private List<String> classNames = new ArrayList<>();
    /**
     * IOC容器
     */
    private Map<String,Object> ioc = new HashMap<>();
    /**
     * 保存url和Method的对应关系
     * 根据设计原则， 单一职责，最少知道原则，帮助更好的理解
     */
    private List<CedrusHandlerMapping> handlerMapping = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6 调用阶段
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detail:"+Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        CedrusHandlerMapping handlerMapping = getHandler(req);
        if(handlerMapping==null){
            resp.getWriter().write("404 NOT FOUND!");
            return;
        }

        Class<?>[] paraTypes = handlerMapping.getParamTypes();
        Object[] paramValues = new Object[paraTypes.length];
        Map<String,String[]> params = req.getParameterMap();

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue())
                    .replaceAll("\\[|\\]","")
                    .replaceAll("\\s","");
            if(!handlerMapping.paramIndexMapping.containsKey(param.getKey())){continue;}
            int index = handlerMapping.paramIndexMapping.get(param.getKey());
            paramValues[index] = convert(paraTypes[index],value);
        }


        if(handlerMapping.paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = handlerMapping.paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
        }
        if(handlerMapping.paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int respIndex = handlerMapping.paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;
        }

        Object value = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues );
        if(value==null || value instanceof Void){
            return;
        }
        resp.getWriter().write(value.toString());
    }

    private Object convert(Class<?> type,String value){
        if(Integer.class == type){
            return Integer.valueOf(value);
        }
        return value;
    }

    private CedrusHandlerMapping getHandler(HttpServletRequest req) {
        if(handlerMapping.isEmpty()){return null;}

        //绝对路径
        String url = req.getRequestURI();
        //处理成相对路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");

        for (CedrusHandlerMapping mapping : this.handlerMapping) {
            Matcher matcher = mapping.getUrl().matcher(url);
            if(!matcher.matches()){ continue; }
            return mapping;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        // 2. 扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        // 3. 初始化扫描到的类，并将其放到IOC容器
        doInstance();
        // 4. 完成依赖注入
        doAutowired();

        // 5.初始化HandlerMapping 
        initHandlerMapping();

        System.out.println("--------------------Cedrus Spring framework is init.-------------------------");
    }

    /**
     * 初始化url和Method的一对一对应关系
     */
    private void initHandlerMapping() {
        if(ioc.isEmpty()){return;}
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();

            if(!clazz.isAnnotationPresent(CedrusController.class)){continue;}

            //保存写在类上面的url
            String baseUrl = "";
            if(clazz.isAnnotationPresent(CedrusRequestMapping.class)){
                CedrusRequestMapping requestMapping = clazz.getAnnotation(CedrusRequestMapping.class);
                baseUrl = requestMapping.value();
            }
            //默认获取所有的public方法
            for(Method method : clazz.getMethods()){
                if(!method.isAnnotationPresent(CedrusRequestMapping.class)){continue;}

                CedrusRequestMapping requestMapping = method.getAnnotation(CedrusRequestMapping.class);

                String regex = ("/"+baseUrl+"/"+requestMapping.value()).replaceAll("/+","/");
                Pattern pattern = Pattern.compile(regex);
                this.handlerMapping.add(new CedrusHandlerMapping(pattern,entry.getValue(),method));
                System.out.println("Mapped:"+pattern +","+method);
            }
        }

    }

    /**
     * 依赖注入
     */
    private void doAutowired() {
        if(ioc.isEmpty()){return;}
        try {
            for (Map.Entry<String, Object> entry : ioc.entrySet()) {
                //getDeclaredFields 获取  所有的属性 ，包括 private  protected public
                Field[] fields = entry.getValue().getClass().getDeclaredFields();
                for (Field field : fields) {
                    if(!field.isAnnotationPresent(CedrusAutowired.class)){continue;}
                    CedrusAutowired autowired = field.getAnnotation(CedrusAutowired.class);
                    //如果没有自定义beanName，默认根据类型注入
                    String beanName = autowired.value().trim();
                    if("".equals(beanName)){
                        beanName = field.getType().getName();
                    }
                    //如果是public以为的修饰符，只要加了Autowired注解，都要强制赋值
                    field.setAccessible(true);
                    field.set(entry.getValue(),ioc.get(beanName));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void doInstance() {
        if(classNames.isEmpty()){return;}
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                // 加了注解的类、才初始化
                if(clazz.isAnnotationPresent(CedrusController.class)){
                    Object instance = clazz.newInstance();
                    //Spring 默认类名首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,instance);
                }else if(clazz.isAnnotationPresent(CedrusService.class)){
                    //1. 自定义beanName
                    CedrusService service = clazz.getAnnotation(CedrusService.class);
                    String  beanName = service.value();
                    //2 为空默认类型首字母小写
                    if("".equals(service.value().trim())){
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);

                    //3 根据类型自动赋值
                    for(Class<?> i : clazz.getInterfaces()){
                        if(ioc.containsKey(i.getName())){
                            throw new Exception("The bean"+i.getName()+"is exists!!");
                        }
                        ioc.put(i.getName(),instance);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 大小写字母ASCII码相差32
     * @param simpleName 名称
     * @return 类名首字母小写
     */
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] +=32;
        return String.valueOf(chars);
    }

    /**
     * 扫描出相关的类
     * @param scanPackage 扫描的类目录
     */
    private void doScanner(String scanPackage) {
        //scanPackage = com.cedrus.spring.framework
        //转换成文件路径
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));

        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            } else {
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                String className = (scanPackage+"."+file.getName().replace(".class",""));
                classNames.add(className);
            }

        }

    }

    /**
     * 加载配置文件
     * @param contextConfigLocation 配置文件地址
     */
    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null!=fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 保存了一个URL和Method的对应关系
     */
    @Data
    public class CedrusHandlerMapping{
        private Pattern url;
        private Method method;
        private Object controller;
        private Class<?>[] paramTypes;
        /**
         * 形参列表
         * 形参的名称作为key，参数的顺序作为value
         */
        private Map<String,Integer> paramIndexMapping;
        public CedrusHandlerMapping(Pattern url,Object controller,Method method){
            this.url = url;
            this.method = method;
            this.controller = controller;
            paramTypes = method.getParameterTypes();

            this.paramIndexMapping = new HashMap<>();
            putParamIndexMapping(method);
        }

        private void putParamIndexMapping(Method method) {
            // 提取方法中加了注解的参数
            // 把方法上的注解拿到，是一个二维数组
            // 因为一个参数可以有多个注解，一个方法有多个参数
            Annotation[][] pa = method.getParameterAnnotations();
            for (int i=0; i<pa.length; i++) {
                for (Annotation a : pa[i]) {
                    if(a instanceof CedrusRequestParam){
                        String paramName = ((CedrusRequestParam) a).value();
                        if(!"".equals(paramName.trim())){
                            paramIndexMapping.put(paramName,i);
                        }
                    }
                }
            }

            //提取方法中的request和response参数
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                Class<?> type = paramTypes[i];
                if(type == HttpServletRequest.class
                || type == HttpServletResponse.class){
                    paramIndexMapping.put(type.getName(),i);
                }
            }
        }

    }
}
