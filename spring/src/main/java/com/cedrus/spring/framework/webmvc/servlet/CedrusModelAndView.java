package com.cedrus.spring.framework.webmvc.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Cedrus
 * @date 2019/5/3
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CedrusModelAndView {
    private String viewName;
    private Map<String,?> model;



}
