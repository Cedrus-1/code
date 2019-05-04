package com.cedrus.design.proxy.statics;

public class Father {

    private Person person;

    public Father(Person person){
        this.person = person;
    }

    public void findLove(){
        System.out.println("根据你的要求物色");
        this.person.findLove();
        System.out.println("双方父母是否同意");
    }
}
