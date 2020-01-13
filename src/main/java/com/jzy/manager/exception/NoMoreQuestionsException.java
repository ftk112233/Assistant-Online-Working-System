package com.jzy.manager.exception;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName NoMoreQuestionsException
 * @description 问题登录换问题时没有问题可换了，即总共只有一个问题
 * @date 2019/12/3 16:14
 **/
public class NoMoreQuestionsException extends QuestionException {
    private static final long serialVersionUID = 2847848072452853636L;

    public NoMoreQuestionsException() {
    }

    public NoMoreQuestionsException(String message) {
        super(message);
    }
}
