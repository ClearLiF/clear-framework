package org.clear.framework.proxy;

import java.util.List;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;


/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ProxyManager
 * @packageName : org.clear.framework.proxy
 * @description : 一般类
 * @date : 2020-07-21 9:25
 **/
public class ProxyManager {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, (MethodInterceptor)
                (targetObject, targetMethod, methodParams, methodProxy)
                        ->
                        new ProxyChain(targetClass, targetObject
                                , targetMethod, methodProxy, methodParams,
                                proxyList).doProxyChain());
    }
}
