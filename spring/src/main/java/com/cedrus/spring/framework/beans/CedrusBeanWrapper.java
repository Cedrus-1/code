package com.cedrus.spring.framework.beans;

import lombok.Getter;

/**
 * Bean 配置信息
 * @author Cedrus
 * @date 2019/4/10
 */
public class CedrusBeanWrapper {

    @Getter
    private Object wrappedInstance;

    public CedrusBeanWrapper(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }

    /**
     * 返回代理以后的Class
     * 可能会是这个 $Proxy0
     */
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }

}
