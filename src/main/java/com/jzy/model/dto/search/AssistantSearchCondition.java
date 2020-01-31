package com.jzy.model.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName AssistantSearchCondition
 * @Author JinZhiyun
 * @Description 助教查询条件的封装
 * @Date 2019/11/21 22:33
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AssistantSearchCondition extends SearchCondition {
    private static final long serialVersionUID = 2070466002733930925L;

    /**
     * 助教工号，唯一，长度小于等于32可以为空
     */
    private String assistantWorkId;

    /**
     * 助教姓名，唯一，非空，不超过50个字符
     */
    private String assistantName;

    /**
     * 性别，可以为null
     */
    private String assistantSex;

    /**
     * 助教所在校区，可以为空，不超过20个字符，且在所有枚举对象中{@link com.jzy.model.CampusEnum}
     */
    private String assistantCampus;

    /**
     * 助教手机号，空或者11位数字
     */
    private String assistantPhone;
}
