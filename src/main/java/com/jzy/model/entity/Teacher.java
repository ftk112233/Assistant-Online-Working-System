package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Teacher
 * @description 教师实体类
 * @date 2019/11/13 14:28
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Teacher extends BaseEntity {
    private static final long serialVersionUID = -1081478262543186598L;

    /**
     * 教师工号
     */
    private String teacherWorkId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 性别
     */
    private String teacherSex;

    /**
     * 教师手机
     */
    private String teacherPhone;

    /**
     * 教师备注
     */
    private String teacherRemark;
}
