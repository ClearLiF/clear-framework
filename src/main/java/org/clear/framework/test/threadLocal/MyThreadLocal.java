package org.clear.framework.test.threadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : MyThreadLocal
 * @packageName : org.clear.framework.test.threadLocal
 * @description : 一般类
 * @date : 2020-07-21 17:17
 **/
public class MyThreadLocal<T> {
    Map<Thread ,T > threadTMap = Collections.synchronizedMap(new HashMap<>());
    public T get(){
        Thread thread = Thread.currentThread();
        T value = threadTMap.get(thread);
        if (value==null&&!threadTMap.containsKey(thread)){
            value= initialValue();
        }
        return value;
    }
    protected T  initialValue(){
        return null;
    }
    public void set(T value){
        threadTMap.put(Thread.currentThread(),value);
    }
}
