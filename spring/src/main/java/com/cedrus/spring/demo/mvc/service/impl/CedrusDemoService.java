package com.cedrus.spring.demo.mvc.service.impl;

import com.cedrus.spring.demo.mvc.service.ICedrusDemoService;
import com.cedrus.spring.framework.annotation.CedrusService;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
@CedrusService
public class CedrusDemoService implements ICedrusDemoService {
    @Override
    public String query(String name)throws Exception {
//        System.out.println("My name is "+ name);
//        return "My name is "+ name;
        throw new Exception("这是故意抛出的异常！！！");
    }

    @Override
    public Integer add(Integer a, Integer b) {
        System.out.println( a+"---------"+b);
        return a+b;
    }

    @Override
    public String add(String a, String b) {
        System.out.println( a+"---------"+b);
        return  a+"---------"+b;
    }
}
