package com.jzy.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentSearchCondition
 * @description 学生个人信息查询条件的封装
 * @date 2019/11/26 17:49
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class StudentSearchCondition extends SearchCondition {
    private static final long serialVersionUID = -3426046776013798526L;


    /**
     * 学员号，不能为空，唯一，长度不超过32
     */
    private String studentId;

    /**
     * 学生姓名，不能为空，长度不超过50
     */
    private String studentName;

    /**
     * 性别，可以为空
     */
    private String studentSex;

    /**
     * 学生联系方式，可以为空，长度不超过11，不做手机正则校验
     */
    private String studentPhone;

    /**
     * 学生就读学校，可以为空，长度不超过50
     */
    private String studentSchool;

}
