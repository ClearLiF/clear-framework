package org.clear.framework.annotation;

import java.lang.annotation.*;

/**
 * aop 在类上 或接口上使用
 * @description
 * @author ClearLi
 * @date 2020/7/20 17:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /*
     * 注解
     */
    Class<? extends Annotation> value();
}
