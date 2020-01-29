package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.MissLessonStudentUtils;
import com.jzy.model.dto.MissLessonStudentDetailedDto;
import com.jzy.model.dto.MissLessonStudentSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.entity.User;
import com.jzy.model.vo.ResultMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MissLessonStudentController
 * @Author JinZhiyun
 * @Description 补课学生业务控制器
 * @Date 2019/11/21 22:07
 * @Version 1.0
 **/
@Controller
@RequestMapping("/missLessonStudent/admin")
public class MissLessonStudentAdminController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(MissLessonStudentAdminController.class);

    /**
     * 跳转学员上课信息管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page() {
        return "student/missLesson/admin/page";
    }

    /**
     * 查询补课学员信息的ajax交互。
     * 其中学员号、原班号、补课班号字段查询不分大小写，因此将该字段upperCase置为全部大写（数据库中班级编码统一为全部大写）后传给服务层
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getMissLessonStudentInfo")
    @ResponseBody
    public ResultMap<List<MissLessonStudentDetailedDto>> getMissLessonStudentInfo(MyPage myPage, MissLessonStudentSearchCondition condition) {
        condition.setStudentId(StringUtils.upperCase(condition.getStudentId()));
        condition.setOriginalClassId(StringUtils.upperCase(condition.getOriginalClassId()));
        condition.setCurrentClassId(StringUtils.upperCase(condition.getCurrentClassId()));
        PageInfo<MissLessonStudentDetailedDto> pageInfo = missLessonStudentService.listMissLessonStudents(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 查询我班上的要补课学员的信息的ajax交互。
     * 因此将查询条件中的原班助教工号置为当前会话用户的工号
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getMissLessonStudentInfoFromMyClass")
    @ResponseBody
    public ResultMap<List<MissLessonStudentDetailedDto>> getMissLessonStudentInfoFromMyClass(MyPage myPage, MissLessonStudentSearchCondition condition) {
        User user = userService.getSessionUserInfo();
        condition.setOriginalAssistantWorkId(user.getUserWorkId());
        PageInfo<MissLessonStudentDetailedDto> pageInfo = missLessonStudentService.listMissLessonStudents(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 查询补课到我班上的学员的信息的ajax交互
     * 因此将查询条件中的补课班助教工号置为当前会话用户的工号
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getMissLessonStudentInfoToMyClass")
    @ResponseBody
    public ResultMap<List<MissLessonStudentDetailedDto>> getMissLessonStudentInfoToMyClass(MyPage myPage, MissLessonStudentSearchCondition condition) {
        User user = userService.getSessionUserInfo();
        condition.setCurrentAssistantWorkId(user.getUserWorkId());
        PageInfo<MissLessonStudentDetailedDto> pageInfo = missLessonStudentService.listMissLessonStudents(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 重定向到编辑补课学生iframe子页面
     *
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm() {
//        model.addAttribute(ModelConstants.MISS_LESSON_STUDENT_EDIT_MODEL_KEY, missLessonStudentDetailedDto);
        return "student/missLesson/admin/missLessonStudentFormEdit";
    }

    /**
     * 重定向到编辑补课学生iframe子页面
     *
     * @return
     */
    @RequestMapping("/insertForm")
    public String insertForm() {
        return "student/missLesson/admin/missLessonStudentFormAdd";
    }

    /**
     * 补课学生管理中的编辑补课学生信息请求，由id修改
     *
     * @param missLessonStudentDetailedDto 修改后的补课学生信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(MissLessonStudentDetailedDto missLessonStudentDetailedDto) {
        Map<String, Object> map = new HashMap<>(1);

        if (!MissLessonStudentUtils.isValidMissLessonStudentUpdateInfo(missLessonStudentDetailedDto)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", missLessonStudentService.updateMissLessonStudentInfo(missLessonStudentDetailedDto));
        return map;
    }

    /**
     * 补课学生管理中的添加补课学生请求
     *
     * @param missLessonStudentDetailedDto 新添加补课学生
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(MissLessonStudentDetailedDto missLessonStudentDetailedDto) {
        Map<String, Object> map = new HashMap<>(1);

        if (!MissLessonStudentUtils.isValidMissLessonStudentUpdateInfo(missLessonStudentDetailedDto)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", missLessonStudentService.insertOneMissLessonStudent(missLessonStudentDetailedDto));
        return map;
    }

    /**
     * 删除一个补课学生ajax交互
     *
     * @param id 被删除补课学生的id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public Map<String, Object> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> map = new HashMap(1);

        missLessonStudentService.deleteOneMissLessonStudentById(id);
        map.put("data", SUCCESS);
        return map;
    }

    /**
     * 删除多个补课学生记录ajax交互
     *
     * @param missLessonStudents 多个补课学生的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("missLessonStudents") String missLessonStudents) {
        Map<String, Object> map = new HashMap(1);

        List<MissLessonStudentDetailedDto> missLessonStudentsParsed = JSON.parseArray(missLessonStudents, MissLessonStudentDetailedDto.class);
        List<Long> ids = new ArrayList<>();
        for (MissLessonStudentDetailedDto dto : missLessonStudentsParsed) {
            ids.add(dto.getId());
        }
        missLessonStudentService.deleteManyMissLessonStudentsByIds(ids);
        map.put("data", SUCCESS);
        return map;
    }

    /**
     * 条件删除多个学生上课记录ajax交互
     *
     * @param condition 输入的查询条件
     * @return
     */
    @RequestMapping("/deleteByCondition")
    @ResponseBody
    public Map<String, Object> deleteByCondition(MissLessonStudentSearchCondition condition) {
        Map<String, Object> map = new HashMap(1);
        missLessonStudentService.deleteMissLessonStudentsByCondition(condition);
        map.put("data", SUCCESS);
        return map;
    }
}
