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
     * 表格的列属性名是否匹配
     *
     * @return
     */
    default boolean isValidColumn(){
        return true;
    }
}
