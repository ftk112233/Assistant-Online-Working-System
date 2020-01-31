package com.jzy.model.dto.search;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserMessageSearchCondition
 * @description 用户消息查询条件的封装
 * @date 2019/12/7 13:37
 **/
@Data
public class UserMessageSearchCondition implements Serializable {
    private static final long serialVersionUID = 7829420552159833296L;

    /**
     * 消息拥有者id
     */
    private Long userId;

    /**
     * 是否已读
     */
    private boolean read;
}
