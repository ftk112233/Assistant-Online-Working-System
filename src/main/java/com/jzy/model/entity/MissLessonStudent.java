package com.jzy.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName MissLessonStudentService
 * @Author JinZhiyun
 * @Description 补课学生实体类
 * @Date 2019/11/21 21:53
 * @Version 1.0
 **/
@Data
public class MissLessonStudent extends BaseEntity{
    private static final long serialVersionUID = -6186014025068181186L;

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
     * 原来班级的自增主键id
     */
    private Long originalClassId;

    /**
     * 补课班级的自增主键id
     */
    private Long currentClassId;

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
