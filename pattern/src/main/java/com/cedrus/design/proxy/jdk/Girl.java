package com.cedrus.design.proxy.jdk;


import com.cedrus.design.proxy.statics.Person;

/**
 * @author Cedrus
 */
public class Girl implements Person {

    @Override
    public void findLove(){
        System.out.println("高富帅");
        System.out.println("身高180cm");
    }

    @Override
    public void zufangzi() {
		System.out.println("租房子");
	}

    @Override
    public void buy() {
		System.out.println("买东西");

    }

    @Override
    public void findJob() {
		System.out.println("找工作");
		System.out.println("月薪20k-50k");

    }
}
