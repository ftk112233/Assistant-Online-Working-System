package com.jzy.model.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName MissLessonStudentSearchCondition
 * @Author JinZhiyun
 * @Description 补课学生查询条件的封装
 * @Date 2019/11/28 14:25
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class MissLessonStudentSearchCondition extends SearchCondition {
    private static final long serialVersionUID = -1052808763185112629L;

    /**
     * 学员编号，可以为空，但是唯一，不超过32个字符
     */
    private String studentId;

    /**
     * 学员姓名，不能为空，不超过50个字符
     */
    private String studentName;

    /**
     * 原来班级的班号，不是自增id
     */
    private String originalClassId;

    /**
     * 原班级名称
     */
    private String originalClassName;

    /**
     * 原班级助教工号
     */
    private String originalAssistantWorkId;

    /**
     * 原班级助教姓名
     */
    private String originalAssistantName;

    /**
     * 原班级教师工号
     */
    private String originalTeacherWorkId;

    /**
     * 原班级教师姓名
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
     * 补课班级助教姓名
     */
    private String currentAssistantName;

    /**
     * 补课班级助教工号
     */
    private String currentAssistantWorkId;

    /**
     * 补课班级教师姓名
     */
    private String currentTeacherName;

    /**
     * 补课班级教师工号
     */
    private String currentTeacherWorkId;


    /**
     * 补课的日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date date;
}
