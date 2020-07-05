package org.clear.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 无建议(默认)
 *
 * @author ClearLi
 * @description 映射路径注解(action注解)
 * @date 2020 /6/29 21:22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMap {
    /**
     * 请求类型与路径
     *
     * @return the string
     */
    String value();
}
