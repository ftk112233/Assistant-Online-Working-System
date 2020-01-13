package com.jzy.manager.exception;

/**
 * @ClassName ClassroomNotExistException
 * @Author JinZhiyun
 * @Description 教室不存在的异常
 * @Date 2020/1/10 11:50
 * @Version 1.0
 **/
public class ClassroomNotExistException extends Exception {
    private static final long serialVersionUID = 6036483947709866042L;

    public ClassroomNotExistException() {
    }

    public ClassroomNotExistException(String message) {
        super(message);
    }
}
