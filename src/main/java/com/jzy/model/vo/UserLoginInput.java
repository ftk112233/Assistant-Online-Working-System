package com.jzy.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserLoginInput
 * @description 登录请求的入参封装--用户名&密码&图形验证码&是否记住密码
 * @date 2019/11/14 22:54
 **/
@Data
public class UserLoginInput implements Serializable {
    private static final long serialVersionUID = -8417834376160462420L;

    private String userName;

    private String userPassword;

    private String imgCode;

    //remember功能交给shrio
    private String rememberMe;
}
