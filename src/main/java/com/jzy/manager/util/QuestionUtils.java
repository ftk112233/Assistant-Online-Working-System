package com.jzy.manager.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName QuestionUtils
 * @description 通过问题免密登录工具类
 * @date 2019/11/17 18:06
 **/
public class QuestionUtils {
    private QuestionUtils(){}

    /**
     * 问题总数
     */
    public static final int QUESTION_COUNT = 4;

    /**
     * 所有问题，键的顺序问题和答案相同
     */
    public static final Map<Integer, String> QUESTIONS=new HashMap<>(QUESTION_COUNT);

    /**
     * 所有答案，键的顺序问题和答案相同
     */
    public static final Map<Integer, String> ANSWERS=new HashMap<>(QUESTION_COUNT);

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
    public static String getQuestionById(Integer id){
        return QUESTIONS.get(id);
    }

    /**
     * 根据问题的键返回答案
     *
     * @param id 问题的键
     * @return
     */
    public static String getAnswerById(Integer id){
        return ANSWERS.get(id);
    }

    /**
     * 根据问题的键和输入的答案判断问题是否正确
     *
     * @param id 问题的键
     * @param inputAnswer 输入的答案
     * @return
     */
    public static boolean isValidAnswer(Integer id , String inputAnswer){
        if (id.equals(1)){
            if (ANSWERS.get(id).equals(inputAnswer) || "初音".equals(inputAnswer)){
                return true;
            }
        }

        if (id.equals(3)){
            if (ANSWERS.get(id).equals(inputAnswer) || "精进".equals(inputAnswer)){
                return true;
            }
        }

        return ANSWERS.get(id).equals(inputAnswer);
    }
}
