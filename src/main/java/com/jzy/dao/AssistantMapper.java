package com.jzy.dao;

import com.jzy.model.dto.AssistantSearchCondition;
import com.jzy.model.entity.Assistant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName AssistantMapper
 * @description 助教业务dao接口
 * @date 2019/11/13 16:17
 **/
public interface AssistantMapper {
    /**
     * 根据助教id查询出助教信息
     *
     * @param id 助教id
     * @return
     */
    Assistant getAssistantById(@Param("id") Long id);

    /**
     * 根据助教工号查询出助教信息
     *
     * @param assistantWorkId 助教工号
     * @return
     */
    Assistant getAssistantByWorkId(@Param("assistantWorkId") String assistantWorkId);

    /**
     * 根据助教姓名查询出助教信息
     *
     * @param assistantName 助教姓名
     * @return
     */
    Assistant getAssistantByName(@Param("assistantName") String assistantName);

    /**
     * 添加助教
     *
     * @param assistant 新添加助教的信息
     * @return
     */
    void insertAssistant(Assistant assistant);

    /**
     * 修改助教信息由id修改
     *
     * @param assistant 修改后的助教信息
     * @return
     */
    void updateAssistantInfo(Assistant assistant);

    /**
     * 修改助教信息由工号修改
     *
     * @param assistant 修改后的助教信息
     * @return
     */
    void updateAssistantByWorkId(Assistant assistant);

    /**
     * 查询符合条件的助教信息
     *
     * @param condition  查询条件入参
     * @return
     */
    List<Assistant> listAssistants(AssistantSearchCondition condition);

    /**
     * 根据id删除一个助教
     *
     * @param id  被删除助教的id
     * @return
     */
    void deleteOneAssistantById(@Param("id") Long id);


    /**
     * 根据id删除多个助教
     *
     * @param ids 助教id的列表
     */
    void deleteManyAssistantsByIds(List<Long> ids);
}
