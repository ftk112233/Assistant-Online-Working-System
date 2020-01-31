package com.jzy.model.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserSendToSearchCondition
 * @description 发送消息页面，用户综合搜索的搜索入参封装
 * @date 2019/12/7 20:08
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UserSendToSearchCondition extends SearchCondition {
    private static final long serialVersionUID = -4338283832871719437L;

    /**
     * 综合搜索内容
     */
    private String commonSearch;

    /**
     * 角色
     */
    private String userRole;

    /**
     * 用户对应助教的所属校区
     */
    private String campus;
}
