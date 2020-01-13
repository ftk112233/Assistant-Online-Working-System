package com.jzy.manager.exception;

/**
 * @ClassName ExcelSheetNameInvalidException
 * @Author JinZhiyun
 * @Description 表格sheet名字不合法
 * @Date 2019/11/28 11:40
 * @Version 1.0
 **/
public class ExcelSheetNameInvalidException extends Exception {
    private static final long serialVersionUID = 5739492594008242362L;

    public ExcelSheetNameInvalidException() {
    }

    public ExcelSheetNameInvalidException(String message) {
        super(message);
    }
}
