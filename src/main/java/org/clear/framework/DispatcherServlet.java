package org.clear.framework;

import lombok.extern.slf4j.Slf4j;
import org.clear.framework.bean.*;
import org.clear.framework.helper.BeanHelper;
import org.clear.framework.helper.ConfigHelper;
import org.clear.framework.helper.ControllerHelper;
import org.clear.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : DispatcherServlet
 * @packageName : org.clear.framework
 * @description : 一般类
 * @date : 2020-07-06 16:36
 **/
@Slf4j
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关 Helper 类
        HelperLoader.init();
        // 获取 ServletContext 对象（用来注册Servlet）
        ServletContext servletContext = config.getServletContext();
        registerServlet(servletContext);
    }
    private void registerServlet(ServletContext servletContext) {
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //例如 post , /login
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        log.error("请求的方法和地址为"+requestMethod+" : "+requestPath);

        if (handler != null) {
            log.error("请求的方法为"+handler.getActionMethod().getName());
            // 获取 Controller 类及其 Bean 实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Map<String, Object> paramMap= getRequestParam(req);

            Param param = new Param(paramMap);

            Object result;

            // 调用 Action 方法
            Method actionMethod = handler.getActionMethod();
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }

            // 处理 Action 方法返回值
            if (result instanceof View) {
                //
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path)
                                .forward(req, resp);
                    }
                }
            } else if (result instanceof Data) {
                //
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }

    }

    private static Map<String, Object> getRequestParam(HttpServletRequest req) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>(16);
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = req.getParameter(paramName);

            paramMap.put(paramName, paramValue);
        }

        String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
        if (StringUtil.isNotEmpty(body)) {
            String[] params = body.split("&");
            if (ArrayUtil.isNotEmpty(params)) {
                for (String param : params) {
                    String[] array = param.split("=");
                    if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                        String paramName = array[0];
                        String paramValue = array[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
        }
        return paramMap;
    }
}
