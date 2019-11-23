package com.jzy.model.dto;

import com.jzy.model.entity.Class;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName ClassDetailedDto
 * @Author JinZhiyun
 * @Description 班级详细信息的封装
 * @Date 2019/11/23 8:44
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ClassDetailedDto extends Class {
    private static final long serialVersionUID = 4966784228299705444L;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 助教姓名，唯一，非空，不超过50个字符
     */
    private String assistantName;
}
