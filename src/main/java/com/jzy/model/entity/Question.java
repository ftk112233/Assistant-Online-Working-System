package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Question
 * @description 游客登录的问题
 * @date 2019/12/3 14:42
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Question extends BaseEntity {
    private static final long serialVersionUID = 6821814764043675116L;

    /**
     * 永真答案
     */
    public static final String ALWAYS_TRUE_ANSWER = "金爷nb";

    /**
     * 问题内容，唯一非空，不超过500个字符
     */
    private String content;

    /**
     * 问题万能答案——金爷nb，非空，不超过100个字符
     */
    private String trueAnswer;

    /**
     * 问题标准答案，非空，不超过100个字符
     */
    private String answer;

    /**
     * 问题备用答案，不超过100个字符
     */
    private String answer2;

    /**
     * 问题创建者
     */
    private Long creatorId;

    /**
     * 问题备注，不超过500个字符
     */
    private String remark;

    public Question() {
    }

    public Question(String content, String answer) {
        this.content = content;
        this.answer = answer;
    }

    public Question(String content, String answer, String answer2) {
        this.content = content;
        this.answer = answer;
        this.answer2 = answer2;
    }
}
