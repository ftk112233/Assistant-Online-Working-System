package com.jzy.manager.exception;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ImgCodeWrongException
 * @description 图形验证码错误的异常
 * @date 2019/11/15 9:35
 **/
public class ImgCodeWrongException extends RuntimeException{
    public ImgCodeWrongException() {
    }

    public ImgCodeWrongException(String message) {
        super(message);
    }
}
