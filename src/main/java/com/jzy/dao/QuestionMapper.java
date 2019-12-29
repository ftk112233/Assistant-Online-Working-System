package com.jzy.dao;

import com.jzy.model.dto.QuestionSearchCondition;
import com.jzy.model.dto.QuestionWithCreatorDto;
import com.jzy.model.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName QuestionMapper
 * @description 登陆问题业务dao接口
 * @date 2019/12/3 14:52
 **/
public interface QuestionMapper {
    /**
     * 根据id查询问题
     *
     * @param id 主键id
     * @return 对应问题对象
     */
    Question getQuestionById(@Param("id") Long id);

    /**
     * 根据问题内容查询问题
     *
     * @param content 问题内容
     * @return 对应问题对象
     */
    Question getQuestionByContent(@Param("content") String content);

    /**
     * 查出所有问题
     *
     * @return 所有问题的list
     */
    List<Question> listAllQuestions();

    /**
     * 查询登录问题信息
     *
     * @param condition 查询条件入参
     * @return 登录问题的详细信息
     */
    List<QuestionWithCreatorDto> listQuestions(QuestionSearchCondition condition);

    /**
     * 修改登录问题，由id修改
     *
     * @param question 修改后的问题信息
     * @return 更新记录数
     */
    long updateQuestionInfo(Question question);

    /**
     * 添加问题
     *
     * @param question 添加问题的封装
     * @return 更新记录数
     */
    long insertQuestion(Question question);

    /**
     * 查出所有问题个数
     *
     * @return 所有问题个数
     */
    long countAllQuestions();

    /**
     * 删除一个问题
     *
     * @param id 被删除问题限的id
     * @return 更新记录数
     */
    long deleteOneQuestionById(@Param("id") Long id);

    /**
     * 删除多个问题
     *
     * @param ids 多个问题id列表
     * @return 更新记录数
     */
    long deleteManyQuestionsByIds(List<Long> ids);
}
