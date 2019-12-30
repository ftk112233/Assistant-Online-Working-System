package com.jzy.manager.exception;

/**
 * @ClassName InvalidFileInputException
 * @Author JinZhiyun
 * @Description 不合法的文件输入
 * @Date 2019/12/30 10:02
 * @Version 1.0
 **/
public class InvalidFileInputException extends InvalidParameterException {
    public InvalidFileInputException() {
    }

    public InvalidFileInputException(String message) {
        super(message);
    }
}
