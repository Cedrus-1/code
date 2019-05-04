package com.cedrus.spring.demo.mvc.aspect;

import com.cedrus.spring.framework.aop.aspect.CedrusJoinPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author Cedrus
 * @date 2019/5/4
 */
@Slf4j
public class LogAspect {

    /**
     * 在调用一个方法之前，执行before方法
     */
    public void before(CedrusJoinPoint joinPoint){
        joinPoint.setUserAttribute("startTime_" + joinPoint.getMethod().getName(),System.currentTimeMillis());
        //这个方法中的逻辑，是由我们自己写的
        log.info("Invoker Before Method!!!" +
                "\nTargetObject:" +  joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 在调用一个方法之后，执行after方法
     */
    public void after(CedrusJoinPoint joinPoint){
        log.info("Invoker After Method!!!" +
                "\nTargetObject:" +  joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArgs()));
        long startTime = (Long) joinPoint.getUserAttribute("startTime_" + joinPoint.getMethod().getName());
        long endTime = System.currentTimeMillis();
        log.info("use time :" + (endTime - startTime));
    }

    /**
     * 出现异常时调用
     */
    public void afterThrowing(CedrusJoinPoint joinPoint, Throwable ex){
        log.info("出现异常" +
                "\nTargetObject:" +  joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArgs()) +
                "\nThrows:" + ex.getMessage());
    }

}
