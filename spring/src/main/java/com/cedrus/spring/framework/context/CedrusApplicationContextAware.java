package com.cedrus.spring.framework.context;

/**
 * 通过解耦的方式获得IOC容器的顶层设计
 * 后面将通过一个监听器扫描所有的类，实现了此接口将自动调用setApplicationContext()方法
 * 从而将IOC容器注入到目标类中
 * @author Cedrus
 * @date 2019/4/10
 */
public interface CedrusApplicationContextAware {
    void setApplicationContext(CedrusApplicationContext applicationContext);
}
