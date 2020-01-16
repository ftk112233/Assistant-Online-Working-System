package com.jzy.manager.exception;

/**
 * @ClassName InvalidDataAddException
 * @Author JinZhiyun
 * @Description 不合法数据不能合并的异常 {@link com.jzy.model.dto.InvalidData}
 * @Date 2020/1/16 10:27
 * @Version 1.0
 **/
public class InvalidDataAddException extends Exception{
    private static final long serialVersionUID = -6039807736669882344L;

    public InvalidDataAddException() {
    }

    public InvalidDataAddException(String message) {
        super(message);
    }
}
