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
    private Constants() {
    }

    public static final String INDEX="http://xdf.kurochan.top/";

    public static final List<String> SEX = Arrays.asList("男", "女");

    /**
     * 改用枚举对象 {@link com.jzy.model.RoleEnum}
     */
    @Deprecated
    public static final List<String> ROLES = Arrays.asList("管理员", "学管", "助教长", "助教", "教师", "游客");

    /**
     * 改用枚举对象 {@link com.jzy.model.SeasonEnum}
     */
    @Deprecated
    public static final List<String> SEASONS = Arrays.asList("暑假", "秋上", "秋下", "寒假", "春季");

    /**
     * 改用枚举对象 {@link com.jzy.model.GradeEnum}
     */
    @Deprecated
    public static final List<String> GRADES = Arrays.asList("小初衔接", "初一", "初二", "中考", "高一", "高二", "高考");

    /**
     * 改用枚举对象 {@link com.jzy.model.SubjectEnum}
     */
    @Deprecated
    public static final List<String> SUBJECTS = Arrays.asList("语文", "数学", "英语", "物理", "化学", "地理", "生物", "历史", "科学", "联报");

    /**
     * 改用枚举对象 {@link com.jzy.model.TypeEnum}
     */
    @Deprecated
    public static final List<String> TYPES = Arrays.asList("好学", "精进", "志高", "行远", "壮志", "凌云", "星耀", "专项", "虚拟");

    public static final String CSRF_NUMBER = "csrfToken";

    public static final String SUCCESS = "success";

    public static final String FAILURE = "failure";

    public static final String UNKNOWN_ERROR = "unknownError";

    public static final String UNCHANGED = "unchanged";

    public static final String EXCEL_COLUMN_NOT_FOUND="excelColumnNotFound";

    public static final String ON = "on";

    public static final String YES = "yes";

    public static final Long ZERO = 0L;

    public static final Long MINUS_ONE = -1L;

    public static final Long MINUS_TWO = -2L;

    public static final Long GUEST_ID = MINUS_ONE;

    /**
     * 固定管理员id
     */
    public static final Long ADMIN_USER_ID =ZERO;

    /**
     * 固定的公告的id=-2
     */
    public static final Long BASE_ANNOUNCEMENT=MINUS_TWO;

}
