package com.jzy.manager.constant;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName RedisConstants
 * @description redis相关常量
 * @date 2019/11/15 21:55
 **/
public class RedisConstants {
    private RedisConstants(){}

    /**
     * 根键
     */
    private static final String ROOT_KEY ="aows";

    /**
     * 用户缓存根键
     */
    private static final String USER_KEY =ROOT_KEY+":user";

    public static final String USER_LOGIN_KEY = USER_KEY +":login";

    public static final String USER_LOGIN_FAIL_KEY = USER_LOGIN_KEY +":fail";

    public static final String KEY_USER_LOGIN_NAMEANDPASSWORD = USER_LOGIN_KEY +":nameAndPassword";

    /**
     * 用户的验证码根键
     */
    private static final String USER_VERIFYCODE_KEY = USER_KEY +":verifyCode";

    /**
     * 邮箱验证码键
     */
    public static final String USER_VERIFYCODE_EMAIL_KEY = USER_VERIFYCODE_KEY +":email";


    /**
     * 系统缓存根键
     */
    private static final String SYSTEM_KEY = ROOT_KEY +":system";

    /**
     * 系统公告的键
     */
    public static final String ANNOUNCEMENT_SYSTEM_KEY = SYSTEM_KEY +":announcement";
}
