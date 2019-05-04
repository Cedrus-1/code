package com.cedrus.spring.demo.mvc.action;

import com.cedrus.spring.demo.mvc.service.CedrusDemoService;
import com.cedrus.spring.mvc.annotation.CedrusAutowired;
import com.cedrus.spring.mvc.annotation.CedrusController;
import com.cedrus.spring.mvc.annotation.CedrusRequestMapping;
import com.cedrus.spring.mvc.annotation.CedrusRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
@CedrusController()
@CedrusRequestMapping("/demo")
public class CedrusAction {

    @CedrusAutowired
    private CedrusDemoService cedrusDemoService;

    @CedrusRequestMapping("/query.*")
    public String query(HttpServletRequest request, HttpServletResponse response, @CedrusRequestParam("name") String name){
        return cedrusDemoService.query(name);
    }

    @CedrusRequestMapping("/add")
    public Integer add(HttpServletRequest request, HttpServletResponse response, @CedrusRequestParam("a") Integer a, @CedrusRequestParam("b") Integer b){
        return cedrusDemoService.add(a,b);
    }

    @CedrusRequestMapping("/add")
    public String add(HttpServletRequest request, HttpServletResponse response, @CedrusRequestParam("a") String a, @CedrusRequestParam("b") String b){
        return cedrusDemoService.add(a,b);
    }

}
