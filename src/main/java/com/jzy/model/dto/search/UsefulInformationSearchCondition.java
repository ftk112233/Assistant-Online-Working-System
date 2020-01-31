package com.jzy.model.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UsefulInformationSearchCondition
 * @description 常用信息查询条件的封装
 * @date 2019/12/6 11:43
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UsefulInformationSearchCondition extends SearchCondition {
    private static final long serialVersionUID = 3028329597232114552L;

    /**
     * 信息主题，非空，不超过100个字符
     */
    private String title;

    /**
     * 信息内容，非空，不超过200个字符
     */
    private String content;

    /**
     * 该信息的所有者，通常是校区，非空，不超过50个字符
     */
    private String belongTo;

    /**
     * 该信息的顺序，不为空，和校区组合唯一
     */
    private Long sequence;
}
