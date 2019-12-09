package com.jzy.manager.util;

import com.jzy.manager.constant.Constants;
import com.jzy.model.entity.Teacher;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName TeacherUtils
 * @Author JinZhiyun
 * @Description 教师的工具类 {@link com.jzy.model.entity.Teacher}
 * 对教师的增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @Date 2019/11/23 20:17
 * @Version 1.0
 **/
public class TeacherUtils {
    private TeacherUtils() {
    }

    public static boolean isValidTeacherWorkId(String teacherWorkId) {
        return teacherWorkId == null || teacherWorkId.length() <= 32;
    }

    public static boolean isValidTeacherName(String teacherName) {
        return !StringUtils.isEmpty(teacherName) && teacherName.length() <= 50;
    }

    public static boolean isValidTeacherSex(String teacherSex) {
        return StringUtils.isEmpty(teacherSex) || Constants.SEX.contains(teacherSex);
    }

    public static boolean isValidTeacherPhone(String teacherPhone) {
        return StringUtils.isEmpty(teacherPhone) || MyStringUtils.isPhone(teacherPhone);
    }

    public static boolean isValidTeacherRemark(String teacherRemark) {
        return teacherRemark == null || teacherRemark.length() <= 500;
    }

    /**
     * 输入的teacher是否合法
     *
     * @param teacher 输入的teacher对象
     * @return
     */
    public static boolean isValidTeacherInfo(Teacher teacher) {
        return teacher != null && isValidTeacherWorkId(teacher.getTeacherWorkId()) && isValidTeacherName(teacher.getTeacherName())
                && isValidTeacherSex(teacher.getTeacherSex()) && isValidTeacherPhone(teacher.getTeacherPhone())
                && isValidTeacherRemark(teacher.getTeacherRemark());
    }

    /**
     * 输入的teacher是否合法
     *
     * @param teacher 输入的teacher对象
     * @return
     */
    public static boolean isValidTeacherUpdateInfo(Teacher teacher) {
        return isValidTeacherInfo(teacher);
    }
}
