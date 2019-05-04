package com.cedrus.design.observer.guava;

import com.google.common.eventbus.EventBus;

public class GuavaEventTest {

    public static void main(String[] args) {
        GuavaQuestion question = new GuavaQuestion();
        question.setUserName("小明");
        question.setPlace("Guper社区");
        question.setContent("观察者设计模式适用于哪些场景？");

        //消息总线
        EventBus eventBus = new EventBus();
        GuavaTeacher teacher = new GuavaTeacher("Tom");
        eventBus.register(teacher);
        eventBus.post(question);




        //从Struts到SpringMVC的升级
        //因为Struts面向的类，而SpringMVC面向的是方法

        //前面两者面向的是类，Guava面向是方法

        //能够轻松落地观察模式的一种解决方案
        //MQ
    }
}
