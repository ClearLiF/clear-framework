package org.clear.framework.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : View
 * @packageName : org.clear.framework.bean
 * @description : 返回视图对象
 * @date : 2020-07-06 14:13
 **/
@Data
public class View {
    /**
     * 视图路径
     */
    private String path;
    /**
     * 模型数据
     */
    private Map<String ,Object>model;

    public View() {
    }

    public View(String path) {
        this.path = path;
        this.model=new HashMap<>(16);
    }
    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }

}
