package org.clear.framework.helper;

import org.clear.framework.ConfigConstant;
import org.clear.framework.util.PropsUtil;

import java.util.Properties;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ConfigHelper
 * @packageName : org.clear.framework.helper
 * @description : 属性文件助手
 * @date : 2020-05-06 22:02
 **/
public final class ConfigHelper {
    //加载属性文件
    private static final Properties CONFIG_PROPS =
            PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
    /**
     * 无建议(默认)
     * @description 获取驱动
     * @author ClearLi
     * @date 2020/5/6 22:07
     * @return java.lang.String
     */
    public static String getJdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }
    /**
     * 无建议(默认)
     * @description 获取JDBC URL
     * @author ClearLi
     * @date 2020/5/6 22:09
     * @return java.lang.String
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }
    /**
     * 无建议(默认)
     * @description 获取用户名
     * @author ClearLi
     * @date 2020/5/6 22:10
     * @return java.lang.String
     */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }
    /**
     * 无建议(默认)
     * @description 获取密码
     * @author ClearLi
     * @date 2020/5/6 22:10
     * @return java.lang.String
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }
    /**
     * 无建议(默认)
     * @description 获取应用基础包名
     * @author ClearLi
     * @date 2020/5/6 22:10
     * @return java.lang.String
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }
    /**
     * 无建议(默认)
     * @description 获取jsp路径
     * @author ClearLi
     * @date 2020/5/6 22:10
     * @return java.lang.String
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }
    /**
     * 无建议(默认)
     * @description 获取静态资源路径
     * @author ClearLi
     * @date 2020/5/6 22:11
     * @return java.lang.String
     */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }



}
