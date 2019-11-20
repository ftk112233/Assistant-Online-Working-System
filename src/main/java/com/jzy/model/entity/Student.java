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
     * 学员号
     */
    private String studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 性别
     */
    private String studentSex;

    /**
     * 学生联系方式
     */
    private String studentPhone;

    /**
     * 学生联系方式（备用）
     */
    private String studentPhoneBackup;

    /**
     * 学生就读学校
     */
    private String studentSchool;

    /**
     * 学生备注
     */
    private String studentRemark;
}
