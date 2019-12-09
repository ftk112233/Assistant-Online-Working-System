package com.jzy.model.entity;

import com.jzy.manager.util.MyStringUtils;
import com.jzy.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
public class Class extends BaseEntity {
    private static final long serialVersionUID = 2082795401009058210L;

    public static final List<String> SEASONS = SeasonEnum.getSeasonsList();

    public static final List<String> GRADES = GradeEnum.getGradesList();

    public static final List<String> SUBJECTS = SubjectEnum.getSubjectsList();

    public static final List<String> TYPES = TypeEnum.getTypesList();

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
     * 班级上课时间详细描述，如周日8:00-10:：00，可以为空，长度不超过50
     */
    private String classTime;

    /**
     * 简化的班级上课时间，如: 8:00-10:00，可以为空，长度不超过50
     */
    private String classSimplifiedTime;

    /**
     * 班级上课次数，有具体班级情况和学期决定，可以为空
     */
    private Integer classTimes;

    /**
     * 外键，班级任课教师的主键id，这里只的是自增的代理主键id，而不是工号teacherId，可以为空
     */
    private Long classTeacherId;

    /**
     * 外键，班级助教的主键id，这里只的是自增的代理主键id，而不是工号assistantId，可以为空
     */
    private Long classAssistantId;

    /**
     * 班级上课教室，如：313，可以为空，长度不超过20
     */
    private String classroom;

    /**
     * 任课教师开课要求，可以为空，长度不超过200
     */
    private String classTeacherRequirement;

    /**
     * 班级备注，可以为空，长度不超过500
     */
    private String classRemark;

    /**
     * 将输入年份字段由yyyy-mm-dd转成yyyy
     *
     * @param year
     * @return
     */
    public static String parseYear(String year) {
        if (!StringUtils.isEmpty(year)) {
            int idx = year.indexOf('-');
            if (idx >= 0) {
                String parsedYear = year.substring(0, year.indexOf('-'));
                return parsedYear;
            }
        }
        return year;
    }

    /**
     * 根据当前班级编码解析当前班级对象
     *
     * @param classId
     */
    public void setParsedClassId(String classId) {
        this.classId = classId;
        if (StringUtils.isEmpty(classId) || classId.length() < 12) {
            //不符合新东方2019年编码规范，不解析，直接常规setClassId
            return;
        } else {
            //解析
            //年级
            this.classGrade = GradeEnum.getGradeByCode(Character.toString(classId.charAt(1)));

            //科目
            this.classSubject = SubjectEnum.getSubjectByCode(Character.toString(classId.charAt(2)));

            //班型
            this.classType = TypeEnum.getTypeByCode(Character.toString(classId.charAt(3)));

            //classId.charAt(4)：F-25人，I-50人；业务无关属性，不解析

            //季度
            //TODO 班级编码规则改变？
            this.classSeason = SeasonEnum.getSeasonByCode(Character.toString(classId.charAt(5)));

            //校区
            String no = classId.substring(6, 8);
            this.classCampus = CampusEnum.getCampusNameByCode(no);
        }
    }

    /**
     * 格式化地设置当前班级对象的上课教室, 如输入教室："YN 曹杨308教"；输出"308"
     *
     * @param classroom
     */
    public void setParsedClassroom(String classroom) {
        this.classroom = MyStringUtils.getMaxLengthNumberSubstring(classroom);
    }

    /**
     * 格式化地设置当前班级对象的上课时间和简化的上课时间,
     * 如输入上课时间："(具体以课表为准)周六8:15-10:45(11.2,11.9休息,11.3,11.4上课)";
     * 设置classTime="(具体以课表为准)周六8:15-10:45(11.2,11.9休息,11.3,11.4上课)"
     * classSimplifiedTime="8:15-10:45"
     *
     * @param classTime
     */
    public void setParsedClassTime(String classTime) {
        this.classTime = classTime;
        this.classSimplifiedTime = MyStringUtils.getParsedTime(classTime);
    }


    /**
     * 将输入对象的年份字段由yyyy-mm-dd转成yyyy
     */
    public void setParsedClassYear() {
        String year = this.getClassYear();
        this.setClassYear(parseYear(year));
    }
}
