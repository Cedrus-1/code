package com.cedrus.design.singleton;


import com.cedrus.design.singleton.lazy.ExecutorThread;
import com.cedrus.design.singleton.threadlocal.ThreadLocalSingleton;

/**
 * @author Cedrus
 * @date 2019/3/10
 */
public class ThreadLocalSingletonTest {
    public static void main(String[] args) {

        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());

        Thread t1 = new Thread(new ExecutorThread());
        Thread t2 = new Thread(new ExecutorThread());
        t1.start();
        t2.start();
        System.out.println("End");

    }
}
