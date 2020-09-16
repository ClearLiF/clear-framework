package org.clear.framework.test;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : GreetingProxy
 * @packageName : org.clear.framework.test
 * @description : 一般类
 * @date : 2020-08-04 11:44
 **/
public class GreetingProxy implements Hello{
    private HelloImpl helloImpl;

    public GreetingProxy(HelloImpl hello) {
        this.helloImpl = hello;
    }

    @Override
    public void say() {
        before();
        helloImpl.say();
        after();
    }
    public void before(){
        System.out.println("before");
    }
    public void after(){
        System.out.println("after");
    }
}
