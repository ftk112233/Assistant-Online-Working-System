package com.jzy.model.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName ImportantLogSearchCondition
 * @Author JinZhiyun
 * @Description 日志管理查询条件封装
 * @Date 2020/1/31 14:08
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ImportantLogSearchCondition extends SearchCondition {
    private static final long serialVersionUID = 8310326877101381919L;

    /**
     * 执行用户综合搜索内容
     */
    private String commonSearchUser;

    /**
     * 用户身份,"管理员","学管", "助教长", "助教", "教师", "游客"
     */
    private String userRole;

    /**
     * 日志正文内容，非空，不超过1000个字符
     */
    private String message;

    /**
     * 日志级别，不超过20个字符
     */
    private String level;

    /**
     * 触发日志事件的执行用户的ip地址，不超过200个字符
     */
    private String operatorIp;

}
