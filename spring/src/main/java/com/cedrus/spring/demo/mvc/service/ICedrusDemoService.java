package com.cedrus.spring.demo.mvc.service;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
public interface ICedrusDemoService {
    String query(String name) throws Exception;

    Integer add(Integer a, Integer b);
    String add(String a, String b);
}
