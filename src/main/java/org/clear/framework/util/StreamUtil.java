package org.clear.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : StreamUtil
 * @packageName : org.clear.framework.util
 * @description : 处理流的工具类
 * @date : 2020-07-06 16:33
 **/
@Slf4j
public final class StreamUtil {

    /**
     * 从输入流中获取字符串
     *
     * @param is
     * @return
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e){
            log.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}