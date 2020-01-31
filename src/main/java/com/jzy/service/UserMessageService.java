package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidFileInputException;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UserMessageDto;
import com.jzy.model.dto.search.UserMessageSearchCondition;
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
     * @return 消息对象
     */
    UserMessage getUserMessageById(Long id);

    /**
     * 根据主键id查询消息传输对象，含发送者的详细信息等
     *
     * @param id 主键id
     * @return 用户消息详情
     */
    UserMessageDto getUserMessageDtoById(Long id);

    /**
     * 根据拥有消息的用户的id查询消息
     *
     * @param userId 拥有消息的用户的id
     * @return 消息对象
     */
    UserMessage getUserMessageByUserId(Long userId);

    /**
     * 根据用户id（发送方）查询消息
     *
     * @param userFromId 用户id（发送方）
     * @return 消息对象
     */
    UserMessage getUserMessageByUserFromId(Long userFromId);

    /**
     * 查询属于userId的全部消息
     *
     * @param userId 消息所有者id
     * @return 消息对象
     */
    List<UserMessageDto> listUserMessagesByUserId(Long userId);

    /**
     * 查询指定条件的用户消息
     *
     * @param myPage    分页的封装
     * @param condition 查询条件
     * @return 分页结果
     */
    PageInfo<UserMessageDto> listUserMessages(MyPage myPage, UserMessageSearchCondition condition);

    /**
     * 根据已读条件查询用户消息
     *
     * @param myPage 分页的封装
     * @param read   是否已读
     * @return 分页结果
     */
    PageInfo<UserMessageDto> listUserMessagesByUserIdAndRead(MyPage myPage, Long userId, boolean read);

    /**
     * 根据输入id将消息标记已读
     *
     * @param id 主键id
     * @return 更新记录数
     */
    long updateUserMessageReadById(Long id);

    /**
     * 查询当前用户id下，状态为read的消息数量
     *
     * @param userId 用户id
     * @param read   布尔值是否已读
     * @return 状态为read的消息数量
     */
    long countUserMessagesByUserIdAndRead(Long userId, boolean read);

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
    long updateManyUserMessagesReadByUserId(Long userId);

    /**
     * 删除多个消息
     *
     * @param ids 多个消息的id的列表
     * @return 更新记录数
     */
    long deleteManyUserMessagesByIds(List<Long> ids);

    /**
     * 添加一条消息。
     * 对用户上传的图片：如果不是欢迎图片，就要做重命名。
     *
     * @param userMessage 新添加的消息
     * @return 1."failure"：错误入参等异常
     * 2."success": 更新成功
     */
    String insertOneUserMessage(UserMessage userMessage);

    /**
     * 上传消息图片附件
     *
     * @param file 上传得到的文件
     * @return 保存文件的名称，用来传回前端
     * @throws InvalidFileInputException 不合法的文件入参
     */
    String uploadPicture(MultipartFile file) throws InvalidFileInputException;

    /**
     * 上传消息图片附件
     *
     * @param file 上传得到的文件
     * @param id   用户id
     * @return 保存文件的名称，用来传回前端
     * @throws InvalidFileInputException 不合法的文件入参
     */
    String uploadPicture(MultipartFile file, String id) throws InvalidFileInputException;

    /**
     * 批量插入用户消息
     *
     * @param userMessages 插入的消息列表
     * @return 1."failure"：错误入参等异常
     * 2."success": 更新成功
     * @throws Exception 消息图片文件操作时发生的异常
     */
    String insertManyUserMessages(List<UserMessage> userMessages) throws Exception;
}
