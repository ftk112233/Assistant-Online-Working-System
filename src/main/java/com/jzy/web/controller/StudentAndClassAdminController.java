package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.util.ClassUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.*;
import com.jzy.model.vo.ResultMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName StudentAndClassAdminController
 * @Author JinZhiyun
 * @Description 学生上课情况管理控制器
 * @Date 2019/11/25 17:54
 * @Version 1.0
 **/
@Controller
@RequestMapping("/studentAndClass/admin")
public class StudentAndClassAdminController extends AbstractController {
    /**
     * 跳转班级管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model, @RequestParam(value = "classId", required = false) String classId) {
        model.addAttribute(ModelConstants.CURRENT_YEAR_MODEL_KEY, ClassUtils.getCurrentYear());
        model.addAttribute(ModelConstants.CURRENT_SEASON_MODEL_KEY, ClassUtils.getCurrentSeason());

        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(ClassUtils.SEASONS));
        model.addAttribute(ModelConstants.CLASS_IDS_MODEL_KEY, JSON.toJSONString(classService.listAllClassIds()));
        model.addAttribute(ModelConstants.GRADES_MODEL_KEY, JSON.toJSONString(ClassUtils.GRADES));
        model.addAttribute(ModelConstants.SUBJECTS_MODEL_KEY, JSON.toJSONString(ClassUtils.SUBJECTS));
        model.addAttribute(ModelConstants.TYPES_MODEL_KEY, JSON.toJSONString(ClassUtils.TYPES));

        model.addAttribute(ModelConstants.CLASS_ID_MODEL_KEY, classId);
        return "student/sc/admin/page";
    }

    /**
     * 查询学员上课信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getStudentAndClassInfo")
    @ResponseBody
    public ResultMap<List<StudentAndClassDetailedDto>> getTeacherInfo(MyPage myPage, StudentAndClassSearchCondition condition) {
        PageInfo<StudentAndClassDetailedDto> pageInfo = studentAndClassService.listStudentAndClasses(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }
}
