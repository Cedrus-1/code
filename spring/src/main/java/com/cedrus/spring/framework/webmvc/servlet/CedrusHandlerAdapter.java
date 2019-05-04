package com.cedrus.spring.framework.webmvc.servlet;


import com.cedrus.spring.framework.annotation.CedrusRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cedrus
 * @date 2019/5/3
 */
public class CedrusHandlerAdapter {

    public boolean support(Object handler){
        return handler instanceof CedrusHandlerMapping;
    }

    public CedrusModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        CedrusHandlerMapping handlerMapping = (CedrusHandlerMapping) handler;

        // 把方法的形参列表和request中的参数
        Map<String,Integer> paramIndexMapping = new HashMap<>();

        // 提取方法中加了注解的参数
        // 把方法上的注解拿到，是一个二维数组
        // 因为一个参数可以有多个注解，一个方法有多个参数
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {
                if (a instanceof CedrusRequestParam) {
                    String paramName = ((CedrusRequestParam) a).value();
                    if (!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        //提取方法中的request和response参数
        // 获得方法的形参列表
        Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> type = paramTypes[i];
            if (type == HttpServletRequest.class
                    || type == HttpServletResponse.class) {
                paramIndexMapping.put(type.getName(), i);
            }
        }
        //实参列表
        Object[] paramValues = new Object[paramTypes.length];
        //实际参数
        Map<String,String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue())
                    .replaceAll("\\[|\\]","")
                    .replaceAll("\\s","");
            if(!paramIndexMapping.containsKey(param.getKey())){continue;}
            int index = paramIndexMapping.get(param.getKey());
            paramValues[index] = caseStringValue(value,paramTypes[index]);
        }

        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }
        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }


        Object value = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);
        if(value==null || value instanceof Void){
            return null;
        }

        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == CedrusModelAndView.class;
        if(isModelAndView){
            return (CedrusModelAndView) value;
        }

        return null;
    }

    private Object caseStringValue(String value, Class<?> type) {
        if(Integer.class == type){
            return Integer.valueOf(value);
        }else if(Double.class == type){
            return Double.valueOf(value);
        }else if(String.class == type){
            return value;
        }else{
            return value;
        }

    }

}
