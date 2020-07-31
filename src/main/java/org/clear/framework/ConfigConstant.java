package org.clear.framework;

/**
 * The interface Config constant.
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ConfigConstant
 * @packageName : org.clear.framework
 * @description : 提供相关配置项常量
 * @date : 2020-05-06 21:56
 */
public interface ConfigConstant {
    /**
     * The constant CONFIG_FILE.
     */
    String CONFIG_FILE = "clear.properties";

    /**
     * The constant JDBC_DRIVER.
     */
    String JDBC_DRIVER = "clear.framework.jdbc.driver";
    /**
     * The constant JDBC_URL.
     */
    String JDBC_URL = "clear.framework.jdbc.url";
    /**
     * The constant JDBC_USERNAME.
     */
    String JDBC_USERNAME = "clear.framework.jdbc.username";
    /**
     * The constant JDBC_PASSWORD.
     */
    String JDBC_PASSWORD= "clear.framework.jdbc.password";

    /**
     * The constant APP_BASE_PACKAGE.
     */
    String APP_BASE_PACKAGE = "clear.framework.app.base_package";
    /**
     * The constant APP_JSP_PATH.
     */
    String APP_JSP_PATH = "clear.framework.app.jsp_path";
    /**
     * The constant APP_ASSET_PATH.
     */
    String APP_ASSET_PATH = "clear.framework.app.asset_path";
    String APP_UPLOAD_LIMIT = "smart.framework.app.upload_limit" ;
}
