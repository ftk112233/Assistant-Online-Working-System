package com.jzy.manager.exception;

import lombok.Getter;

/**
 * @ClassName ExcelTooManyRowsException
 * @Author JinZhiyun
 * @Description 表格行数过多异常
 * @Date 2020/1/12 13:08
 * @Version 1.0
 **/
public class ExcelTooManyRowsException extends Exception {
    private static final long serialVersionUID = 7254716126055023396L;

    /**
     * 实际的行数
     */
    @Getter
    private int actualRowCount;

    /**
     * 行数最大限制
     */
    @Getter
    private int rowCountThreshold;

    public ExcelTooManyRowsException() {
    }

    public ExcelTooManyRowsException(String message) {
        super(message);
    }

    /**
     * 构造表格行数过多异常，入参具体的行数上限maxRowCount和实际行数
     *
     * @param message     异常描述
     * @param maxRowCount 最大行数限制
     * @param actualRowCount 最大行数限制
     */
    public ExcelTooManyRowsException(String message, int maxRowCount, int actualRowCount) {
        super(message);
        this.rowCountThreshold = maxRowCount;
        this.actualRowCount=actualRowCount;
    }


    /**
     * 构造表格行数过多异常，入参具体的行数上限maxRowCount和实际行数
     *
     * @param maxRowCount 最大行数限制
     */
    public ExcelTooManyRowsException(int maxRowCount, int actualRowCount) {
        super("输入表格的行数过多。最大行数限制：" + maxRowCount+ "，实际行数："+actualRowCount);
        this.rowCountThreshold = maxRowCount;
        this.actualRowCount=actualRowCount;
    }
}
