package com.jzy.model.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName TeacherSearchCondition
 * @Author JinZhiyun
 * @Description 教师查询条件的封装
 * @Date 2019/11/24 17:23
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class TeacherSearchCondition extends SearchCondition {
    private static final long serialVersionUID = -2964583001985609828L;

    /**
     * 教师工号，唯一，长度小于等于32可以为空
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
}
