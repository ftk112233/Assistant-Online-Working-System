package com.jzy.manager.exception;

import lombok.Getter;

/**
 * @ClassName ExcelColumnNotFoundException
 * @Author JinZhiyun
 * @Description 表格列属性名未匹配的异常
 * @Date 2019/11/22 20:59
 * @Version 1.0
 **/
public class ExcelColumnNotFoundException extends Exception {
    private static final long serialVersionUID = -7196076591253275281L;

    /**
     * 哪一列不匹配
     */
    @Getter
    private String whatWrong;

    public ExcelColumnNotFoundException() {
    }

    public ExcelColumnNotFoundException(String message) {
        super(message);
    }

    /**
     * 构造表格列属性名未匹配的异常，并指明未读取到哪一列的预期值。
     *
     * @param message            异常描述
     * @param columnNameExpected 期待的列名，但实际未读取到
     */
    public ExcelColumnNotFoundException(String message, String columnNameExpected) {
        super("未找到名称为\"" + columnNameExpected + "\"的列。");
        this.whatWrong = columnNameExpected;
    }
}
