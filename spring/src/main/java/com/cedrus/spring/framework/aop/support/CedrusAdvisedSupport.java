package com.cedrus.spring.framework.aop.support;


import com.cedrus.spring.framework.aop.aspect.CedrusAfterReturningAdviceInterceptor;
import com.cedrus.spring.framework.aop.aspect.CedrusAfterThrowingAdviceInterceptor;
import com.cedrus.spring.framework.aop.aspect.CedrusMethodBeforeAdviceInterceptor;
import com.cedrus.spring.framework.aop.config.CedrusAopConfig;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
@Data
public class CedrusAdvisedSupport {

    private Object target;

    private Class<?> targetClass;

    private CedrusAopConfig config;

    private Pattern pointCutClassPattern;

    private transient Map<Method,List<Object>> methodCache;

    public CedrusAdvisedSupport(CedrusAopConfig config){
        this.config = config;
    }


    public List<Object> getInterceptorAndDynamicInterceptionAdvice(Method method,Class<?> targetClass)throws Exception{

        List<Object> cached = methodCache.get(method);
        // 此处有问题
        if(cached == null){
           Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
           cached = methodCache.get(m);
           this.methodCache.put(m,cached);
        }

        return cached;
    }

    public void setTargetClass(Class<?> targetClass){
        this.targetClass = targetClass;
        try {
            parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parse() throws Exception{
        methodCache = new HashMap<>();

        String pointCut = config.getPointCut()
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");

        // pointCut= public .* com.cedrus.spring.demo.mvc.service..*Service..*(.*)

        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1));


        Pattern pattern = Pattern.compile(pointCut);


        Class aspectClass = Class.forName(this.config.getAspectClass());
        Object aspectInstance = aspectClass.newInstance();
        Map<String,Method> aspectMethods = new HashMap<>();
        for (Method method : aspectClass.getMethods()) {
            aspectMethods.put(method.getName(),method);
        }

        for (Method method : this.targetClass.getMethods()) {
            String methodStr = method.toString();
            if(methodStr.contains("throws")){
                methodStr = methodStr.substring(0,methodStr.lastIndexOf("throws")).trim();
            }
           Matcher matcher =  pattern.matcher(methodStr);
            if(matcher.matches()){
                List<Object> advices = new LinkedList<>();
                //把每一个方法包装成 MethodInterceptor
                // before
                if(!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))){
                    advices.add(new CedrusMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()),aspectInstance));
                }
                // after
                if(!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))){
                    advices.add(new CedrusAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),aspectInstance));
                }
                // afterThrowing
                if(!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))){
                    CedrusAfterThrowingAdviceInterceptor afterThrowingAdviceInterceptor =
                            new CedrusAfterThrowingAdviceInterceptor(aspectMethods.get(config.getAspectAfterThrow()),aspectInstance);
                    afterThrowingAdviceInterceptor.setThrowingName(config.getAspectAfterThrowingName());
                    advices.add(afterThrowingAdviceInterceptor);
                }
                methodCache.put(method,advices);
            }
        }
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
