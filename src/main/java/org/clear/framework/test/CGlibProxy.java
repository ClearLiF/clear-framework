package org.clear.framework.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : CGlibProxy
 * @packageName : org.clear.framework.test
 * @description : 一般类
 * @date : 2020-07-20 17:01
 **/
public class CGlibProxy implements MethodInterceptor {

    private static CGlibProxy cGlibProxy = null;

    public static synchronized CGlibProxy getCGlibProxy() {
        if (cGlibProxy == null) {
            cGlibProxy = new CGlibProxy();
        }
        return cGlibProxy;
    }

    public <T> T getProxy(Class<T> tClass) {
        return (T) Enhancer.create(tClass, this);
    }

    @Override
    public Object intercept(Object o, Method method,
                            Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("前置");
        Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println("后置");
        return invoke;
    }

    public static void main(String[] args) {
        HelloImpl proxy = CGlibProxy.getCGlibProxy().getProxy(HelloImpl.class);
        proxy.say();
    }
}
