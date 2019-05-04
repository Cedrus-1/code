package com.cedrus.spring.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CedrusRequestParam {
    String value() default "";
}
