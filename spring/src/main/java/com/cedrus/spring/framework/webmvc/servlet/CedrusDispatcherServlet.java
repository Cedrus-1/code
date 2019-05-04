package com.cedrus.spring.framework.webmvc.servlet;

import com.cedrus.spring.framework.annotation.CedrusController;
import com.cedrus.spring.framework.annotation.CedrusRequestMapping;
import com.cedrus.spring.framework.context.CedrusApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Cedrus
 * @date 2019/4/13
 */

@Slf4j
public class CedrusDispatcherServlet extends HttpServlet {
    private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private CedrusApplicationContext applicationContext;

    private List<CedrusHandlerMapping> handlerMappings = new ArrayList<>();

    private Map<CedrusHandlerMapping,CedrusHandlerAdapter> handlerAdapters = new HashMap<>();

    private List<CedrusViewResolver> viewResolvers = new ArrayList<>();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{

            this.doDispatch(req,resp);
        }catch (Exception e){
//            processDispatchResult(req,resp,new CedrusModelAndView("404",null));
            resp.getWriter().write("500 Exception, Details:\r\n" + Arrays.toString(e.getStackTrace()).
                    replaceAll("\\[|\\]","").replaceAll(",\\s","\r\n"));
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        // 1. 通过request中的url 去匹配一个handlerMapping
        CedrusHandlerMapping handler =  getHandler(req);
        if(handler == null) {
            // 返回404
            processDispatchResult(req,resp,new CedrusModelAndView("404",null));
            return ;
        }

        CedrusHandlerAdapter ha = getHandlerAdapter(handler);

        CedrusModelAndView mv = ha.handle(req,resp,handler);

        // 这一步才输出
        processDispatchResult(req, resp, mv);

    }

    /**
     * 把ModelAndView 变成一个 html、outPutStream、json等
     * @param req request
     * @param resp response
     * @param mv modelAndView
     */
    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, CedrusModelAndView mv)throws Exception {
        if(null == mv){
            return;
        }
        // 如果modelAndView不为null
        if(this.viewResolvers.isEmpty()){
            return;
        }

        for (CedrusViewResolver viewResolver : this.viewResolvers) {
            CedrusView view = viewResolver.resolveViewName(mv.getViewName(),null);
            view.render(mv.getModel(),req,resp);
            return;
        }

    }

    private CedrusHandlerAdapter getHandlerAdapter(CedrusHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){
            return null;
        }
        CedrusHandlerAdapter ha = this.handlerAdapters.get(handler);
        if(ha.support(handler)){
            return ha;
        }
        return null;
    }

    private CedrusHandlerMapping getHandler(HttpServletRequest req) {
        if(this.handlerMappings.isEmpty()){
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");

        for (CedrusHandlerMapping handlerMapping : this.handlerMappings) {
            Matcher matcher = handlerMapping.getUrl().matcher(url);
            if(!matcher.matches()){
                continue;
            }
            return handlerMapping;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1 初始化ApplicationContext
        this.applicationContext = new CedrusApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        // 2 初始化Spring MVC 九大组件
        initStrategies(this.applicationContext);
    }

    private void initStrategies(CedrusApplicationContext context) {
        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        //handlerMapping 必须实现
        initHandlerMappings(context);
        //初始化参数适配器 必须实现
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器 必须实现
        initViewResolvers(context);
        //初始化flashMap
        initFlashMapManager(context);

    }

    /**
     * 初始化flashMap
     * @param context springContext
     */
    private void initFlashMapManager(CedrusApplicationContext context) {

    }

    /**
     * 初始化视图转换器
     * @param context springContext
     */
    private void initViewResolvers(CedrusApplicationContext context) {
        //拿到模板存放目录
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);

        for (String file : templateRootDir.list()) {
            this.viewResolvers.add(new CedrusViewResolver(templateRoot));
        }

    }

    /**
     * 初始化视图预处理器
     * @param context springContext
     */
    private void initRequestToViewNameTranslator(CedrusApplicationContext context) {

    }

    /**
     * 初始化异常拦截器
     * @param context springContext
     */
    private void initHandlerExceptionResolvers(CedrusApplicationContext context) {

    }

    /**
     * 初始化参数适配器
     * @param context springContext
     */
    private void initHandlerAdapters(CedrusApplicationContext context) {

        // 把一个request 请求变成一个handler，参数都是字符串的，自动匹配到handler中的形参
        // 有几个handlerMapping 就有几个HandlerAdapter

        for (CedrusHandlerMapping handlerMapping : this.handlerMappings) {

            this.handlerAdapters.put(handlerMapping,new CedrusHandlerAdapter());

        }

    }

    /**
     * handlerMapping
     * @param context springContext
     */
    private void initHandlerMappings(CedrusApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames) {
                Object controller =  context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if(!clazz.isAnnotationPresent(CedrusController.class)){
                    continue;
                }

                String baseUrl = "";
                if(clazz.isAnnotationPresent(CedrusRequestMapping.class)){
                    CedrusRequestMapping requestMapping = clazz.getAnnotation(CedrusRequestMapping.class);
                    // 比如  /demo
                    baseUrl = requestMapping.value();
                }

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if(!method.isAnnotationPresent(CedrusRequestMapping.class)){
                        continue;
                    }
                    //映射url
                    CedrusRequestMapping requestMapping = method.getAnnotation(CedrusRequestMapping.class);
                    //  如: /demo/query
                    String regex = ("/" + baseUrl + requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");

                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappings.add(new CedrusHandlerMapping(pattern,controller,method));

                    log.info("Mapped " + regex + "," + method);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化模板处理器
     * @param context springContext
     */
    private void initThemeResolver(CedrusApplicationContext context) {

    }

    /**
     * 初始化本地语言环境
     * @param context springContext
     */
    private void initLocaleResolver(CedrusApplicationContext context) {

    }

    /**
     * 多文件上传的组件
     * @param context springContext
     */
    private void initMultipartResolver(CedrusApplicationContext context) {
    }
}
