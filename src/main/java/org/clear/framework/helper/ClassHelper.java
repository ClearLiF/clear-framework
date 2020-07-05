package org.clear.framework.helper;

import org.clear.framework.annotation.Controller;
import org.clear.framework.annotation.Service;
import org.clear.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Class helper.
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ClassHelper
 * @packageName : org.clear.framework.helper
 * @description : 类操作助手类
 * @date : 2020-06-29 21:31
 */
public final class ClassHelper {
    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        //获取类集合
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 无建议(默认)
     *
     * @param
     * @return java.util.Set<java.lang.Class < ?> >
     * @description 获取应用包名下的所有类
     * @author ClearLi
     * @date 2020 /6/29 21:43
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 无建议(默认)
     *
     * @param
     * @return java.util.Set<java.lang.Class < ?> >
     * @description 获取所有controller
     * @author ClearLi
     * @date 2020 /6/29 21:48
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet =
                new HashSet<>(16);
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Controller.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * 无建议(默认)
     *
     * @param
     * @return java.util.Set<java.lang.Class < ?> >
     * @description 获取所有service
     * @author ClearLi
     * @date 2020 /6/29 21:48
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet =
                new HashSet<>(16);
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Service.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * 无建议(默认)
     *
     * @param
     * @return java.util.Set<java.lang.Class < ?> >
     * @description 获取应用包名下所有的Bean类  service controller
     * @author ClearLi
     * @date 2020 /6/29 21:51
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet =
                new HashSet<>(16);
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

}
