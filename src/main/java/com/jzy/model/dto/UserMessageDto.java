package com.jzy.model.dto;

import com.jzy.model.entity.UserMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserMessageDto
 * @description 用户消息的传输对象
 * @date 2019/12/7 10:54
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UserMessageDto extends UserMessage {
    private static final long serialVersionUID = 8984482335593976506L;

    /**
     * 发送方的角色
     */
    private String userFromRole;

    /**
     * 发送方的真实姓名
     */
    private String userFromRealName;

    /**
     * 发送方的头像
     */
    private String userFromIcon;

    /**
     * 发送方的综合描述：userFromRole+userFromRealName
     */
    private String userFrom;

    /**
     * 设置 发送方的综合描述
     */
    public void setDefaultUserFrom(){
        userFrom=userFromRole+userFromRealName;
    }
}
