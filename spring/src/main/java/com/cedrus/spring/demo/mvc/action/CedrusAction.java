package com.cedrus.spring.demo.mvc.action;

import com.cedrus.spring.demo.mvc.service.ICedrusDemoService;
import com.cedrus.spring.framework.annotation.CedrusAutowired;
import com.cedrus.spring.framework.annotation.CedrusController;
import com.cedrus.spring.framework.annotation.CedrusRequestMapping;
import com.cedrus.spring.framework.annotation.CedrusRequestParam;
import com.cedrus.spring.framework.webmvc.servlet.CedrusModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
@CedrusController()
@CedrusRequestMapping("/demo")
public class CedrusAction {

    @CedrusAutowired
    private ICedrusDemoService cedrusDemoService;

    @CedrusRequestMapping("/query")
    public CedrusModelAndView query(HttpServletRequest request, HttpServletResponse response, @CedrusRequestParam("name") String name){
        String str = null;
        try {
            str = cedrusDemoService.query(name);
            return out(response,str);
        } catch (Exception e) {
           // e.printStackTrace();
            Map<String,Object> model = new HashMap<>();
            model.put("detail",e.getMessage());
            model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
            return new CedrusModelAndView("500",model);
        }
    }

    @CedrusRequestMapping("/add")
    public CedrusModelAndView add(HttpServletRequest request, HttpServletResponse response, @CedrusRequestParam("a") Integer a, @CedrusRequestParam("b") Integer b){
        int total = cedrusDemoService.add(a,b);
        return out(response,String.valueOf(total));

    }

    @CedrusRequestMapping("/add")
    public CedrusModelAndView add(HttpServletRequest request, HttpServletResponse response, @CedrusRequestParam("a") String a, @CedrusRequestParam("b") String b){
        String total =  cedrusDemoService.add(a,b);
        return out(response,total);
    }

    private CedrusModelAndView out(HttpServletResponse response,String str){
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
