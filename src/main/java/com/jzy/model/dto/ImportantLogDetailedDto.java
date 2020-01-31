package com.jzy.model.dto;

import com.jzy.model.entity.ImportantLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName ImportantLogDetailedDto
 * @Author JinZhiyun
 * @Description 重要日志信息详细封装，带具体执行用户信息
 * @Date 2020/1/31 13:58
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ImportantLogDetailedDto extends ImportantLog {
    private static final long serialVersionUID = -6709645450059379346L;

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
}
