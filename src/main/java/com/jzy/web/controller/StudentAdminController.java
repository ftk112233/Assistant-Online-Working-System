package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.util.ClassUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.dto.StudentAndClassSearchCondition;
import com.jzy.model.dto.StudentSearchCondition;
import com.jzy.model.entity.Student;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import com.jzy.model.excel.input.StudentListExcel;
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


        StudentListExcel excel = null;

        if (type != null) {
            if (type.equals(1)) {
                try {
                    excel = new StudentListExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
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
                    excel = new StudentListExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
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

}
