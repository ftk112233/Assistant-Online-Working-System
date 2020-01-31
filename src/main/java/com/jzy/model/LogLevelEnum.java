package com.jzy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName LogLevelEnum
 * @description 日志级别枚举，四种
 * @date 2020/1/31 9:43
 **/
public enum LogLevelEnum {
    /**
     * debug级别
     */
    DEBUG("debug"),

    /**
     * info级别
     */
    INFO("info"),

    /**
     * warn级别
     */
    WARN("warn"),

    /**
     * error级别
     */
    ERROR("error");

    private String level;

    public String getLevel() {
        return level;
    }

    LogLevelEnum(String level) {
        this.level=level;
    }

    /**
     * 获取所有日志级别列表
     *
     * @return 所有日志级别的list
     */
    public static List<String> getLevelsList() {
        List<String> list = new ArrayList<>();
        for (LogLevelEnum levelEnum : LogLevelEnum.values()) {
            list.add(levelEnum.getLevel());
        }
        return list;
    }
}
