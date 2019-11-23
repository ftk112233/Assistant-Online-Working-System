package com.jzy.manager.exception;

/**
 * @ClassName ExcelColumnNotFoundException
 * @Author JinZhiyun
 * @Description 表格列属性名未匹配的异常
 * @Date 2019/11/22 20:59
 * @Version 1.0
 **/
public class ExcelColumnNotFoundException extends RuntimeException{
    public ExcelColumnNotFoundException() {
    }

    public ExcelColumnNotFoundException(String message) {
        super(message);
    }
}
