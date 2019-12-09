package com.jzy.manager.util;

import com.jzy.model.entity.UserMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserMessageUtils
 * @description 有用信息的工具类 {@link com.jzy.model.entity.UserMessage}
 * 删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @date 2019/12/6 21:11
 **/
public class UserMessageUtils {
    private UserMessageUtils() {
    }

    public static boolean isValidUserId(Long userId) {
        return userId != null;
    }

    public static boolean isValidUserFromId(Long userFromId) {
        return true;
    }

    public static boolean isValidMessageTitle(String messageTitle) {
        return !StringUtils.isEmpty(messageTitle) && messageTitle.length() <= 100;
    }

    public static boolean isValidMessageContent(String messageContent) {
        return !StringUtils.isEmpty(messageContent) && messageContent.length() <= 1000;
    }

    public static boolean isValidMessagePicture(String messagePicture) {
        return StringUtils.isEmpty(messagePicture) || (FileUtils.isImage(messagePicture) && messagePicture.length() <= 100);
    }

    public static boolean isValidMessageTime(Date messageTime) {
        return messageTime !=null;
    }

    public static boolean isValidRead(boolean read) {
        return true;
    }
    public static boolean isValidMessageRemark(String messageRemark) {
        return messageRemark == null || messageRemark.length() <= 500;
    }

    /**
     * userMessage是否合法
     *
     * @param userMessage 输入的userMessage对象
     * @return
     */
    public static boolean isValidUserMessageInfo(UserMessage userMessage) {
        return userMessage != null && isValidUserId(userMessage.getUserId()) && isValidUserFromId(userMessage.getUserFromId())
                && isValidMessageTitle(userMessage.getMessageTitle()) && isValidMessageContent(userMessage.getMessageContent())
                && isValidMessagePicture(userMessage.getMessagePicture()) && isValidMessageTime(userMessage.getMessageTime())
                && isValidRead(userMessage.isRead()) && isValidMessageRemark(userMessage.getMessageRemark());
    }

    /**
     * UserMessage是否合法
     *
     * @param userMessage 输入的UserMessage对象
     * @return
     */
    public static boolean isValidUserMessageUpdateInfo(UserMessage userMessage) {
        return isValidUserMessageInfo(userMessage);
    }
}
