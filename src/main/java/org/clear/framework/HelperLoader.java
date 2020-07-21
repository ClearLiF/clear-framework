package org.clear.framework;

import org.clear.framework.helper.*;
import org.clear.framework.util.ClassUtil;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : HelperLoader
 * @packageName : org.clear.framework
 * @description : 加载相应的Helper类
 * @date : 2020-07-05 23:09
 **/
public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classList) {
            ClassUtil.loadClass(aClass.getName(),true);
        }
    }

}
