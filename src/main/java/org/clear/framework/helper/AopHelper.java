package org.clear.framework.helper;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.clear.framework.annotation.Aspect;
import org.clear.framework.annotation.Service;
import org.clear.framework.proxy.AspectProxy;
import org.clear.framework.proxy.Proxy;
import org.clear.framework.proxy.ProxyManager;
import org.clear.framework.proxy.TransactionProxy;
/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : AopHelper
 * @packageName : org.clear.framework.helper
 * @description : 一般类
 * @date : 2020-07-21 10:09
 **/


/**
 * 方法拦截助手类
 *
 * @author huangyong
 * @since 1.0.0
 */
@Slf4j
public final class AopHelper {


    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            log.error("aop failure", e);
        }
    }
    /**
     * 获取代理类和目标类之间的映射关系(如一个实现了AspectProxy的类 和Service类的对应关系  用于处理事务)
     * @description
     * @author ClearLi
     * @date 2020/7/21 10:30
     * @param
     * @return java.util.Map<java.lang.Class < ?>,java.util.Set<java.lang.Class<?>>>
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
       // addTransactionProxy(proxyMap);
        return proxyMap;
    }
    /**
     * 获取代理类和目标类之间的映射关系(如一个实现了AspectProxy的类 和Service类的对应关系  用于处理事务)
     * @description
     * @author ClearLi
     * @date 2020/7/21 10:48
     * @param proxyMap 空的map数据用于存储映射关系
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        //获取AspectProxy的子类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                //所有要被代理的类
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }
    /**
     * 无建议(默认)
     * @description 获取注解中的代理类，假如注解不为aspect注解  则返回此注解下的对象集合
     * @author ClearLi
     * @date 2020/7/21 10:36
     * @param aspect
     * @return java.util.Set<java.lang.Class < ?>>
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if (!annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }
    /**
     * 获取目标类与代理类之间的映射关系
     * @description
     * @author ClearLi
     * @date 2020/7/21 11:11
     * @param proxyMap
     * @return java.util.Map<java.lang.Class < ?>,java.util.List<org.clear.framework.proxy.Proxy>>
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
