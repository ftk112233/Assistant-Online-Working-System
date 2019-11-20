package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName User
 * @description 用户实体类
 * @date 2019/11/13 16:11
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = 3383159835719833836L;

    /**
     * 用户的工号，即助教的工号，唯一，长度小于32可以为空
     */
    private String userWorkId;

    /**
     * 用户的身份证，可以为空
     */
    private String userIdCard;

    /**
     * 用户名，唯一，可以自定义，6~20位(数字、字母、下划线)以字母开头
     */
    private String userName;

    /**
     * 用户密码，非空，6~20个字符(数字、字母、下划线)
     */
    private String userPassword;

    /**
     * 用户密码加密所用的盐
     */
    private String userSalt;

    /**
     * 用户的真实姓名，非空，50个字符以内
     */
    private String userRealName;

    /**
     * 用户身份,"管理员","学管", "助教长", "助教", "教师", "游客"
     */
    private String userRole;

    /**
     * 用户的头像存储的地址路径，空或者长度小于100
     */
    private String userIcon;

    /**
     * 用户邮箱，唯一，空或者长度小于100
     */
    private String userEmail;

    /**
     * 用户手机，唯一，空或者11位数字
     */
    private String userPhone;

    /**
     * 用户备注，空或者长度小于500
     */
    private String userRemark;
}
