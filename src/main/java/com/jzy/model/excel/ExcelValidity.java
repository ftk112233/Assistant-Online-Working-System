package com.jzy.model.excel;

import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.ExcelTooManyRowsException;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName ExcelValidity
 * @description 表格的合法性
 * @date 2019/12/19 11:56
 **/
public interface ExcelValidity {
    /**
     * 表格的列属性名是否符合要求
     *
     * @return 是否符合的布尔值
     * @throws ExcelColumnNotFoundException 规定名称的列未找到
     */
    default boolean testColumnNameValidity() throws ExcelColumnNotFoundException {
        return true;
    }

    /**
     * 测试当前索引的sheet表格行数是否合法，不超过指定数量
     *
     * @param sheetIdx sheet的索引
     * @return 行数是否合法的布尔值
     * @throws ExcelTooManyRowsException 行数超过规定值
     */
    boolean testRowCountValidityOfSheet(int sheetIdx) throws ExcelTooManyRowsException;

    /**
     * 当前表格所有sheet行数都合法，不超过指定数量
     *
     * @return 行数是否合法的布尔值
     * @throws ExcelTooManyRowsException 行数超过规定值
     */
    boolean testRowCountValidity() throws ExcelTooManyRowsException;
}
