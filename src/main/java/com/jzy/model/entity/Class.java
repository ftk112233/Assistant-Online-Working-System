package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Class
 * @description 班级实体类
 * @date 2019/11/13 14:41
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Class extends BaseEntity{
    private static final long serialVersionUID = 2082795401009058210L;

    /**
     * 班级编码，2019年新东方优能中学部的班级编码为12位，U6MCFB020001，
     *  其中‘u'表示优能中学部，6表示六年级，M学科，C为班型：志高，F为班级规模：25人，B表示季度，’02‘表示曹杨校区，'0001'为序号。
     *  所以这里不另外加年级，学科等字段，以便后续班级编码规则改变系统更新
     */
    private String classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级开设的年份，2019版的班级编码中没有能标识年份的字段
     */
    private Date classYear;

    /**
     * 班级上课时间，如周日8:00-10:：00
     */
    private String classTime;

    /**
     * 班级上课次数，有具体班级情况和学期决定
     */
    private Integer classTimes;

    /**
     * 外键，班级任课教师的主键id，这里只的是自增的代理主键id，而不是工号teacherId
     */
    private Long classTeacherId;

    /**
     * 外键，班级助教的主键id，这里只的是自增的代理主键id，而不是工号assistantId
     */
    private Long classAssistantId;

    /**
     * 班级上课教室，如：313
     */
    private String classroom;

    /**
     * 任课教师开课要求
     */
    private String classTeacherRequirement;

    /**
     * 班级备注
     */
    private String classRemark;
}
