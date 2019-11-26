package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.util.ClassUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.ClassDetailedDto;
import com.jzy.model.dto.ClassSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.entity.Class;
import com.jzy.model.entity.User;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import com.jzy.model.excel.input.ClassArrangementExcel;
import com.jzy.model.vo.ResultMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ClassAdminController
 * @Author JinZhiyun
 * @Description 班级管理的控制器
 * @Date 2019/11/24 13:11
 * @Version 1.0
 **/
@Controller
@RequestMapping("/class/admin")
public class ClassAdminController extends AbstractController{
    private final static Logger logger = Logger.getLogger(ClassAdminController.class);

    /**
     * 导入排班表
     *
     * @param file         上传的文件
     * @param parseClassId 是否开启自动解析的开关
     * @param clazz        开班年份、校区、季度等信息的封装
     * @return
     */
    @RequestMapping("/import")
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("parseClassId") String parseClassId, Class clazz) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);
        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map2.put("src", "");
        map.put("data", map2);

        if (clazz == null || !ClassUtils.isValidClassYear(clazz.getClassYear())) {
            map.put("msg", "yearInvalid");
            return map;
        }

        if (!ClassUtils.isValidClassSeason(clazz.getClassSeason()) || !ClassUtils.isValidClassCampus(clazz.getClassCampus())) {
            map.put("msg", Constants.FAILURE);
            return map;
        }

        if (file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }


        if (!Excel.isExcel(file.getOriginalFilename())) {
            String msg = "上传文件不是excel";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }


        ClassArrangementExcel excel = null;
        try {
            excel = new ClassArrangementExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
            excel.readClassDetailFromExcel();
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        }

        try {
            teacherService.insertAndUpdateTeachersFromExcel(new ArrayList<>(excel.getTeachers()));

            List<ClassDetailedDto> classDetailedDtos=excel.getClassDetailedDtos();
            for (ClassDetailedDto classDetailedDto:classDetailedDtos) {
                classDetailedDto.setClassYear(clazz.getClassYear());
                if (!StringUtils.isEmpty(clazz.getClassSeason())){
                    classDetailedDto.setClassSeason(clazz.getClassSeason());
                }
                if (!StringUtils.isEmpty(clazz.getClassCampus())){
                    classDetailedDto.setClassCampus(clazz.getClassCampus());
                }
            }
            classService.insertAndUpdateClassesFromExcel(classDetailedDtos);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        }


        map.put("msg", Constants.SUCCESS);
        return map;
    }

    /**
     * 跳转班级管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute(ModelConstants.CURRENT_YEAR_MODEL_KEY, ClassUtils.getCurrentYear());
        model.addAttribute(ModelConstants.CURRENT_SEASON_MODEL_KEY, ClassUtils.getCurrentSeason());

        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(ClassUtils.SEASONS));
        model.addAttribute(ModelConstants.CLASS_IDS_MODEL_KEY, JSON.toJSONString(classService.listAllClassIds()));
        model.addAttribute(ModelConstants.GRADES_MODEL_KEY, JSON.toJSONString(ClassUtils.GRADES));
        model.addAttribute(ModelConstants.SUBJECTS_MODEL_KEY, JSON.toJSONString(ClassUtils.SUBJECTS));
        model.addAttribute(ModelConstants.TYPES_MODEL_KEY, JSON.toJSONString(ClassUtils.TYPES));
        return "class/admin/page";
    }

    /**
     * 查询班级信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getClassInfo")
    @ResponseBody
    public ResultMap<List<ClassDetailedDto>> getTeacherInfo(MyPage myPage, ClassSearchCondition condition) {
        PageInfo<ClassDetailedDto> pageInfo = classService.listClasses(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 查询助教自己的班级信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getOwnClassInfoByAssistant")
    @ResponseBody
    public ResultMap<List<ClassDetailedDto>> getOwnClassInfoByAssistant(MyPage myPage, ClassSearchCondition condition) {
        User user=userService.getSessionUserInfo();
        condition.setAssistantWorkId(user.getUserWorkId());
        PageInfo<ClassDetailedDto> pageInfo = classService.listClasses(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 重定向到编辑班级权限iframe子页面并返回相应model
     *
     * @param model
     * @param clazz 当前要被编辑的班级信息
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm(Model model, Class clazz) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(ClassUtils.SEASONS));
        model.addAttribute(ModelConstants.GRADES_MODEL_KEY, JSON.toJSONString(ClassUtils.GRADES));
        model.addAttribute(ModelConstants.SUBJECTS_MODEL_KEY, JSON.toJSONString(ClassUtils.SUBJECTS));
        model.addAttribute(ModelConstants.TYPES_MODEL_KEY, JSON.toJSONString(ClassUtils.TYPES));
        model.addAttribute(ModelConstants.TYPES_MODEL_KEY, JSON.toJSONString(ClassUtils.TYPES));

        model.addAttribute(ModelConstants.CLASS_EDIT_MODEL_KEY, clazz);
        return "class/admin/classForm";
    }
}
