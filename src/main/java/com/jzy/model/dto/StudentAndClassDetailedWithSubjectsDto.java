package com.jzy.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName StudentAndClassDetailedWithSubjectsDto
 * @Author JinZhiyun
 * @Description 带有学员所读学科的学员上课信息对象
 * @Date 2019/11/27 11:20
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class StudentAndClassDetailedWithSubjectsDto extends StudentAndClassDetailedDto {
    private static final long serialVersionUID = -2276198196031102476L;

    /**
     * 当前学生的所有在读学科
     */
    private List<String> subjects;
}
