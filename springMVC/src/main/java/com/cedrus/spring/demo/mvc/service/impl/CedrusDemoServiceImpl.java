package com.cedrus.spring.demo.mvc.service.impl;

import com.cedrus.spring.demo.mvc.service.CedrusDemoService;
import com.cedrus.spring.mvc.annotation.CedrusService;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
@CedrusService
public class CedrusDemoServiceImpl implements CedrusDemoService {
    @Override
    public String query(String name) {
        System.out.println("My name is "+ name);
        return "My name is "+ name;
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
