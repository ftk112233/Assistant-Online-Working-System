package com.jzy.model.dto;

import com.jzy.model.entity.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName QuestionWithCreatorDto
 * @description 带创建者信息的问题对象
 * @date 2019/12/3 17:07
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class QuestionWithCreatorDto extends Question {
    private static final long serialVersionUID = -2264811813380104375L;

    /**
     * 创建者姓名
     */
    private String creatorName;
}
