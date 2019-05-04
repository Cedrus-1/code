package com.cedrus.spring.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author Cedrus
 * @date 2019/4/16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CedrusController {
    String value() default "";


}
