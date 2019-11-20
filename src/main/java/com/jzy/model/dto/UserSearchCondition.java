package com.jzy.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserSearchCondition
 * @description 用户查询添加的封装
 * @date 2019/11/19 14:12
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UserSearchCondition extends SearchCondition {
    private static final long serialVersionUID = -8168782105848351950L;

    /**
     * 用户的工号
     */
    private String userWorkId;

    /**
     * 用户的身份证
     */
    private String userIdCard;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户的真实姓名
     */
    private String userRealName;

    /**
     * 用户身份,"管理员","学管", "助教长", "助教", "教师", "游客"
     */
    private String userRole;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户手机
     */
    private String userPhone;
}
