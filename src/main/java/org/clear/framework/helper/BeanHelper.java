package org.clear.framework.helper;

import org.clear.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Bean helper.
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : BeanHelper
 * @packageName : org.clear.framework.helper
 * @description : bean助手类
 * @date : 2020-06-29 22:08
 */
public final class BeanHelper {
    //定义bean的映射用于存放bean类与bean实例的映射关系
    private static final Map<Class<?>,Object>BEAN_MAP
            =new HashMap<>(16);
    static {
        Set<Class<?>>beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> aClass : beanClassSet) {
            //将bean放入map
            BEAN_MAP.put(aClass, ReflectionUtil.newInstance(aClass));
        }
    }

    /**
     * Gets bean map.
     *
     * @return the bean map
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * Get bean t.
     *
     * @param <T>    the type parameter
     * @param tClass the t class
     * @return the t
     */
    public static <T> T getBean(Class<T> tClass){
        if (!BEAN_MAP.containsKey(tClass)) {
            throw new RuntimeException("can not get bean by class"+tClass);
        }
        return (T) BEAN_MAP.get(tClass);
    }
}
