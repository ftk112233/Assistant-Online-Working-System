package com.jzy.manager.util;

import com.jzy.model.entity.Question;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName QuestionUtils
 * @description 登录问题的工具类 {@link com.jzy.model.entity.Question}
 * 增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * 现改用数据库存储所有问题信息，便于修改，舍弃静态Map的方式
 * @date 2019/11/17 18:06
 **/
public class QuestionUtils {
    private QuestionUtils() {
    }

    /**
     * 永真答案
     */
    private static final String ALWAYS_TRUE_ANSWER = Question.ALWAYS_TRUE_ANSWER;

    /**
     * 问题总数
     */
    private static final int QUESTION_COUNT = 4;

    /**
     * 所有问题，键的顺序问题和答案相同
     */
    private static final Map<Integer, String> QUESTIONS = new HashMap<>(QUESTION_COUNT);

    /**
     * 所有答案，键的顺序问题和答案相同
     */
    private static final Map<Integer, String> ANSWERS = new HashMap<>(QUESTION_COUNT);

    static {
        QUESTIONS.put(1, "金爷爷最喜欢的二次元角色是?");
        //答案输入初音也可以
        ANSWERS.put(1, "初音未来");

        QUESTIONS.put(2, "优能中学助教团队的工作目标是\"续班\"、\"扩科\"和_________?");
        ANSWERS.put(2, "转介绍");

        QUESTIONS.put(3, "优能中学班级类型中比“志高班”难度低一档的班型是什么？");
        //答案输入精进也可以
        ANSWERS.put(3, "精进班");

        QUESTIONS.put(4, "在开班初期，助教往往要对第一节课学生上课反馈做整理，完成\"首课回访\"；而在续班窗口期，" +
                "助教的一个十分重要的工作是通过打家长电话等方式完成\"_____回访\"？");
        ANSWERS.put(4, "中期");
    }

    /**
     * 根据问题的键返回值，即问题具体内容
     *
     * @param id 问题的键
     * @return
     */
    @Deprecated
    public static String getQuestionById(Integer id) {
        return QUESTIONS.get(id);
    }

    /**
     * 根据问题的键返回答案
     *
     * @param id 问题的键
     * @return
     */
    @Deprecated
    public static String getAnswerById(Integer id) {
        return ANSWERS.get(id);
    }

    /**
     * 根据问题的键和输入的答案判断问题是否正确
     *
     * @param id          问题的键
     * @param inputAnswer 输入的答案
     * @return
     */
    @Deprecated
    public static boolean isCorrectAnswer(Integer id, String inputAnswer) {
        if (isAlwaysTrueAnswer(inputAnswer)) {
            return true;
        }

        if (id.equals(1)) {
            if (ANSWERS.get(id).equals(inputAnswer) || "初音".equals(inputAnswer)) {
                return true;
            }
        }

        if (id.equals(3)) {
            if (ANSWERS.get(id).equals(inputAnswer) || "精进".equals(inputAnswer)) {
                return true;
            }
        }

        return ANSWERS.get(id).equals(inputAnswer);
    }

    /**
     * 是否等于万能答案
     *
     * @param inputAnswer 输入的答案
     * @return
     */
    public static boolean isAlwaysTrueAnswer(String inputAnswer) {
        return ALWAYS_TRUE_ANSWER.equals(inputAnswer);
    }

    /**
     * 得到默认问题
     *
     * @return 问题对象
     */
    public static Question getDefaultQuestion() {
        Integer id = 1;
        return new Question(QUESTIONS.get(id), ANSWERS.get(id));
    }

    /**
     * 默认问题的答案是否正确
     *
     * @param inputAnswer 输入的答案
     * @return
     */
    public static boolean isCorrectAnswerOfDefaultQuestion(String inputAnswer) {
        if (isAlwaysTrueAnswer(inputAnswer)) {
            return true;
        }

        if (ANSWERS.get(1).equals(inputAnswer) || "初音".equals(inputAnswer)) {
            return true;
        }
        return false;
    }


    public static boolean isValidContent(String content) {
        return !StringUtils.isEmpty(content) && content.length() <= 500;
    }

    /**
     * 万能答案不校验，不更新，永远使用数据库默认值
     *
     * @param answer
     * @return
     */
    public static boolean isValidTrueAnswer(String answer) {
        return true;
    }

    public static boolean isValidAnswer(String answer) {
        return !StringUtils.isEmpty(answer) && answer.length() <= 100;
    }

    public static boolean isValidAnswer2(String answer2) {
        return StringUtils.isEmpty(answer2) || answer2.length() <= 100;
    }

    public static boolean isValidCreatorId(Long creator) {
        return true;
    }

    public static boolean isValidRemark(String remark) {
        return remark == null || remark.length() <= 500;
    }

    /**
     * question是否合法
     *
     * @param question 输入的question对象
     * @return
     */
    public static boolean isValidQuestionInfo(Question question) {
        return question != null && isValidContent(question.getContent()) && isValidTrueAnswer(question.getTrueAnswer()) && isValidAnswer(question.getAnswer())
                && isValidAnswer2(question.getAnswer2()) && isValidCreatorId(question.getCreatorId())
                && isValidRemark(question.getRemark());
    }

    /**
     * question是否合法
     *
     * @param question 输入的question对象
     * @return
     */
    public static boolean isValidQuestionUpdateInfo(Question question) {
        return isValidQuestionInfo(question);
    }
}
