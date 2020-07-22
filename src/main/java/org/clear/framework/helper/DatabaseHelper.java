package org.clear.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import org.clear.framework.util.CollectionUtil;
import org.clear.framework.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The type Database helper.
 *
 * @author wukong
 * @since 2017 -07-01.
 */
@Slf4j
public class DatabaseHelper {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    //查询更新 助手
    private static final QueryRunner QUERY_RUNNER;
    // 为了确保一个线程中只有一个Connection，使用ThreadLocal来存放当前线程的 sql 链接对象
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    //用于dbcp链接池的数据源
    private static final BasicDataSource DATA_SOURCE;
    static {
        CONNECTION_HOLDER = new ThreadLocal<>();
        QUERY_RUNNER = new QueryRunner();

       // Properties conf = PropsUtil.loadProps("clear.properties");
        /*String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String username = conf.getProperty("jdbc.username");
        String password = conf.getProperty("jdbc.password");*/
        String driver = ConfigHelper.getJdbcDriver();
        String url = ConfigHelper.getJdbcUrl();
        String username = ConfigHelper.getJdbcUsername();
        String password = ConfigHelper.getJdbcPassword();

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                log.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                log.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 获取数据库连接
     *
     * @return the connection
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        //只有在无数据库连接的时候才新建连接 其他情况都使用原始的连接
        if (conn == null) {
            try {
                //获取连接
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                logger.error("get connection failure", e);
                throw new RuntimeException(e);
            } finally {
                //将此连接放到ThreadLocal中
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    //    public static void closeConnection() {
//        Connection conn = CONNECTION_HOLDER.get();
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                logger.error("close connection failure", e);
//                throw new RuntimeException(e);
//            } finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }

    /**
     * 查询实体类列表
     *
     * @param <T>         the type parameter
     * @param entityClass the entity class
     * @param sql         the sql
     * @param params      the params
     * @return list
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            logger.error("query entity list failure", e);
            throw new RuntimeException(e);
        }
        return entityList;
    }


    /**
     * 查询实体
     *
     * @param <T>         the type parameter
     * @param entityClass the entity class
     * @param sql         the sql
     * @param params      the params
     * @return t
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            logger.error("query entity failure", e);
            throw new RuntimeException(e);
        }
        return entity;
    }


    /**
     * 执行查询语句
     *
     * @param sql    the sql
     * @param params the params
     * @return list
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("execute query failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 执行更新语句（包括update、insert、delete）
     *
     * @param sql    the sql
     * @param params the params
     * @return rows 返回执行后影响的行数
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            //使用commons-dbutils的api
            rows = QUERY_RUNNER.update(conn, sql, params);
            log.info(sql);
        } catch (SQLException e) {
            logger.error("execute update failure", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     *
     * @param <T>         the type parameter
     * @param entityClass 实体类
     * @param fieldMap    the field map
     * @return boolean
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            logger.error("can not insert entity: fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), columns.length(), ")");
        sql += columns + " VALUES " + values;
        log.info(sql);
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }


    /**
     * 更新实体，根据ID
     *
     * @param <T>         the type parameter
     * @param entityClass the entity class
     * @param id          the id
     * @param fieldMap    the field map
     * @return boolean
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            logger.error("can not update entity: fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";
        log.info(sql);
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除实体
     *
     * @param <T>         the type parameter
     * @param entityClass the entity class
     * @param id          the id
     * @return boolean
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 返回 表名.实体名称 全大写
     * @param entityClass
     * @param <T>
     * @return
     */
    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName().toUpperCase();
    }

    /**
     * 执行SQL文件
     *
     * @param filePath the file path
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = br.readLine()) != null) {
                //除去注释项
                if (!sql.substring(0, 1).equals("#") && !sql.substring(0, 1).equals("--")) {
                    log.info(sql);
                    executeUpdate(sql);
                }
            }
        } catch (Exception e) {
            logger.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }
}
