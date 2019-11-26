package com.jzy.model.entity;

import com.jzy.manager.util.MyStringUtils;
import com.jzy.model.CampusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

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
     * 根据当前班级编码解析当前班级对象
     *
     * @param classId
     * @return
     */
    public Class setParsedClassId(String classId) {
        this.classId = classId;
        if (StringUtils.isEmpty(classId) || classId.length() != 12) {
            //不符合新东方2019年编码规范，不解析，直接常规setClassId
            return this;
        } else {
            //解析
            //年级
            switch (classId.charAt(1)) {
                case '6':
                    this.classGrade = "小初衔接";
                    break;
                case '7':
                    this.classGrade = "初一";
                    break;
                case '8':
                    this.classGrade = "初二";
                    break;
                case '9':
                    this.classGrade = "中考";
                    break;
                case 'A':
                    this.classGrade = "高一";
                    break;
                case 'B':
                    this.classGrade = "高二";
                    break;
                case 'C':
                    this.classGrade = "高考";
                    break;
                default:
            }

            //科目
            switch (classId.charAt(2)) {
                case 'Y':
                    this.classSubject = "语文";
                    break;
                case 'M':
                    this.classSubject = "数学";
                    break;
                case 'E':
                    this.classSubject = "英语";
                    break;
                case 'P':
                    this.classSubject = "物理";
                    break;
                case 'C':
                    this.classSubject = "化学";
                    break;
                case 'G':
                    this.classSubject = "地理";
                    break;
                case 'B':
                    this.classSubject = "生物";
                    break;
                case 'H':
                    this.classSubject = "历史";
                    break;
                case 'S':
                    this.classSubject = "科学";
                    break;
                case 'L':
                    this.classSubject = "联报";
                    break;
                default:
            }

            //班型
            switch (classId.charAt(3)) {
                case 'A':
                    this.classType = "好学";
                    break;
                case 'B':
                    this.classType = "精进";
                    break;
                case 'C':
                    this.classType = "志高";
                    break;
                case 'D':
                    this.classType = "行远";
                    break;
                case 'E':
                    this.classType = "壮志";
                    break;
                case 'F':
                    this.classType = "凌云";
                    break;
                case 'G':
                    this.classType = "星耀";
                    break;
                case 'Z':
                    this.classType = "专项";
                    break;
                case 'X':
                    this.classType = "虚拟";
                    break;
                default:
            }

            //季度
            switch (classId.charAt(5)) {
                case 'A':
                    this.classSeason = "暑假";
                    break;
                case 'B':
                    this.classSeason = "秋上";
                    break;
                case 'C':
                    this.classSeason = "秋下";
                    break;
                case 'D':
                    this.classSeason = "寒假";
                    break;
                case 'H':
                    this.classSeason = "春季";
                    break;
                    //TODO 班级编码规则改变？
                default:
            }

            //校区
            String no=classId.substring(6,8);
            this.classCampus=CampusEnum.getCampusNameByCode(no);
        }
        return this;
    }

    /**
     * 格式化地设置当前班级对象的上课教室, 如输入教室："YN 曹杨308教"；输出"308"
     *
     * @param classroom
     * @return
     */
    public Class setParsedClassroom(String classroom){
        this.classroom=MyStringUtils.getMaxLengthNumberSubstring(classroom);
        return this;
    }

    /**
     * 格式化地设置当前班级对象的上课时间和简化的上课时间,
     *  如输入上课时间："(具体以课表为准)周六8:15-10:45(11.2,11.9休息,11.3,11.4上课)";
     *  设置classTime="(具体以课表为准)周六8:15-10:45(11.2,11.9休息,11.3,11.4上课)"
     *      classSimplifiedTime="8:15-10:45"
     * @param classTime
     * @return
     */
    public Class setParsedClassTime(String classTime){
        this.classTime=classTime;
        this.classSimplifiedTime=MyStringUtils.getParsedTime(classTime);
        return this;
    }
}
