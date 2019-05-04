package com.cedrus.spring.framework.beans.config;

import lombok.Data;

/**
 * Bean 配置信息
 * @author Cedrus
 * @date 2019/4/10
 */
@Data
public class CedrusBeanDefinition {

    /**
     * bean的class名称
     */
    private String beanClassName;
    /**
     * 是否延时加载
     */
    private boolean lazyInit = false;
    /**
     * 是否单例
     */
    private boolean isSingleton = true;
    /**
     * 获取bean的名称
     */
    private String factoryBeanName;

}
