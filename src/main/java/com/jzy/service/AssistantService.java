package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.*;
import com.jzy.model.dto.search.AssistantSearchCondition;
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
     * 判断输入助教对象的工号是否与数据库中已有的有冲突
     *
     * @param assistant 要判断的助教
     * @return 工号是否冲突
     */
    boolean isRepeatedAssistantWorkId(Assistant assistant);

    /**
     * 判断输入助教对象的姓名是否与数据库中已有的有冲突
     *
     * @param assistant 要判断的助教
     * @return 姓名是否冲突
     */
    boolean isRepeatedAssistantName(Assistant assistant);

    /**
     * 根据助教id查询出助教信息
     *
     * @param id 助教id
     * @return 对应助教
     */
    Assistant getAssistantById(Long id);

    /**
     * 根据助教工号查询出助教信息
     *
     * @param assistantWorkId 助教工号
     * @return 对应助教
     */
    Assistant getAssistantByWorkId(String assistantWorkId);

    /**
     * 根据助教姓名查询出助教信息
     *
     * @param assistantName 助教姓名
     * @return 对应助教
     */
    Assistant getAssistantByName(String assistantName);

    /**
     * 根据助教校区查询出助教信息
     *
     * @param campus 助教校区
     * @return 指定校区的全部助教
     */
    List<Assistant> listAssistantsByCampus(String campus);

    /**
     * 根据开课的年份季度分期和助教校区查询出助教信息。如果某入参为空，该字段不作为sql查询约束
     *
     * @param classSeasonDto 开课的年份季度分期
     * @param campus 助教校区
     * @return 指定开课的年份季度分期和校区的全部助教
     */
    List<Assistant> listAssistantsByClassSeasonAndCampus(ClassSeasonDto classSeasonDto, String campus);

    /**
     * 添加一个助教
     *
     * @param assistant 新添加助教的信息
     * @return (更新结果，更新记录数)
     * 1."failure"：错误入参等异常
     * 2."workIdRepeat"：工号冲突
     * 3."nameRepeat"：姓名冲突
     * 4."success": 更新成功
     */
    UpdateResult insertOneAssistant(Assistant assistant);

    /**
     * 修改助教信息由id修改
     *
     * @param assistant 修改后的助教信息
     * @return 1."failure"：错误入参等异常
     * 2."workIdRepeat"：工号冲突
     * 3."nameRepeat"：姓名冲突
     * 4."unchanged": 对比数据库原记录未做任何修改
     * 5."success": 更新成功
     */
    String updateAssistantInfo(Assistant assistant);

    /**
     * 修改助教信息由工号修改
     *
     * @param assistant 修改后的助教信息
     * @return (更新结果，更新记录数)
     * 1."failure"：错误入参等异常
     * 2."nameRepeat"：姓名冲突
     * 3."success": 更新成功
     */
    UpdateResult updateAssistantByWorkId(Assistant assistant);


    /**
     * 修改助教信息由工号修改
     *
     * @param originalAssistant 原来的助教信息
     * @param newAssistant      新的助教信息
     * @return (更新结果，更新记录数)
     * 1."failure"：错误入参等异常
     * 2."nameRepeat"：姓名冲突
     * 3."success": 更新成功
     */
    UpdateResult updateAssistantByWorkId(Assistant originalAssistant, Assistant newAssistant);

    /**
     * 根据从excel中读取到的assistant信息，更新插入多个。根据工号判断：
     * if 当前工号不存在
     * 执行插入。
     * else
     * 根据工号更新。
     * 这里对于非法的入参采取抛出异常的方式，而不是返回"failure"，这是便于控制层捕获做进一步地异常处理
     *
     * @param assistants 输入的助教集合
     * @return (更新结果，更新记录数)
     */
    DefaultFromExcelUpdateResult insertAndUpdateAssistantsFromExcel(List<Assistant> assistants);

    /**
     * 返回符合条件的助教信息分页结果
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果集
     */
    PageInfo<Assistant> listAssistants(MyPage myPage, AssistantSearchCondition condition);

    /**
     * 根据id删除一个助教
     *
     * @param id 被删除助教的id
     * @return 更新记录数
     */
    long deleteOneAssistantById(Long id);

    /**
     * 根据id的列表删除多个助教
     *
     * @param ids 助教id的列表
     * @return 更新记录数
     */
    long deleteManyAssistantsByIds(List<Long> ids);

    /**
     * 根据输入条件删除指定的助教
     *
     * @param condition 输入条件封装
     * @return 更新记录数
     */
    long deleteAssistantsByCondition(AssistantSearchCondition condition);

}
