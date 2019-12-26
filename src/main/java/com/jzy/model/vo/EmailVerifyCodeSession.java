package com.jzy.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName EmailVerifyCodeSession
 * @description 验证邮箱验证码后，存入session的对象。若服务端校验成功，flag置true，
 *      防止攻击者通过将json数据改成verifyCodeCorrect来绕过验证码
 * @date 2019/10/11 12:13
 **/
@Data
public class EmailVerifyCodeSession implements Serializable {
    private static final long serialVersionUID = 5352564703274820500L;

    private String userEmail;

    private boolean auth;

    public EmailVerifyCodeSession(String userEmail, boolean auth) {
        this.userEmail = userEmail;
        this.auth = auth;
    }
}
