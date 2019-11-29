package com.jzy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName StudentAndClassDetailedDto
 * @Author JinZhiyun
 * @Description 学生上课信息的封装
 * @Date 2019/11/22 21:57
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class StudentAndClassDetailedDto extends BaseEntity {
    private static final long serialVersionUID = 730389930087152500L;

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
     * 学生联系方式（备用）
     */
    private String studentSchool;

    /**
     * 班级编码，唯一，非空，2019年新东方优能中学部的班级编码为12位，U6MCFB020001，
     * 其中‘u'表示优能中学部，6表示六年级，M学科，C为班型：志高，F为班级规模：25人，B表示季度，’02‘表示曹杨校区，'0001'为序号。
     * 所以这里另外加年级，学科等字段，以便后续班级编码规则改变系统更新
     */
    private String classId;

    /**
     * 班级名称，可以为空，长度不超过50
     */
    private String className;

    /**
     * 班级所属的校区
     */
    private String classCampus;

    /**
     * 班级所属年级
     */
    private String classGrade;

    /**
     * 班级所属学科
     */
    private String classSubject;

    /**
     * 班级类型
     */
    private String classType;

    /**
     * 班级开设的年份，2019版的班级编码中没有能标识年份的字段
     */
    private String classYear;

    /**
     * 班级开设的季度，如春季，暑假等
     */
    private String classSeason;

    /**
     * 班级上课时间详细描述，如周日8:00-10:：00
     */
    private String classTime;

    /**
     * 简化的班级上课时间，如: 8:00-10:：00
     */
    private String classSimplifiedTime;

    /**
     * 班级上课次数，有具体班级情况和学期决定
     */
    private Integer classTimes;

    /**
     * 班级任课教师的姓名
     */
    private String teacherName;

    /**
     * 班级助教的主键姓名
     */
    private String assistantName;

    /**
     * 班级上课教室，如：313
     */
    private String classroom;

    /**
     * 任课教师开课要求，可以为空，长度不超过200
     */
    private String classTeacherRequirement;

    /**
     * 学生报名进班时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date registerTime;

    /**
     * 学生上课中间表的备注
     */
    private String remark;
}
