package com.jzy.manager.constant;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SessionConstants
 * @description 会话信息的常量类
 * @date 2019/11/14 23:20
 **/
public final class SessionConstants {
    private SessionConstants() {
    }

    /**
     * KAPTCHA图形验证码session的键
     */
    public static final String KAPTCHA_SESSION_KEY = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

    /**
     * 用户登录信息session的键
     */
    public static final String USER_INFO_SESSION_KEY = "userInfo";

    /**
     * 用户发送邮箱验证码的邮箱存放到的相应session的键
     */
    public static final String USER_EMAIL_SESSION_KEY = "userEmail";

    /**
     * 免密登录成功的session的键，值为"true"表示登录成功
     */
    public static final String LOGIN_WITHOUT_PASSWORD_SESSION_KEY = "loginWithoutPassword";

    /**
     * 通过小问题免密登录的键
     */
    public static final String LOGIN_QUESTION_SESSION_KEY = "loginQuestion";

    /**
     * 是否需要滑块验证的键
     */
    public static final String REQUIRE_SLIDER_AUTH_SESSION_KEY = "requireSlider";
}
