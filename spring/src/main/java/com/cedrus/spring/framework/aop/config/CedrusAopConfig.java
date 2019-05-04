package com.cedrus.spring.framework.aop.config;

import lombok.Data;

/**
 * @author Cedrus
 * @date 2019/5/4
 */

@Data
public class CedrusAopConfig {

    private String pointCut;

    private String aspectBefore;

    private String aspectAfter;

    private String aspectClass;

    private String aspectAfterThrow;

    private String aspectAfterThrowingName;



}
