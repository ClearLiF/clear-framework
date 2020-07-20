package org.clear.framework.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : HelloProxy
 * @packageName : org.clear.framework.test
 * @description : jdk动态代理
 * @date : 2020-07-20 16:48
 **/
public class HelloProxy implements InvocationHandler {
   private Object target;

    public HelloProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置");
        Object invoke = method.invoke(target, args);
        System.out.println("后置");
        return invoke;
    }
    public <T> T getProxy(){
        //HelloProxy helloProxy = new HelloProxy(target);
        Object o =  Proxy.newProxyInstance(target.getClass().getClassLoader()
                , target.getClass().getInterfaces(), this
        );
        return (T)o;
    }


    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        HelloProxy helloProxy = new HelloProxy(hello);
        Hello proxy = helloProxy.getProxy();
        proxy.say();

    }
}
