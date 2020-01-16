package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.QuestionUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.QuestionSearchCondition;
import com.jzy.model.dto.QuestionWithCreatorDto;
import com.jzy.model.entity.Question;
import com.jzy.model.vo.ResultMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName QuestionAdminController
 * @description 登录问题的控制器
 * @date 2019/12/3 16:47
 **/
@Controller
@RequestMapping("/question/admin")
public class QuestionAdminController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(QuestionAdminController.class);

    /**
     * 登录问题管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page() {
        return "question/admin/page";
    }

    /**
     * 查询登录问题信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getQuestionInfo")
    @ResponseBody
    public ResultMap<List<QuestionWithCreatorDto>> getQuestionInfo(MyPage myPage, QuestionSearchCondition condition) {
        PageInfo<QuestionWithCreatorDto> pageInfo = questionService.listQuestions(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 重定向到编辑登录问题iframe子页面并返回相应model
     *
     * @param model
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm(Model model) {
        return "question/admin/questionFormEdit";
    }

    /**
     * 登录问题管理中的编辑请求，由id修改
     *
     * @param question 修改后的问题信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(Question question) {
        Map<String, Object> map = new HashMap<>(1);

        if (!QuestionUtils.isValidQuestionUpdateInfo(question)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", questionService.updateQuestionInfo(question));

        return map;
    }

    /**
     * 重定向到添加问题iframe子页面并返回相应model
     *
     * @param model
     * @return
     */
    @RequestMapping("/insertForm")
    public String insertForm(Model model) {
        return "question/admin/questionFormAdd";
    }

    /**
     * 登录问题管理中的添加问题请求。需要是否匿名的判断处理
     *
     * @param anonymous 是否匿名
     * @param question  添加问题的封装
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(@RequestParam(value = "anonymous", required = false) String anonymous, Question question) {
        Map<String, Object> map = new HashMap<>(1);

        if (!QuestionUtils.isValidQuestionUpdateInfo(question)) {
            String msg = "insert方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        if (!Constants.ON.equals(anonymous)) {
            //不匿名
            question.setCreatorId(userService.getSessionUserInfo().getId());
        }

        map.put("data", questionService.insertOneQuestion(question));

        return map;
    }

    /**
     * 删除一个问题ajax交互
     *
     * @param id 被删除问题限的id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public Map<String, Object> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> map = new HashMap(1);

        map.put("data", questionService.deleteOneQuestionById(id).getResult());
        return map;
    }

    /**
     * 删除多个问题ajax交互
     *
     * @param questions 多个问题的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("questions") String questions) {
        Map<String, Object> map = new HashMap(1);

        List<Question> questionsParsed = JSON.parseArray(questions, Question.class);
        List<Long> ids = new ArrayList<>();
        for (Question question : questionsParsed) {
            ids.add(question.getId());
        }

        map.put("data", questionService.deleteManyQuestionsByIds(ids).getResult());
        return map;
    }
}
