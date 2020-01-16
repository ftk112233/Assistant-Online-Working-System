package com.jzy.dao;

import com.jzy.model.dto.UserMessageDto;
import com.jzy.model.dto.UserMessageSearchCondition;
import com.jzy.model.entity.UserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName UserMessageMapper
 * @description 用户消息业务dao接口
 * @date 2019/12/6 20:34
 **/
public interface UserMessageMapper {
    /**
     * 根据主键id查询消息
     *
     * @param id 主键id
     * @return 对应消息对象
     */
    UserMessage getUserMessageById(@Param("id") Long id);

    /**
     * 根据拥有消息的用户的id查询消息
     *
     * @param userId 拥有消息的用户的id
     * @return 对应消息对象
     */
    UserMessage getUserMessageByUserId(@Param("userId") Long userId);

    /**
     * 根据用户id（发送方）查询消息
     *
     * @param userFromId 用户id（发送方）
     * @return 对应消息对象
     */
    UserMessage getUserMessageByUserFromId(@Param("userFromId") Long userFromId);

    /**
     * 查询指定条件的用户消息
     *
     * @param condition 查询条件
     * @return 符合条件的消息
     */
    List<UserMessageDto> listUserMessages(UserMessageSearchCondition condition);

    /**
     * 根据已读条件查询用户消息
     *
     * @param read 是否已读
     * @return 符合条件的消息
     */
    List<UserMessageDto> listUserMessagesByRead(@Param("userId") Long userId, @Param("read") boolean read);

    /**
     * 根据主键id查询消息传输对象，含发送者的详细信息等
     *
     * @param id 主键id
     * @return 用户消息详情
     */
    UserMessageDto getUserMessageDtoById(@Param("id") Long id);

    /**
     * 根据输入id将消息标记已读
     *
     * @param id 主键id
     * @return 更新记录数
     */
    long updateUserMessageReadById(@Param("id") Long id);

    /**
     * 查询当前用户id下，状态为read的消息数量
     *
     * @param userId 用户id
     * @param read   布尔值是否已读
     * @return 状态为read的消息数量
     */
    long countUserMessagesByUserIdAndRead(@Param("userId") Long userId, @Param("read") boolean read);

    /**
     * 根据输入id的列表将消息标记已读
     *
     * @param ids 主键id的列表
     * @return 更新记录数
     */
    long updateManyUserMessagesReadByIds(List<Long> ids);

    /**
     * 将指定所有者用户id的所有消息置为已读
     *
     * @param userId 用户id
     * @return 更新记录数
     */
    long updateManyUserMessagesReadByUserId(@Param("userId") Long userId);

    /**
     * 删除多个消息
     *
     * @param ids 多个消息的id的列表
     * @return 更新记录数
     */
    long deleteManyUserMessagesByIds(List<Long> ids);

    /**
     * 添加一条消息
     *
     * @param userMessage 新添加的消息
     * @return 更新记录数
     */
    long insertOneUserMessage(UserMessage userMessage);

    /**
     * 批量添加消息
     *
     * @param userMessages 新添加的消息集合
     * @return 更新记录数
     */
    long insertManyUserMessages(List<UserMessage> userMessages);
}
