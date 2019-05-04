package com.cedrus.design.observer.guava;


import com.google.common.eventbus.Subscribe;


public class GuavaTeacher {
    private String name;

    public GuavaTeacher (String name){
        this.name = name;
    }

    @Subscribe
    public void subscribe(GuavaQuestion guavaQuestion){
        System.out.println(guavaQuestion.getUserName() + "在" + guavaQuestion.getPlace() + "上提交了一个问题。");
        System.out.println(name + "老师，你好！\n" +
                "您收到了一个来自“" + guavaQuestion.getPlace() + "”的提问，希望您解答，问题内容如下：\n" +
                guavaQuestion.getContent() + "\n" +
                "提问者：" + guavaQuestion.getUserName());
    }

}
