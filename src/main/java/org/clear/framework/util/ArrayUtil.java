package org.clear.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Array util.
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ArrayUtil
 * @packageName : org.clear.framework.util
 * @description : 数组工具类
 * @date : 2020-06-30 8:28
 */
public final class ArrayUtil {
    /**
     * Is not empty boolean.
     * 判断数组是否为非空
     * @param array the array
     * @return the boolean
     */
    public static boolean isNotEmpty(Object[] array){
        return ArrayUtils.isNotEmpty(array);
    }

    /**
     * Is empty boolean.
     * 判断数据是否为空
     * @param array the array
     * @return the boolean
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }
}
