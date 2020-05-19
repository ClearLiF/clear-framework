package org.clear.framework;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ConfigConstant
 * @packageName : org.clear.framework
 * @description : 提供相关配置项常量
 * @date : 2020-05-06 21:56
 **/
public interface ConfigConstant {
    String CONFIG_FILE = "clear.properties";

    String JDBC_DRIVER = "clear.framework.jdbc.driver";
    String JDBC_URL = "clear.framework.jdbc.url";
    String JDBC_USERNAME = "clear.framework.jdbc.username";
    String JDBC_PASSWORD= "clear.framework.jdbc.password";

    String APP_BASE_PACKAGE = "clear.framework.app.base_package";
    String APP_JSP_PATH = "clear.framework.app.jsp_path";
    String APP_ASSET_PATH = "clear.framework.app.asset_path";
}
