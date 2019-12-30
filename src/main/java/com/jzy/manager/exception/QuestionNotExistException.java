package com.jzy.manager.exception;

/**
 * @ClassName QuestionNotExistException
 * @Author JinZhiyun
 * @Description 登录问题不存在的异常
 * @Date 2019/12/30 17:00
 * @Version 1.0
 **/
public class QuestionNotExistException extends QuestionException {
    public QuestionNotExistException() {
    }

    public QuestionNotExistException(String message) {
        super(message);
    }
}
