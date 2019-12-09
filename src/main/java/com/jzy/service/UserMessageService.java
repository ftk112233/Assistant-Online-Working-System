package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UserMessageDto;
import com.jzy.model.dto.UserMessageSearchCondition;
import com.jzy.model.entity.UserMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName UserMessageService
 * @description 用户消息的业务接口
 * @date 2019/12/6 20:33
 **/
public interface UserMessageService {
    /**
     * 根据主键id查询消息
     *
     * @param id 主键id
     * @return
     */
    UserMessage getUserMessageById(Long id);

    /**
     * 根据主键id查询消息传输对象，含发送者的详细信息等
     *
     * @param id 主键id
     * @return
     */
    UserMessageDto getUserMessageDtoById(Long id);

    /**
     * 根据拥有消息的用户的id查询消息
     *
     * @param userId 拥有消息的用户的id
     * @return
     */
    UserMessage getUserMessageByUserId(Long userId);

    /**
     * 根据用户id（发送方）查询消息
     *
     * @param userFromId 用户id（发送方）
     * @return
     */
    UserMessage getUserMessageByUserFromId(Long userFromId);

    /**
     * 查询属于userId的全部消息
     *
     * @param userId 消息所有者id
     * @return
     */
    List<UserMessageDto> listUserMessagesByUserId(Long userId);

    /**
     *   查询指定条件的用户消息
     *
     * @param myPage
     * @param condition 查询条件
     * @return
     */
    PageInfo<UserMessageDto> listUserMessages(MyPage myPage, UserMessageSearchCondition condition);

    /**
     *  根据已读条件查询用户消息
     *
     * @param myPage
     * @param read 是否已读
     * @return
     */
    PageInfo<UserMessageDto> listUserMessagesByUserIdAndRead(MyPage myPage, Long userId,  boolean read);

    /**
     * 根据输入id将消息标记已读
     *
     * @param id 主键id
     * @return
     */
    long updateUserMessageReadById(Long id);

    /**
     * 查询当前用户id下，状态为read的消息数量
     *
     * @param userId  用户id
     * @param read  布尔值是否已读
     * @return
     */
    long countUserMessagesByUserIdAndRead(Long userId ,boolean read);

    /**
     * 根据输入id的列表将消息标记已读
     *
     * @param ids 主键id的列表
     * @return
     */
    long updateManyUserMessagesReadByIds(List<Long> ids);

    /**
     * 将指定所有者用户id的所有消息置为已读
     *
     * @param userId 用户id
     * @return
     */
    long updateManyUserMessagesReadByUserId(Long userId);

    /**
     * 删除多个消息
     *
     * @param ids 多个消息的id的列表
     * @return
     */
    long deleteManyUserMessagesByIds(List<Long> ids);

    /**
     * 添加一条消息
     *
     * @param userMessage 新添加的消息
     * @return
     */
    String insertUserMessage(UserMessage userMessage);

    /**
     * 上传消息图片附件
     *
     * @param file 上传得到的文件
     * @return 保存文件的名称，用来传回前端
     */
    String uploadPicture(MultipartFile file) throws InvalidParameterException;

    /**
     * 上传消息图片附件
     *
     * @param file 上传得到的文件
     * @param id 用户id
     * @return 保存文件的名称，用来传回前端
     */
    String uploadPicture(MultipartFile file, String id) throws InvalidParameterException;

    /**
     * 批量插入用户消息
     *
     * @param userMessages
     * @return
     */
    String insertManyUserMessages(List<UserMessage> userMessages) throws Exception;
}
