package com.jzy.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserMessage
 * @description 用户的消息
 * @date 2019/12/6 20:23
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UserMessage extends BaseEntity {
    private static final long serialVersionUID = 8877334245211730478L;

    /**
     * 欢迎信息的配图
     */
    public static final String WELCOME_PICTURE="welcome.png";

    /**
     * 收消息用户的id，非空
     */
    private Long userId;

    /**
     * 收消息用户的id
     */
    private Long userFromId;

    /**
     * 消息标题，非空，且不超过100个字符
     */
    private String messageTitle;

    /**
     * 消息内容，非空，且不超过1000个字符
     */
    private String messageContent;

    /**
     * 消息图片路径，可以为空，不超过100个字符
     */
    private String messagePicture;

    /**
     * 消息生成日期，非空，且不超过100个字符
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date messageTime;

    /**
     * 消息是否已读
     */
    private boolean read;

    /**
     * 消息备注，可以为空，不超过500个字符
     */
    private String messageRemark;
}
