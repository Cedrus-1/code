package com.cedrus.spring.framework.beans.support;

import com.cedrus.spring.framework.beans.config.CedrusBeanDefinition;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Cedrus
 * @date 2019/4/10
 */
public class CedrusBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<String>();

    @Getter
    private Properties config = new Properties();

    /**
     *     固定配置文件中的key，相当于xml规范
     */
    private static final String SCAN_PACKAGE = "scanPackage";

    public CedrusBeanDefinitionReader(String... locations){
        //通过url找到对应的文件，转化为文件流然后读取
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));

        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanner(config.getProperty(SCAN_PACKAGE));

    }

    private void doScanner(String scanPackage) {
        //scanPackage = com.cedrus.spring.demo
        //转换成文件路径
        // web环境用这个
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        //非web环境 用这个
//        URL url = this.getClass().getResource("/"+scanPackage.replaceAll("\\.","/"));

        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            } else {
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                String className = (scanPackage+"."+file.getName().replace(".class",""));
                registryBeanClasses.add(className);
            }
        }
    }

    /**
     *  把配置文件中扫描到的所有配置信息转化为BeanDefinition对象，以便于后续IOC操作方便
     * @return List<CedrusBeanDefinition>
     */
    public List<CedrusBeanDefinition> loadBeanDefinitions() throws Exception{
        List<CedrusBeanDefinition> result = new ArrayList<>();
        for (String className : registryBeanClasses) {
            Class<?> beanClass = Class.forName(className);
            if(beanClass.isInterface()){ continue;}

            //beanName 有三种情况，
            // 1. 默认类名首字母小写
            // 2. 自定义名字
            // 3. 接口注入
            result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
//            result.add(doCreateBeanDefinition(beanClass.getName(),beanClass.getName()));
            Class<?>[] interfaces = beanClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                result.add(doCreateBeanDefinition(anInterface.getName(),beanClass.getName()));
            }
        }
        return result;
    }

    private CedrusBeanDefinition doCreateBeanDefinition(String className, String name) {
        CedrusBeanDefinition beanDefinition = new CedrusBeanDefinition();
        beanDefinition.setFactoryBeanName(className);
        beanDefinition.setBeanClassName(name);
        return beanDefinition;
    }

//    /**
//     * 把每一个配置信息解析成 beanDefinition
//     * @param className 类名
//     * @return beanDefinition
//     */
//    private CedrusBeanDefinition doCreateBeanDefinition(String className){
//        Class<?> beanClass = null;
//        try {
//            beanClass = Class.forName(className);
//            // bean 可能是一个接口,如果是接口，用他的实现类作为beanClassName
//            if(!beanClass.isInterface()){
//                CedrusBeanDefinition beanDefinition = new CedrusBeanDefinition();
//                beanDefinition.setBeanClassName(className);
//                beanDefinition.setFactoryBeanName(toLowerFirstCase(beanClass.getSimpleName()));
//                return beanDefinition;
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 类名首字母小写
     * @param simpleName 类名
     * @return simpleName
     */
    private String toLowerFirstCase(String simpleName){
        char ch = simpleName.charAt(0);
        ch += 32;
        return ch+ simpleName.substring(1);

    }

}
