package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.util.StudentUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.StudentSearchCondition;
import com.jzy.model.entity.Student;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import com.jzy.model.excel.input.StudentListImportToDatabaseExcel;
import com.jzy.model.vo.ResultMap;
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
 * @ClassName StudentAdminController
 * @Author JinZhiyun
 * @Description /学生管理的控制器
 * @Date 2019/11/24 15:15
 * @Version 1.0
 **/
@Controller
@RequestMapping("/student/admin")
public class StudentAdminController extends AbstractController {
    private final static Logger logger = Logger.getLogger(StudentAdminController.class);

    /**
     * 导入排班表
     *
     * @param file         上传的文件
     * @param type         选项：
     *                     1: 导入学生和上课信息
     *                     2: 导入学生详细信息带手机号
     * @return
     */
    @RequestMapping("/import")
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "type") Integer type) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);
        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map2.put("src", "");
        map.put("data", map2);


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


        StudentListImportToDatabaseExcel excel = null;

        if (type != null) {
            if (type.equals(1)) {
                try {
                    excel = new StudentListImportToDatabaseExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
                    excel.readStudentAndClassInfoFromExcel();
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }

                try {
                    studentService.insertAndUpdateStudentsFromExcel(new ArrayList<>(excel.getStudents()));

                    studentAndClassService.insertAndUpdateStudentAndClassesFromExcel(excel.getStudentAndClassDetailedDtos());
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            } else if (type.equals(2)) {
                try {
                    excel = new StudentListImportToDatabaseExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
                    excel.readStudentDetailInfoFromExcel();
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }

                try {
                    studentService.insertAndUpdateStudentsDetailedFromExcel(new ArrayList<>(excel.getStudents()));

                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            }
            map.put("msg", Constants.SUCCESS);
        }

        return map;
    }

    /**
     * 跳转学员个人信息管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page() {
        return "student/personal/admin/page";
    }


    /**
     * 查询学员个人信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getStudentInfo")
    @ResponseBody
    public ResultMap<List<Student>> getStudentInfo(MyPage myPage, StudentSearchCondition condition) {
        PageInfo<Student> pageInfo = studentService.listStudents(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 重定向到编辑学生iframe子页面并返回相应model
     *
     * @param model
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm(Model model) {
        return "student/personal/admin/studentForm";
    }

    /**
     * 学生管理中的编辑学生请求，由id修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(Student student) {
        Map<String, Object> map = new HashMap<>(1);

        if (!StudentUtils.isValidStudentUpdateInfo(student)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", studentService.updateStudentInfo(student));

        return map;
    }

    /**
     * 学生管理中的添加学生请求
     *
     * @param student 新添加学生的信息
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(Student student) {
        Map<String, Object> map = new HashMap<>(1);

        if (!StudentUtils.isValidStudentUpdateInfo(student)) {
            String msg = "insert方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", studentService.insertStudent(student));

        return map;
    }

    /**
     * 删除一个学生ajax交互
     *
     * @param id 被删除学生的id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public Map<String, Object> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> map = new HashMap(1);

        studentService.deleteOneStudentById(id);
        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 删除多个学生ajax交互
     *
     * @param students 多个学生的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("students") String students) {
        Map<String, Object> map = new HashMap(1);

        List<Student> studentsParsed = JSON.parseArray(students, Student.class);
        List<Long> ids = new ArrayList<>();
        for (Student student : studentsParsed) {
            ids.add(student.getId());
        }
        studentService.deleteManyStudentsByIds(ids);
        map.put("data", Constants.SUCCESS);
        return map;
    }
}
