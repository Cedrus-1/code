package com.cedrus.spring.framework.webmvc.servlet;


import java.io.File;
import java.util.Locale;

/**
 * @author Cedrus
 * @date 2019/5/3
 */
public class CedrusViewResolver {

    private static final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    private File templateRootDir;

//    private String viewName;

    public CedrusViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        templateRootDir = new File(templateRootPath);

    }

    public CedrusView resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)?viewName: (viewName+DEFAULT_TEMPLATE_SUFFIX);

        File templateFile =  new File((templateRootDir + "/" + viewName).replaceAll("/+","/"));
        return new CedrusView(templateFile);
    }
}
