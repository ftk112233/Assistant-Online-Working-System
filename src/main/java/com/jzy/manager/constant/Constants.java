package com.jzy.manager.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Constants
 * @description 公共的常量类
 * @date 2019/11/18 12:59
 **/
public class Constants {
    public static final List<String> SEX=Arrays.asList("男","女");

    public static final List<String> ROLES= Arrays.asList("管理员", "学管", "助教长", "助教", "教师", "游客");

    public static final String CSRF_NUMBER = "csrfToken";

    /**
     * 返回json的值，表示成功
     */
    public static final String SUCCESS="success";

    /**
     * 返回json的值，表示失败
     */
    public static final String FAILURE="failure";

    /**
     * 返回json的值，表示未知错误
     */
    public static final String UNKNOWN_ERROR="unknownError";
}
