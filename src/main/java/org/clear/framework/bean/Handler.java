package org.clear.framework.bean;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : Handler
 * @packageName : org.clear.framework.bean
 * @description : 封装action信息
 * @date : 2020-06-30 8:46
 **/
@Data
public class Handler {
    //controller类
    private Class<?> controllerClass;
    //action方法
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Handler() {
    }
}
