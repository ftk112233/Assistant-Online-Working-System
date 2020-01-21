package com.jzy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName MissLessonStudentDetailedDto
 * @Author JinZhiyun
 * @Description 补课学生信息的dto对象
 * @Date 2019/11/28 13:54
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class MissLessonStudentDetailedDto extends BaseEntity {
    private static final long serialVersionUID = 7427253186463280614L;

    /**
     * 学员编号，可以为空，但是唯一，不超过32个字符
     */
    private String studentId;

    /**
     * 学员姓名，不能为空，不超过50个字符
     */
    private String studentName;

    /**
     * 学员联系方式，可以为空，或者11位手机号。
     * 这里不同于用户的手机，学生老师助教手机号一律不作手机号的正则校验，以避免不必要的麻烦
     */
    private String studentPhone;

    /**
     * 原来班级的班号，不是自增id
     */
    private String originalClassId;

    /**
     * 原班级名称
     */
    private String originalClassName;

    /**
     * 原班级教室
     */
    private String originalClassroom;

    /**
     * 原班级上课时间
     */
    private String originalClassTime;

    /**
     * 原班级上课时间简化版
     */
    private String originalClassSimplifiedTime;

    /**
     * 原班级助教
     */
    private String originalAssistantName;

    /**
     * 原班级教师
     */
    private String originalTeacherName;


    /**
     * 补课班级的班号，不是自增id
     */
    private String currentClassId;


    /**
     * 补课班级名称
     */
    private String currentClassName;

    /**
     * 补课班级教室
     */
    private String currentClassroom;

    /**
     * 补课班级上课时间
     */
    private String currentClassTime;

    /**
     * 补课班级上课时间简化版
     */
    private String currentClassSimplifiedTime;

    /**
     * 补课班级年级
     */
    private String currentClassGrade;

    /**
     * 补课班级年级
     */
    private String currentClassSubject;

    /**
     * 补课班级助教
     */
    private String currentAssistantName;

    /**
     * 补课班级教师
     */
    private String currentTeacherName;

    /**
     * 补课的日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date date;

    /**
     * 备注，可以为空，或不超过500个字符
     */
    private String remark;
}
