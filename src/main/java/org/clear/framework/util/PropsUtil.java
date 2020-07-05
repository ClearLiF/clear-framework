package org.clear.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 无建议(默认)
 *
 * @author ClearLi
 * @description 加载配置文件
 * @date 2020 /5/6 20:53
 */
public final class PropsUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */
    /**
     * 无建议(默认)
     *
     * @param fileName 属性文件名
     * @return java.util.Properties properties
     * @description 加载属性文件
     * @author ClearLi
     * @date 2020 /6/29 17:24
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            //获取当前文件的路径
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            logger.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    /**
     * 无建议(默认)
     *
     * @param props 配置对象
     * @param key   key值
     * @return java.lang.String string
     * @description 获取字符型属性 （默认值为空字符串）
     * @author ClearLi
     * @date 2020 /6/29 17:31
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * 获取字符型属性（可指定默认值）
     *
     * @param props        the props
     * @param key          the key
     * @param defaultValue the default value
     * @return the string
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值型属性（默认值为0）
     *
     * @param props the props
     * @param key   the key
     * @return the int
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * 获取数值型属性（可指定默认值）
     *
     * @param props        the props
     * @param key          the key
     * @param defaultValue the default value
     * @return the int
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔型属性（默认值为false）
     *
     * @param props the props
     * @param key   the key
     * @return the boolean
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 获取布尔型属性（可指定默认值）
     *
     * @param props        the props
     * @param key          the key
     * @param defaultValue the default value
     * @return the boolean
     */
    public static boolean getBoolean(Properties props, String key, Boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }

}
