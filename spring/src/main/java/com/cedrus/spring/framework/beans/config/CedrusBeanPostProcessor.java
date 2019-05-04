package com.cedrus.spring.framework.beans.config;

/**
 * @author Cedrus
 * @date 2019/5/3
 */
public class CedrusBeanPostProcessor {

    public Object  postProcessBeforeInitialization(Object bean,String beanName) throws Exception{

        return bean;
    }


    public Object  postProcessAfterInitialization(Object bean,String beanName) throws Exception{

        return bean;
    }

}
