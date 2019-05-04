package com.cedrus.spring.demo.orm.annotation;

import java.lang.annotation.*;

/**
 * @author Cedrus
 * @date 2019/4/20
 */
@Target( ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CedrusTable {
    String name() default "";
}
