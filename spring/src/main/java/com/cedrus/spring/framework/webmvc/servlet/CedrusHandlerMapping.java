package com.cedrus.spring.framework.webmvc.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author Cedrus
 * @date 2019/5/3
 */

@Data
public class CedrusHandlerMapping{

    private Pattern url;
    private Method method;
    private Object controller;

    public CedrusHandlerMapping(Pattern url,Object controller,Method method){
        this.url = url;
        this.method = method;
        this.controller = controller;
    }

}
