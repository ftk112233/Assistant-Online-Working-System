package com.jzy.model.entity;

import com.jzy.model.LogLevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName ImportantLog
 * @Author JinZhiyun
 * @Description 持久化到数据库的重要日志记录
 * @Date 2020/1/31 9:18
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ImportantLog extends BaseEntity {
    private static final long serialVersionUID = 2936879846768643336L;

    /**
     * 所有日志级别的list
     */
    public static final List<String> LEVELS = LogLevelEnum.getLevelsList();

    /**
     * 日志正文内容，非空，不超过1000个字符
     */
    private String message;

    /**
     * 日志级别，不超过20个字符
     */
    private String level;

    /**
     * 触发日志事件的执行用户的id
     */
    private Long operatorId;

    /**
     * 触发日志事件的执行用户的ip地址，不超过200个字符
     */
    private String operatorIp;

    /**
     * 备注，可以为空，或不超过500个字符
     */
    private String remark;
}
