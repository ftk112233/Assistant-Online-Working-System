package com.jzy.manager.exception;

/**
 * @ClassName QuestionException
 * @Author JinZhiyun
 * @Description 登录问题的相关异常
 * @Date 2019/12/30 17:02
 * @Version 1.0
 **/
public class QuestionException extends Exception {
    private static final long serialVersionUID = 6878060946247194586L;

    public QuestionException() {
    }

    public QuestionException(String message) {
        super(message);
    }
}
