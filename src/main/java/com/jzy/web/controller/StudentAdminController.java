package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InputFileTypeException;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.StudentUtils;
import com.jzy.manager.util.UserMessageUtils;
import com.jzy.model.dto.*;
import com.jzy.model.entity.Assistant;
import com.jzy.model.entity.Class;
import com.jzy.model.entity.Student;
import com.jzy.model.entity.UserMessage;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import com.jzy.model.excel.input.StudentListImportToDatabaseExcel;
import com.jzy.model.excel.input.StudentSchoolExcel;
import com.jzy.model.vo.ResultMap;
import com.jzy.model.vo.Speed;
import com.jzy.model.vo.SqlProceedSpeed;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    private final static Logger logger = LogManager.getLogger(StudentAdminController.class);

    /**
     * 导入排班表
     *
     * @param file 上传的文件
     * @param type 选项：
     *             1: 导入学生和上课信息
     *             2: 导入学生详细信息带手机号
     * @return
     */
    @RequestMapping("/import")
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "deleteFirst",required = false) boolean deleteFirstChecked
                                           ,@RequestParam(value = "type") Integer type) throws InvalidParameterException {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>();
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

        long startTime = System.currentTimeMillis();   //获取开始时间
        int excelEffectiveDataRowCount = 0; //表格有效数据行数
        int databaseUpdateRowCount = 0; //数据库更新记录数
        int databaseInsertRowCount = 0; //数据库插入记录数
        int databaseDeleteRowCount = 0; //数据库删除记录数

        if (type != null) {
            StudentListImportToDatabaseExcel excel = null;

            if (type.equals(1)) {
                try {
                    excel = new StudentListImportToDatabaseExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
                    excelEffectiveDataRowCount=excel.readStudentAndClassInfoFromExcel();
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                } catch (ExcelColumnNotFoundException e) {
                    e.printStackTrace();
                    map.put("msg", "excelColumnNotFound");
                    return map;
                } catch (InputFileTypeException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }

                try {
                    UpdateResult studentResult=studentService.insertAndUpdateStudentsFromExcel(new ArrayList<>(excel.getStudents()));
                    databaseInsertRowCount += (int) studentResult.getInsertCount();
                    databaseUpdateRowCount += (int) studentResult.getUpdateCount();

                    if (deleteFirstChecked) {
                        //如果开启先导后删
                        if (excel.getStudentAndClassDetailedDtos().size() > 0) {
                            StudentAndClassDetailedDto dto = excel.getStudentAndClassDetailedDtos().get(0);
                            Class clazz=classService.getClassByClassId(dto.getClassId());
                            StudentAndClassSearchCondition condition = new StudentAndClassSearchCondition();
                            condition.setClassYear(clazz.getClassYear());
                            condition.setClassSeason(clazz.getClassSeason());
                            condition.setClassSubSeason(clazz.getClassSubSeason());
                            condition.setClassCampus(clazz.getClassCampus());
                            databaseDeleteRowCount += (int) studentAndClassService.deleteStudentAndClassesByCondition(condition).getDeleteCount();
                        }
                    }

                    UpdateResult studentAndClassResult=studentAndClassService.insertAndUpdateStudentAndClassesFromExcel(excel.getStudentAndClassDetailedDtos());
                    databaseInsertRowCount += (int) studentAndClassResult.getInsertCount();
                    databaseUpdateRowCount += (int) studentAndClassResult.getUpdateCount();
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            } else if (type.equals(2)) {
                try {
                    excel = new StudentListImportToDatabaseExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
                    excelEffectiveDataRowCount=excel.readStudentDetailInfoFromExcel();
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                } catch (ExcelColumnNotFoundException e) {
                    e.printStackTrace();
                    map.put("msg", "excelColumnNotFound");
                    return map;
                } catch (InputFileTypeException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }

                try {
                    UpdateResult studentResult=studentService.insertAndUpdateStudentsDetailedFromExcel(new ArrayList<>(excel.getStudents()));
                    databaseInsertRowCount += (int) studentResult.getInsertCount();
                    databaseUpdateRowCount += (int) studentResult.getUpdateCount();

                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            }

            long endTime = System.currentTimeMillis(); //获取结束时间
            Speed speedOfExcelImport = new Speed(excelEffectiveDataRowCount, endTime - startTime);
            SqlProceedSpeed speedOfDatabaseImport = new SqlProceedSpeed(databaseUpdateRowCount, databaseInsertRowCount, databaseDeleteRowCount, endTime - startTime);
            speedOfExcelImport.parseSpeed();
            speedOfDatabaseImport.parseSpeed();
            map.put("excelSpeed",speedOfExcelImport);
            map.put("databaseSpeed",speedOfDatabaseImport);

            map.put("msg", Constants.SUCCESS);

            //向对应校区的用户发送通知消息
            if (excel !=null && excel.getStudentAndClassDetailedDtos().size() > 0) {
                StudentAndClassDetailedDto dto = excel.getStudentAndClassDetailedDtos().get(0);
                Class clazz = classService.getClassByClassId(dto.getClassId());
                clazz.setParsedClassYear();
                String campus = clazz.getClassCampus();
                if (!StringUtils.isEmpty(campus)) {
                    List<Assistant> assistants = assistantService.listAssistantsByCampus(campus);
                    List<Long> userIds = new ArrayList<>();
                    for (Assistant assistant : assistants) {
                        userIds.add(userService.getUserByWorkId(assistant.getAssistantWorkId()).getId());
                    }

                    for (Long userId : userIds) {
                        UserMessage message = new UserMessage();
                        message.setUserId(userId);
                        message.setUserFromId(userService.getSessionUserInfo().getId());
                        message.setMessageTitle("学员上课信息有变化");
                        StringBuffer messageContent = new StringBuffer();
                        messageContent.append("你的学管老师刚刚更新了" + clazz.getClassCampus() + "校区" + clazz.getClassYear() + "年" + clazz.getClassSeason() + "的学员上课信息。")
                                .append("<br>点<a lay-href='/studentAndClass/admin/page' lay-text='上课信息'>这里</a>前往查看。");
                        message.setMessageContent(messageContent.toString());
                        message.setMessageTime(new Date());
                        if (UserMessageUtils.isValidUserMessageUpdateInfo(message)) {
                            userMessageService.insertUserMessage(message);
                        }
                    }
                }
            }
        }

        return map;
    }

    /**
     * 导入学校统计
     *
     * @param file
     * @return
     * @throws InvalidParameterException
     */
    @RequestMapping("/importSchool")
    @ResponseBody
    public Map<String, Object> importSchool(@RequestParam(value = "file", required = false) MultipartFile file) throws InvalidParameterException {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>();
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

        long startTime = System.currentTimeMillis();   //获取开始时间
        int excelEffectiveDataRowCount = 0; //表格有效数据行数
        int databaseUpdateRowCount = 0; //数据库更新记录数
        int databaseInsertRowCount = 0; //数据库插入记录数
        int databaseDeleteRowCount = 0; //数据库删除记录数

        StudentSchoolExcel excel = null;
        try {
            excel = new StudentSchoolExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
            excelEffectiveDataRowCount = excel.readStudentsSchoolsFromExcel();
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        } catch (ExcelColumnNotFoundException e) {
            e.printStackTrace();
            map.put("msg", "excelColumnNotFound");
            return map;
        } catch (InputFileTypeException e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        }

        try {
            UpdateResult result = studentService.insertAndUpdateStudentsSchoolsFromExcel(excel.getStudents());
            databaseInsertRowCount += (int) result.getInsertCount();
            databaseUpdateRowCount += (int) result.getUpdateCount();

            long endTime = System.currentTimeMillis(); //获取结束时间
            Speed speedOfExcelImport = new Speed(excelEffectiveDataRowCount, endTime - startTime);
            SqlProceedSpeed speedOfDatabaseImport = new SqlProceedSpeed(databaseUpdateRowCount, databaseInsertRowCount, databaseDeleteRowCount, endTime - startTime);
            speedOfExcelImport.parseSpeed();
            speedOfDatabaseImport.parseSpeed();
            map.put("excelSpeed", speedOfExcelImport);
            map.put("databaseSpeed", speedOfDatabaseImport);

            map.put("msg", Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
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
        condition.setStudentId(condition.getStudentId() == null ? null : condition.getStudentId().toUpperCase());
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
    public Map<String, Object> updateById(Student student) throws InvalidParameterException {
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
    public Map<String, Object> insert(Student student) throws InvalidParameterException {
        Map<String, Object> map = new HashMap<>(1);

        if (!StudentUtils.isValidStudentUpdateInfo(student)) {
            String msg = "insert方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", studentService.insertStudent(student).getResult());

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

    /**
     * 条件删除多个学生ajax交互
     *
     * @param condition 输入的查询条件
     * @return
     */
    @RequestMapping("/deleteByCondition")
    @ResponseBody
    public Map<String, Object> deleteByCondition(StudentSearchCondition condition) {
        Map<String, Object> map = new HashMap(1);
        map.put("data", studentService.deleteStudentsByCondition(condition));
        return map;
    }
}
