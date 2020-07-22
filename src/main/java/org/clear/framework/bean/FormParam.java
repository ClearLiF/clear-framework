package org.clear.framework.bean;

import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : FormParam
 * @packageName : org.clear.framework.bean
 * @description : 封装表单参数
 * @date : 2020-07-22 10:22
 **/
@Data
public class FormParam {

    private String fieldName;
    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

