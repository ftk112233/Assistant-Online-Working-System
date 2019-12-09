package com.jzy.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserSendTo
 * @description 发送消息页面，用户综合搜索结果的封装
 * @date 2019/12/7 20:05
 **/
@Data
public class UserSendTo implements Serializable {
    private static final long serialVersionUID = 5725913076872538312L;

    /**
     * 主键id，自增
     */
    protected Long id;

    /**
     * 用户的工号，即助教的工号，唯一，长度不超过32可以为空
     */
    private String userWorkId;

    /**
     * 用户名，唯一，可以自定义，6~20位(数字、字母、下划线)以字母开头
     */
    private String userName;

    /**
     * 用户的真实姓名，非空，不超过50个字符
     */
    private String userRealName;

    /**
     * 用户身份,"管理员","学管", "助教长", "助教", "教师", "游客"
     */
    private String userRole;

    /**
     * 用户的头像存储的地址路径，空或者长度小于等于100
     */
    private String userIcon;

    /**
     * 用户对应助教的所属校区
     */
    private String campus;
}
