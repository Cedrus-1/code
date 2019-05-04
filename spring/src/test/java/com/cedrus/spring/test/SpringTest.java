package com.cedrus.spring.test;

import com.cedrus.spring.demo.mvc.action.CedrusAction;
import com.cedrus.spring.framework.context.CedrusApplicationContext;
import org.junit.Test;

/**
 * @author Cedrus
 * @date 2019/5/3
 */
public class SpringTest {

    @Test
    public void testSpringIOC(){

        CedrusApplicationContext context =  new CedrusApplicationContext("classpath:application.properties");

        try {
            Object obj = context.getBean("cedrusAction");

            Object obj2 = context.getBean(CedrusAction.class);

            System.out.println(obj);
            System.out.println(obj2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
