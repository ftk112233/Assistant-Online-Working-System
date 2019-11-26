package com.jzy.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName ClassSearchCondition
 * @Author JinZhiyun
 * @Description 班级查询条件的封装
 * @Date 2019/11/25 11:10
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ClassSearchCondition extends SearchCondition {
    private static final long serialVersionUID = -5466160059919746687L;

    /**
     * 班级编码，唯一，非空，长度小于32，2019年新东方优能中学部的班级编码为12位，U6MCFB020001，
     * 其中‘u'表示优能中学部，6表示六年级，M学科，C为班型：志高，F为班级规模：25人，B表示季度，’02‘表示曹杨校区，'0001'为序号。
     * 所以这里另外加年级，学科等字段，以便后续班级编码规则改变系统更新
     */
    private String classId;

    /**
     * 班级名称，可以为空，长度不超过50
     */
    private String className;

    /**
     * 班级所属的校区，可以为空，长度不超过50
     */
    private String classCampus;

    /**
     * 班级所属年级，可以为空，长度不超过50
     */
    private String classGrade;

    /**
     * 班级所属学科，可以为空，长度不超过50
     */
    private String classSubject;

    /**
     * 班级类型，可以为空，长度不超过20
     */
    private String classType;

    /**
     * 班级开设的年份，可以为空，2019版的班级编码中没有能标识年份的字段
     */
    private String classYear;

    /**
     * 班级开设的季度，如春季，暑假等，可以为空，长度不超过50
     */
    private String classSeason;

    /**
     * 班级上课时间
     */
    private String classTime;

    /**
     * 班级上课教室
     */
    private String classroom;

    /**
     * 任课教师的工号
     */
    private String teacherId;

    /**
     * 教师工号
     */
    private String teacherWorkId;

    /**
     * 任课教师的姓名
     */
    private String teacherName;

    /**
     * 助教工号
     */
    private String assistantWorkId;

    /**
     * 助教的姓名
     */
    private String assistantName;

}
