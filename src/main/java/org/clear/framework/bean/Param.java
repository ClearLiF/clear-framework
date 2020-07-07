package org.clear.framework.bean;

import lombok.Data;
import org.clear.framework.util.CastUtil;
import org.clear.framework.util.CollectionUtil;

import java.util.Map;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : Param
 * @packageName : org.clear.framework.bean
 * @description : 用于反射时调用作为参数使用
 * @date : 2020-07-06 14:05
 **/
@Data
public class Param {
    private Map<String,Object> paramMap;
    /**
     * 校验参数为空
     * @return
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(this.paramMap);
    }
    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Param() {
    }

    public Long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }
}
