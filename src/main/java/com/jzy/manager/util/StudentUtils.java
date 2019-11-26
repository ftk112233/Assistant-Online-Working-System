package com.jzy.manager.util;

import com.jzy.manager.constant.Constants;
import com.jzy.model.entity.Student;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName StudentUtils
 * @Author JinZhiyun
 * @Description 学生的工具类 {@link com.jzy.model.entity.Student}
 *   对学生的增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @Date 2019/11/23 20:31
 * @Version 1.0
 **/
public class StudentUtils {
    private StudentUtils(){}

    public static boolean isValidStudentId(String studentId) {
        return !StringUtils.isEmpty(studentId) && studentId.length() <= 32;
    }

    public static boolean isValidStudentName(String studentName) {
        return StringUtils.isEmpty(studentName) || studentName.length() <= 50;
    }

    public static boolean isValidStudentSex(String studentSex) {
        return StringUtils.isEmpty(studentSex) || Constants.SEX.contains(studentSex);
    }

    public static boolean isValidStudentPhone(String studentPhone) {
        return StringUtils.isEmpty(studentPhone) || studentPhone.length() <= 11;
    }

    public static boolean isValidStudentPhoneBackup(String studentPhoneBackup) {
        return isValidStudentPhone(studentPhoneBackup);
    }

    public static boolean isValidStudentSchool(String studentSchool) {
        return StringUtils.isEmpty(studentSchool) || studentSchool.length() <= 50;
    }

    public static boolean isValidStudentRemark(String studentRemark) {
        return StringUtils.isEmpty(studentRemark) || studentRemark.length() <= 500;
    }

    /**
     * 输入的student是否合法
     *
     * @param student 输入的student对象
     * @return
     */
    public static boolean isValidStudentInfo(Student student){
        return student!=null && isValidStudentId(student.getStudentId()) && isValidStudentName(student.getStudentName())
                && isValidStudentSex(student.getStudentSex()) && isValidStudentPhone(student.getStudentPhone())
                && isValidStudentPhoneBackup(student.getStudentPhoneBackup()) && isValidStudentSchool(student.getStudentSchool())
                && isValidStudentRemark(student.getStudentRemark());
    }
}
