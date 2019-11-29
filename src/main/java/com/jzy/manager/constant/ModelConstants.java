package com.jzy.manager.constant;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ModelConstants
 * @description springmvc的model的key
 * @date 2019/11/19 16:50
 **/
public class ModelConstants {
    private ModelConstants(){}

    /**
     * 值为所有用户角色所对应的model键
     */
    public static final String ROLES_MODEL_KEY="roles";

    /**
     * 值为所有校区名称所对应的model键
     */
    public static final String CAMPUS_NAMES_MODEL_KEY="campusNames";

    /**
     * 值为所有季度所对应的model键
     */
    public static final String SEASONS_MODEL_KEY="seasons";

    /**
     * 值为所有班级编码所对应的model键
     */
    public static final String CLASS_IDS_MODEL_KEY="classIds";

    /**
     * 值为所有年级所对应的model键
     */
    public static final String GRADES_MODEL_KEY="grades";

    /**
     * 值为所有学科所对应的model键
     */
    public static final String SUBJECTS_MODEL_KEY="subjects";

    /**
     * 值为所有班型所对应的model键
     */
    public static final String TYPES_MODEL_KEY="types";


    /**
     * 值为当前年份所对应的model键
     */
    public static final String CURRENT_YEAR_MODEL_KEY="currentYear";

    /**
     * 值为当前季度所对应的model键
     */
    public static final String CURRENT_SEASON_MODEL_KEY="currentSeason";

    /**
     * 通过"查看该班学生"功能跳转学生插叙页面是，传递的班级编码的model键
     */
    public static final String CLASS_ID_MODEL_KEY="classId";

    /**
     * 通过"开班做表"功能跳转做表页面是，传递的班级校区的model键
     */
    public static final String CLASS_CAMPUS_MODEL_KEY="classCampus";

    /**
     * 跳转编辑用户iframe页面时，存放的model键
     */
    public static final String USER_EDIT_MODEL_KEY="userEdit";

    /**
     * 跳转编辑角色权限iframe页面时，存放的model键
     */
    public static final String PERMISSION_EDIT_MODEL_KEY="permissionEdit";

    /**
     * 跳转编辑助教iframe页面时，存放的model键
     */
    public static final String ASSISTANT_EDIT_MODEL_KEY="assistantEdit";

    /**
     * 跳转编辑班级iframe页面时，存放的model键
     */
    public static final String CLASS_EDIT_MODEL_KEY="classEdit";

    /**
     * 跳转编辑学员上课信息iframe页面时，存放的model键
     */
    public static final String STUDENT_AND_CLASS_EDIT_MODEL_KEY="studentAndClassEdit";

    /**
     * 跳转预览班级iframe页面时，存放的model键
     */
    public static final String CLASS_PREVIEW_MODEL_KEY="classPreview";

    /**
     * 跳转编辑学生iframe页面时，存放的model键
     */
    public static final String STUDENT_EDIT_MODEL_KEY="studentEdit";

    /**
     * 跳转编辑补课学生iframe页面时，存放的model键
     */
    public static final String MISS_LESSON_STUDENT_EDIT_MODEL_KEY="missLessonStudentEdit";

    /**
     * 跳转系统公告推送页面时，存放的model键
     */
    public static final String ANNOUNCEMENT_EDIT_MODEL_KEY="announcementEdit";

    /**
     * 跳转主页面时，存放的关于公告信息的model键
     */
    public static final String ANNOUNCEMENT_MODEL_KEY="announcement";

}
