package com.jzy.model.excel;

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
     */
    default boolean isValidColumn(){
        return true;
    }
}
