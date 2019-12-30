package com.jzy.manager.exception;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName InvalidFileTypeException
 * @description 输入文件类型不符合规则的异常
 * @date 2019/10/30 13:01
 **/
public class InvalidFileTypeException extends Exception {
    public InvalidFileTypeException() {
    }

    public InvalidFileTypeException(String message) {
        super(message);
    }
}
