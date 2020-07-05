package org.clear.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.clear.framework.annotation.RequestMap;
import org.clear.framework.bean.Handler;
import org.clear.framework.bean.Request;
import org.clear.framework.util.ArrayUtil;
import org.clear.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ControllerHelper
 * @packageName : org.clear.framework.helper
 * @description : 控制器助手类
 * @date : 2020-06-30 8:54
 **/
public class ControllerHelper {
    //用于存储请求与处理器的映射关系(action map)
    private static final Map<Request, Handler> ACTION_MAP =
            new HashMap<>(16);

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            //获取controller类中定义的方法
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(declaredMethods)) {
                    //遍历这些controller方法
                    for (Method method : declaredMethods) {
                        if (method.isAnnotationPresent(RequestMap.class)) {
                            //从action注解中获取URL映射规则
                            RequestMap requestMap = method.getAnnotation(RequestMap.class);
                            //获取映射
                            String mapping = requestMap.value();
                            //验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] split = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(split) && split.length == 2) {
                                    //获取请求方法与请求路径
                                    String requestMethod = split[0];
                                    String requestPath = split[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    //初始化Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }


                    }
                }
            }
        }
    }
    /**
     * 无建议(默认)
     * @description 获取handler
     * @author ClearLi
     * @date 2020/7/5 23:07
     * @param requestMethod 请求的方法
     * @param requestPath 请求的地址
     * @return org.clear.framework.bean.Handler
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        return ACTION_MAP.get(new Request(requestMethod,requestPath));
    }
}
