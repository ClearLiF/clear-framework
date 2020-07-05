package org.clear.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author wukong
 * @since 2017 -06-30.
 */
public final class StringUtil {
    /**
     * 判断字符串是否为空
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
