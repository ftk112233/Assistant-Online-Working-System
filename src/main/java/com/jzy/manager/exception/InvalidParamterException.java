package com.jzy.manager.exception;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName InvalidParamterException
 * @description 不合法的入参异常
 * @date 2019/11/19 9:34
 **/
public class InvalidParamterException extends RuntimeException {
    public InvalidParamterException() {
    }

    public InvalidParamterException(String message) {
        super(message);
    }
}
