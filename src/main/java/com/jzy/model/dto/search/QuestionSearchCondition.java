package com.jzy.model.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName QuestionSearchCondition
 * @description 登录问题查询条件的封装
 * @date 2019/12/3 17:04
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class QuestionSearchCondition extends SearchCondition {
    private static final long serialVersionUID = -8941623540302957462L;

    /**
     * 问题内容，唯一非空，不超过500个字符
     */
    private String content;

    /**
     * 问题创建者名字
     */
    private String creatorName;
}
