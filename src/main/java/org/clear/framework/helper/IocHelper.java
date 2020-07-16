package org.clear.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.clear.framework.annotation.Autowired;
import org.clear.framework.util.CollectionUtil;
import org.clear.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * {@link}
 *
 *
 * <p>标签定义段落</p>
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : IocHelper
 * @packageName : org.clear.framework.helper
 * @description : 一般类
 * @date : 2020-06-29 22:46
 */
@Slf4j
public class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> classObjectEntry : beanMap.entrySet()) {
                Class<?> beanClass = classObjectEntry.getKey();
                Object beanInstance = classObjectEntry.getValue();
                //获取bean类的所有成员变量
                Field[] declaredFields = beanClass.getDeclaredFields();
                log.error("ioc类的成员变量数据的大小为"+declaredFields.length);
                if (ArrayUtils.isNotEmpty(declaredFields)) {
                    //遍历bean field
                    for (Field declaredField : declaredFields) {
                        //判断是否有Autowired注解
                        if (declaredField.isAnnotationPresent(Autowired.class)){
                            Class<?> type = declaredField.getType();
                            log.error("获取ioc的type为"+type.getName());
                            //查找对应的生成类
                            Object beanFieldInstance = beanMap.get(type);
                            if (beanFieldInstance!=null){
                                log.error("获取ioc类为"+beanFieldInstance.getClass().getName());
                                ReflectionUtil.setField(beanInstance
                                        ,declaredField
                                        ,beanFieldInstance);

                            }
                        }
                    }
                }
            }
        }
    }
}
