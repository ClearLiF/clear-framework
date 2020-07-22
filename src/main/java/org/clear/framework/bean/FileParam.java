package org.clear.framework.bean;

import lombok.Data;

import java.io.InputStream;
/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : FileParam
 * @packageName : org.clear.framework.bean
 * @description : 封装上传文件参数
 * @date : 2020-07-22 10:17
 **/
@Data
public class FileParam {
    /*
     *表单字段名
     */
    private String fieldName;
    /*
     *文件名
     */
    private String fileName;
    /*
     *文件大小
     */
    private long fileSize;
    /*
     *文件类型
     */
    private String contentType;
    /*
     *输入流
     */
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public FileParam() {
    }
}
