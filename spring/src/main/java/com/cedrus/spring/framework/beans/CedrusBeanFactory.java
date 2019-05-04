package com.cedrus.spring.framework.beans;

/**
 * 单例工厂的顶层设计
 * @author Cedrus
 * @date 2019/4/10
 */
public interface CedrusBeanFactory {

    /**
     * 根据beanName 从ioc容器获取bean
     * @param beanName bean 名称
     * @return bean
     */
    Object getBean(String beanName) throws Exception;

    Object getBean(Class<?> beanName) throws Exception;

    String[] getBeanDefinitionNames();

    int getBeanDefinitionCount();

}
