package org.clear.framework.bean;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : JsonUtil
 * @packageName : org.clear.framework.bean
 * @description : 一般类
 * @date : 2020-07-06 16:41
 **/
@Slf4j
public final class JsonUtil {

    /**
     * 将 POJO 转为 JSON
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = JSON.toJSONString(obj);
        } catch (Exception e) {
            log.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 JSON 转为 POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = JSON.parseObject(json, type);
        } catch (Exception e) {
            log.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}

