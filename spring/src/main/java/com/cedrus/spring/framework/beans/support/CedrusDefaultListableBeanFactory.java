package com.cedrus.spring.framework.beans.support;

import com.cedrus.spring.framework.beans.config.CedrusBeanDefinition;
import com.cedrus.spring.framework.context.support.CedrusAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cedrus
 * @date 2019/4/10
 */
public class CedrusDefaultListableBeanFactory extends CedrusAbstractApplicationContext{


    protected Map<String, CedrusBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, CedrusBeanDefinition>();





}
