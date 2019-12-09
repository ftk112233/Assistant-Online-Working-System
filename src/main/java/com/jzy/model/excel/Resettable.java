package com.jzy.model.excel;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName Resettable
 * @description 可重置特性的接口
 * @date 2019/11/1 15:05
 **/
public interface Resettable {
    /**
     * 重置成员变量
     */
    default void resetParam() {
    }
}
