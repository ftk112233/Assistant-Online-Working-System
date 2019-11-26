package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Student
 * @description 学生实体类
 * @date 2019/11/13 14:31
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Student extends BaseEntity {
    private static final long serialVersionUID = -1784823362625521029L;

    /**
     * 学员号，不能为空，唯一，长度不超过32
     */
    private String studentId;

    /**
     * 学生姓名，不能为空，长度不超过50
     */
    private String studentName;

    /**
     * 性别，可以为空
     */
    private String studentSex;

    /**
     * 学生联系方式，可以为空，长度不超过11，不做手机正则校验
     */
    private String studentPhone;

    /**
     * 学生联系方式（备用），可以为空，长度不超过11，不做手机正则校验
     */
    private String studentPhoneBackup;

    /**
     * 学生就读学校，可以为空，长度不超过50
     */
    private String studentSchool;

    /**
     * 学生备注，可以为空，长度不超过500
     */
    private String studentRemark;
}
