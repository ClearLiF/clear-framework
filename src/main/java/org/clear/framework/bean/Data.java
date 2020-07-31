package org.clear.framework.bean;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : Data
 * @packageName : org.clear.framework.bean
 * @description : 返回数据对象
 *
 *      此类封装一个Object类型的模型数据，框架会将该对象写入
 *      HttpServletResponse对象中，从而直接输出到浏览器中 ce
 * @date : 2020-07-06 14:17
 **/

public class Data {
    private Object model;

    public Data() {
    }

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
