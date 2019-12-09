package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.model.dto.AssistantSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UpdateResult;
import com.jzy.model.entity.Assistant;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName AssistantService
 * @description 助教业务
 * @date 2019/11/14 23:27
 **/
public interface AssistantService {
    /**
     * 根据助教id查询出助教信息
     *
     * @param id 助教id
     * @return
     */
    Assistant getAssistantById(Long id);

    /**
     * 根据助教工号查询出助教信息
     *
     * @param assistantWorkId 助教工号
     * @return
     */
    Assistant getAssistantByWorkId(String assistantWorkId);

    /**
     * 根据助教姓名查询出助教信息
     *
     * @param assistantName 助教姓名
     * @return
     */
    Assistant getAssistantByName(String assistantName);

    /**
     * 根据助教校区查询出助教信息
     *
     * @param campus 助教校区
     * @return
     */
    List<Assistant> listAssistantsByCampus(String campus);

    /**
     * 添加助教
     *
     * @param assistant 新添加助教的信息
     * @return
     */
    UpdateResult insertAssistant(Assistant assistant);

    /**
     * 修改助教信息由id修改
     *
     * @param assistant 修改后的助教信息
     * @return
     */
    String updateAssistantInfo(Assistant assistant);

    /**
     * 修改助教信息由工号修改
     *
     * @param assistant 修改后的助教信息
     * @return
     */
    UpdateResult updateAssistantByWorkId(Assistant assistant);


    /**
     * 修改助教信息由工号修改
     *
     * @param originalAssistant 原来的助教信息
     * @param newAssistant      新的助教信息
     * @return
     */
    UpdateResult updateAssistantByWorkId(Assistant originalAssistant, Assistant newAssistant);


    /**
     * 根据从excel中读取到的assistant信息，更新插入多个。根据工号判断：
     * if 当前工号不存在
     * 执行插入
     * else
     * 根据工号更新
     *
     * @param assistants
     * @return
     */
    UpdateResult insertAndUpdateAssistantsFromExcel(List<Assistant> assistants) throws InvalidParameterException;

    /**
     * 根据从excel中读取到的assistant信息，更新插入一个。根据工号判断：
     * if 当前工号不存在
     * 执行插入
     * else
     * 根据工号更新
     *
     * @param assistant
     * @return
     */
    UpdateResult insertAndUpdateOneAssistantFromExcel(Assistant assistant) throws Exception;

    /**
     * 返回符合条件的助教信息分页结果
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    PageInfo<Assistant> listAssistants(MyPage myPage, AssistantSearchCondition condition);

    /**
     * 根据id删除一个助教
     *
     * @param id 被删除助教的id
     * @return
     */
    long deleteOneAssistantById(Long id);

    /**
     * 根据id删除多个助教
     *
     * @param ids 助教id的列表
     */
    long deleteManyAssistantsByIds(List<Long> ids);

    /**
     * 根据输入条件删除指定的助教
     *
     * @param condition 输入条件封装
     * @return
     */
    String deleteAssistantsByCondition(AssistantSearchCondition condition);

}
