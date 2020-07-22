package org.clear.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The type Reflection util.
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ReflectionUtil
 * @packageName : org.clear.framework.util
 * @description : 反射工具类
 * @date : 2020-06-29 21:52
 */
@Slf4j
public final class ReflectionUtil {
    /**
     * 无建议(默认)
     *
     * @param cls the cls
     * @return java.lang.Object object
     * @description 创建实例
     * @author ClearLi
     * @date 2020 /6/29 21:57
     */
    public static Object newInstance(Class<?> cls){
        Object o;
        try {
            o = cls.newInstance();
            log.error("反射生成类为"+cls.getName());
        } catch (InstantiationException | IllegalAccessException e) {
           log.error("new instance failure 创建实例出错",e);
           throw new RuntimeException(e);
        }
        return o;
    }

    /**
     * 无建议(默认)
     *
     * @param obj    the obj
     * @param method the method
     * @param args   the args
     * @return java.lang.Object object
     * @description 调用方法
     * @author ClearLi
     * @date 2020 /6/29 22:01
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            log.error("调用参数数目"+args.length);
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            log.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 无建议(默认)
     *
     * @param obj   the obj
     * @param field the field
     * @param val   the val
     * @return void
     * @description 设置成员变量的值
     * @author ClearLi
     * @date 2020 /6/29 22:04
     */
    public static void setField(Object obj, Field field,Object val){
        //设置可以设置成员变量的值
        try {
            field.setAccessible(true);
            field.set(obj,val);
        } catch (IllegalAccessException e) {
            log.error("set field failure",e);
            throw new RuntimeException(e);
        }
    }
}
