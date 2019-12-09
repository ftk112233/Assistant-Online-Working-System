package com.jzy.manager.util;

import com.jzy.model.dto.MissLessonStudentDetailedDto;
import com.jzy.model.entity.MissLessonStudent;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @ClassName MissLessonStudentUtils
 * @Author JinZhiyun
 * @Description 补课学生的工具类 {@link com.jzy.model.entity.MissLessonStudent}
 * 增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @Date 2019/11/28 13:16
 * @Version 1.0
 **/
public class MissLessonStudentUtils {
    private MissLessonStudentUtils() {
    }

    public static boolean isValidStudentId(String studentId) {
        return StringUtils.isEmpty(studentId) || StudentUtils.isValidStudentId(studentId);
    }

    public static boolean isValidStudentName(String studentName) {
        return StudentUtils.isValidStudentName(studentName);
    }

    public static boolean isValidStudentPhone(String studentPhone) {
        return StudentUtils.isValidStudentPhone(studentPhone);
    }

    public static boolean isValidOriginalClassId(Long originalClassId) {
        return originalClassId != null;
    }

    public static boolean isValidCurrentClassId(Long currentClassId) {
        return currentClassId != null;
    }

    public static boolean isValidDate(Date date) {
        return true;
    }

    public static boolean isValidRemark(String remark) {
        return StringUtils.isEmpty(remark) || remark.length() <= 500;
    }

    /**
     * 输入的missLessonStudent是否合法
     *
     * @param missLessonStudent 输入的missLessonStudent对象
     * @return
     */
    public static boolean isValidMissLessonStudentInfo(MissLessonStudent missLessonStudent) {
        return missLessonStudent != null && isValidStudentId(missLessonStudent.getStudentId()) && isValidStudentName(missLessonStudent.getStudentName())
                && isValidStudentPhone(missLessonStudent.getStudentPhone()) && isValidOriginalClassId(missLessonStudent.getOriginalClassId()) && isValidCurrentClassId(missLessonStudent.getCurrentClassId())
                && isValidDate(missLessonStudent.getDate()) && isValidRemark(missLessonStudent.getRemark());
    }

    /**
     * 输入的missLessonStudentDetailedDto是否合法
     *
     * @param missLessonStudentDetailedDto 输入的missLessonStudentDetailedDto对象
     * @return
     */
    public static boolean isValidMissLessonStudentUpdateInfo(MissLessonStudentDetailedDto missLessonStudentDetailedDto) {
        return missLessonStudentDetailedDto != null && isValidStudentId(missLessonStudentDetailedDto.getStudentId()) && isValidStudentName(missLessonStudentDetailedDto.getStudentName())
                && isValidStudentPhone(missLessonStudentDetailedDto.getStudentPhone()) && ClassUtils.isValidClassId(missLessonStudentDetailedDto.getOriginalClassId()) && ClassUtils.isValidClassId(missLessonStudentDetailedDto.getCurrentClassId())
                && isValidDate(missLessonStudentDetailedDto.getDate()) && isValidRemark(missLessonStudentDetailedDto.getRemark());
    }
}
