package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.exception.NoMoreQuestionsException;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.QuestionSearchCondition;
import com.jzy.model.dto.QuestionWithCreatorDto;
import com.jzy.model.entity.Question;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName QuestionService
 * @description 游客登录问题业务
 * @date 2019/12/3 14:46
 **/
public interface QuestionService {
    /**
     * 根据id查询问题
     *
     * @param id 主键id
     * @return
     */
    Question getQuestionById(Long id);

    /**
     * 根据问题内容查询问题
     *
     * @param content 问题内容
     * @return
     */
    Question getQuestionByContent(String content);

    /**
     * 查出所有问题
     *
     * @return
     */
    List<Question> listAllQuestions();

    /**
     * 查出所有问题个数
     *
     * @return
     */
    long countAllQuestions();

    /**
     * 获得默认问题问题
     *
     * @return
     */
    Question getDefaultQuestion();

    /**
     * 获得数据库中随机地一个问题
     *
     * @return
     */
    Question getRandomQuestion();

    /**
     * 获得数据库中随机地一个问题，前该问题与输入问题不同
     *
     * @param currentQuestion 输入问题对象
     * @return
     */
    Question getRandomDifferentQuestion(Question currentQuestion) throws NoMoreQuestionsException;

    /**
     * 获得数据库中随机地一个问题，前该问题与输入问题不同
     *
     * @param currentQuestionContent 输入问题内容
     * @return
     */
    Question getRandomDifferentQuestion(String currentQuestionContent) throws NoMoreQuestionsException;

    /**
     * 判断当前问题的输入答案是否正确
     *
     * @param questionContent 问题内容
     * @param answerInput 输入的答案
     * @return
     */
    boolean isCorrectAnswer(String questionContent, String answerInput) throws InvalidParameterException;

    /**
     * 查询登录问题信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    PageInfo<QuestionWithCreatorDto> listQuestions(MyPage myPage, QuestionSearchCondition condition);

    /**
     * 修改登录问题，由id修改
     *
     * @param question 修改后的问题信息
     * @return
     */
    String updateQuestionInfo(Question question);

    /**
     * 添加问题
     *
     * @param question 添加问题的封装
     * @return
     */
    String insertQuestion(Question question);

    /**
     * 删除一个问题
     *
     * @param id 被删除问题限的id
     * @return
     */
    String deleteOneQuestionById(Long id);

    /**
     * 删除多个问题
     *
     * @param ids 多个问题id列表
     * @return
     */
    String deleteManyQuestionsByIds(List<Long> ids);
}
