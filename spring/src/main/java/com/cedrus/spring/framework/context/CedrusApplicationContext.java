package com.cedrus.spring.framework.context;

import com.cedrus.spring.framework.annotation.CedrusAutowired;
import com.cedrus.spring.framework.annotation.CedrusController;
import com.cedrus.spring.framework.annotation.CedrusService;
import com.cedrus.spring.framework.aop.CedrusAopProxy;
import com.cedrus.spring.framework.aop.CedrusCglibAopProxy;
import com.cedrus.spring.framework.aop.CedrusJdkDynamicAopProxy;
import com.cedrus.spring.framework.aop.config.CedrusAopConfig;
import com.cedrus.spring.framework.aop.support.CedrusAdvisedSupport;
import com.cedrus.spring.framework.beans.CedrusBeanFactory;
import com.cedrus.spring.framework.beans.config.CedrusBeanDefinition;
import com.cedrus.spring.framework.beans.CedrusBeanWrapper;
import com.cedrus.spring.framework.beans.config.CedrusBeanPostProcessor;
import com.cedrus.spring.framework.beans.support.CedrusBeanDefinitionReader;
import com.cedrus.spring.framework.beans.support.CedrusDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cedrus
 * @date 2019/4/10
 */
public class CedrusApplicationContext extends CedrusDefaultListableBeanFactory implements CedrusBeanFactory {

    private String[] configLocations ;
    private CedrusBeanDefinitionReader reader;

    /**
     *  单例的IOC容器缓存
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    private Map<String, CedrusBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, CedrusBeanWrapper>();

    public CedrusApplicationContext(String... configLocations){
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String beanName) throws Exception{
        CedrusBeanDefinition beanDefinition = super.beanDefinitionMap.get(beanName);
        CedrusBeanPostProcessor postProcessor = new CedrusBeanPostProcessor();

        // 1 初始化 doCreateBean()
        Object instance = instantiateBean(beanName,beanDefinition);

        // 2. 把这个对象封装到beanWrapper里面
        CedrusBeanWrapper beanWrapper = new CedrusBeanWrapper(instance);

        // 前置通知
        postProcessor.postProcessBeforeInitialization(beanWrapper.getWrappedInstance(),beanName);

        // class A{B b;}
        // class B{A a;}
        //循环依赖，一个方法搞不定

        // 3. 拿到beanWrapper过后，把他保存到IOC容器里面
        this.factoryBeanInstanceCache.put(beanName,beanWrapper);

        // 4 注入 populateBean()
        populateBean(beanName,beanDefinition,beanWrapper);

        // 后置通知
        postProcessor.postProcessAfterInitialization(beanWrapper.getWrappedInstance(),beanName);

        return beanWrapper.getWrappedInstance();

    }

    @Override
    public Object getBean(Class<?> clazz) throws Exception{
        return this.getBean(clazz.getName());
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return super.beanDefinitionMap.keySet().toArray(new String[super.beanDefinitionMap.size()]);
    }

    @Override
    public int getBeanDefinitionCount() {
        return super.beanDefinitionMap.size();
    }

    private void populateBean(String beanName, CedrusBeanDefinition beanDefinition, CedrusBeanWrapper beanWrapper) throws Exception {
        Object instance = beanWrapper.getWrappedInstance();


        Class<?> clazz = beanWrapper.getWrappedClass();
        // 判断 只有加了注解的类 才执行注入

        if(!(clazz.isAnnotationPresent(CedrusController.class) || clazz.isAnnotationPresent(CedrusService.class))){
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAnnotationPresent(CedrusAutowired.class)){ continue;}
            CedrusAutowired autowired = field.getAnnotation(CedrusAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            // 先后顺序问题，可能导致为空
            CedrusBeanWrapper autowiredBeanWrapper = this.factoryBeanInstanceCache.get(autowiredBeanName);
            if(autowiredBeanWrapper == null){
                continue;
            }
            field.set(instance,autowiredBeanWrapper.getWrappedInstance());
        }
    }

    private Object instantiateBean(String beanName, CedrusBeanDefinition beanDefinition)throws Exception {
        // 1. 拿到要实例化的对象类名
        String className = beanDefinition.getBeanClassName();
        // 2. 反射实例化，得到一个对象
        Object instance;
        Class<?> clazz = Class.forName(className);

        if(!beanDefinition.isSingleton()){
            instance = clazz.newInstance();
        }else {
            // 默认是单例
            if(this.singletonObjects.containsKey(beanName)){
                instance = this.singletonObjects.get(beanName);
            }else{
                instance = clazz.newInstance();

                CedrusAdvisedSupport config = initAopConfig(beanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);

                //符合Pointcut规则，就创建代理对象
                if(config.pointCutMatch()){
                    instance = createProxy(config).getProxy();
                }
                this.singletonObjects.put(beanName,instance);
                this.singletonObjects.put(beanName,instance);
            }
        }
        return instance;

    }

    private CedrusAopProxy createProxy(CedrusAdvisedSupport config) {
        Class<?> clazz = config.getTargetClass();
        if(clazz.getInterfaces().length>0){
            return new CedrusJdkDynamicAopProxy(config);
        }
        return new CedrusCglibAopProxy(config);
    }

    private CedrusAdvisedSupport initAopConfig(CedrusBeanDefinition beanDefinition) {
        CedrusAopConfig config = new CedrusAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));

        return new CedrusAdvisedSupport(config);
    }


    @Override
    public void refresh() throws Exception{
        // 1 定位，配置文件
        reader = new CedrusBeanDefinitionReader(this.configLocations);

        // 2 加载配置文件，扫描相关的类，把他们封装成BeanDefinition
        List<CedrusBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        // 3 注册，把配置文件放到容器里面
        doRegisterBeanDefinition(beanDefinitions);

        // 4 把不是延时加载的类，提前初始化
        doAutowired();

    }

    private void doRegisterBeanDefinition(List<CedrusBeanDefinition> beanDefinitions) {
        for (CedrusBeanDefinition beanDefinition : beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    /**
     * 支处理非延时加载的情况
     */
    private void doAutowired() throws Exception{
        for (Map.Entry<String, CedrusBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                String beanName = beanDefinitionEntry.getKey();
                getBean(beanName);
            }
        }

    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
