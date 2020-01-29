package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.NoMoreQuestionsException;
import com.jzy.manager.exception.QuestionNotExistException;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.QuestionSearchCondition;
import com.jzy.model.dto.QuestionWithCreatorDto;
import com.jzy.model.dto.UpdateResult;
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
     * 判断输入问题对象的内容是否与数据库中已有的有冲突
     *
     * @param question 要判断的问题
     * @return 内容是否冲突
     */
    boolean isRepeatedQuestionContent(Question question);

    /**
     * 根据id查询问题
     *
     * @param id 主键id
     * @return 对应问题
     */
    Question getQuestionById(Long id);

    /**
     * 根据问题内容查询问题
     *
     * @param content 问题内容
     * @return 对应问题
     */
    Question getQuestionByContent(String content);

    /**
     * 查出所有问题。全部问题的list要做redis缓存处理。
     *
     * @return 全部问题的list
     */
    List<Question> listAllQuestions();

    /**
     * 查出数据库中所有问题个数
     *
     * @return 问题个数
     */
    long countAllQuestions();

    /**
     * 获得默认问题问题
     *
     * @return 问题对象
     */
    Question getDefaultQuestion();

    /**
     * 获得随机的一个问题
     * 如果数据库中有，查出所有问题，随机选一个；
     * 否者返回默认的问题（设定是数据库中必须至少保留一个问题，这个else逻辑仅仅作为备用方案）
     *
     * @return 随机问题对象
     */
    Question getRandomQuestion();

    /**
     * 获得数据库中随机地一个问题，前该问题与输入问题不同
     *
     * @param currentQuestion 输入问题对象
     * @return 不同的随机问题
     * @throws NoMoreQuestionsException 除当前问题外没有更多问题的异常
     */
    Question getDifferentRandomQuestion(Question currentQuestion) throws NoMoreQuestionsException;

    /**
     * 获得数据库中随机地一个问题，且该问题内容与输入问题内容currentQuestionContent不同
     *
     * @param currentQuestionContent 输入问题内容
     * @return 不同的随机问题
     * @throws NoMoreQuestionsException 除当前问题外没有更多问题的异常
     */
    Question getDifferentRandomQuestion(String currentQuestionContent) throws NoMoreQuestionsException;

    /**
     * 判断当前问题的输入答案是否正确
     *
     * @param questionContent 问题内容
     * @param answerInput     输入的答案
     * @return 是否正确的布尔值
     * @throws QuestionNotExistException 不合法的问题内容questionContent，问题不存在
     */
    boolean isCorrectAnswer(String questionContent, String answerInput) throws QuestionNotExistException;

    /**
     * 是否是永真答案——“金爷nb”
     *
     * @param answerInput 输入的答案
     * @return 是否是永真答案
     */
    boolean isAlwaysTrueAnswer(String answerInput);

    /**
     * 查询登录问题信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<QuestionWithCreatorDto> listQuestions(MyPage myPage, QuestionSearchCondition condition);

    /**
     * 修改登录问题，由id修改
     *
     * @param question 修改后的问题信息
     * @return 1."failure"：错误入参等异常
     * 2."questionContentRepeat"：问题内容已存在
     * 3."unchanged": 对比数据库原记录未做任何修改
     * 4."success": 更新成功
     */
    String updateQuestionInfo(Question question);

    /**
     * 添加问题
     *
     * @param question 添加问题的封装
     * @return 1."failure"：错误入参等异常
     * 2."questionContentRepeat"：问题内容已存在
     * 3."success": 更新成功
     */
    String insertOneQuestion(Question question);

    /**
     * 删除一个问题
     *
     * @param id 被删除问题限的id
     * @return (更新结果, 更新记录数)
     * 1."atLeastOneQuestionNeeded"：不能完成删除，至少需要保留一个问题
     * 2."success": 更新成功
     */
    UpdateResult deleteOneQuestionById(Long id);

    /**
     * 删除多个问题
     *
     * @param ids 多个问题id列表
     * @return (更新结果, 更新记录数)
     * 1."atLeastOneQuestionNeeded"：不能完成删除，至少需要保留一个问题
     * 2."success": 更新成功
     */
    UpdateResult deleteManyQuestionsByIds(List<Long> ids);
}
