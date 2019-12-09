package com.jzy.manager.util;

import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.entity.StudentAndClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @ClassName StudentAndClassUtils
 * @Author JinZhiyun
 * @Description 学员上课情况的工具类 {@link com.jzy.model.entity.StudentAndClass}
 * 对学员上课情况的增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @Date 2019/11/23 21:36
 * @Version 1.0
 **/
public class StudentAndClassUtils {
    private StudentAndClassUtils() {
    }

    public static boolean isValidStudentId(Long studentId) {
        return studentId != null;
    }

    public static boolean isValidClassId(Long classId) {
        return classId != null;
    }

    public static boolean isValidRegisterTime(Date registerTime) {
        return true;
    }

    public static boolean isValidRemark(String remark) {
        return StringUtils.isEmpty(remark) || remark.length() <= 500;
    }

    /**
     * 输入的studentAndClass是否合法
     *
     * @param studentAndClass 输入的studentAndClass对象
     * @return
     */
    public static boolean isValidStudentAndClassInfo(StudentAndClass studentAndClass) {
        return studentAndClass != null && isValidStudentId(studentAndClass.getStudentId()) && isValidClassId(studentAndClass.getClassId())
                && isValidRegisterTime(studentAndClass.getRegisterTime()) && isValidRemark(studentAndClass.getRemark());
    }

    /**
     * 输入的studentAndClassDetailedDto是否合法
     *
     * @param studentAndClassDetailedDto 输入的studentAndClassDetailedDto对象
     * @return
     */
    public static boolean isValidStudentAndClassDetailedDtoInfo(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        return studentAndClassDetailedDto != null && StudentUtils.isValidStudentId(studentAndClassDetailedDto.getStudentId()) && ClassUtils.isValidClassId(studentAndClassDetailedDto.getClassId())
                && isValidRegisterTime(studentAndClassDetailedDto.getRegisterTime()) && isValidRemark(studentAndClassDetailedDto.getRemark());
    }

    /**
     * 更新操作输入的studentAndClassDetailedDto是否合法
     *
     * @param studentAndClassDetailedDto 输入的studentAndClassDetailedDto对象
     * @return
     */
    public static boolean isValidStudentAndClassUpdateDtoInfo(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        return isValidStudentAndClassDetailedDtoInfo(studentAndClassDetailedDto);
    }

    /**
     * 将输入对象的年份字段由yyyy-mm-dd转成yyyy
     * 改用class类自带的成员方法
     *
     * @param input
     * @return
     */
    @Deprecated
    public static StudentAndClassDetailedDto parseClassYear(StudentAndClassDetailedDto input) {
        if (input != null) {
            String year = input.getClassYear();
            int idx = year.indexOf('-');
            if (idx >= 0) {
                String parsedYear = year.substring(0, year.indexOf('-'));
                input.setClassYear(parsedYear);
            }
        }
        return input;
    }
}
