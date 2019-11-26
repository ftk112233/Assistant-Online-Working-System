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
    private Constants(){}

    public static final List<String> SEX=Arrays.asList("男","女");

    public static final List<String> ROLES= Arrays.asList("管理员", "学管", "助教长", "助教", "教师", "游客");

    public static final List<String> SEASONS= Arrays.asList("暑假", "秋上", "秋下", "寒假", "春季");

    public static final List<String> GRADES= Arrays.asList("小初衔接", "初一", "初二", "中考", "高一", "高二", "高考");

    public static final List<String> SUBJECTS = Arrays.asList("语文", "数学", "英语", "物理", "化学", "地理", "生物", "历史", "科学", "联报");

    public static final List<String> TYPES= Arrays.asList("好学", "精进", "志高", "行远", "壮志", "凌云", "星耀", "专项", "虚拟");

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

    /**
     * 返回json的值，表示失败
     */
    public static final String ON="on";

}
