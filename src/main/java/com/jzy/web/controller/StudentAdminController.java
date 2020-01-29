package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.*;
import com.jzy.manager.util.ClassUtils;
import com.jzy.manager.util.StudentUtils;
import com.jzy.manager.util.UserMessageUtils;
import com.jzy.model.dto.*;
import com.jzy.model.entity.*;
import com.jzy.model.entity.Class;
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
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "deleteFirst", required = false) boolean deleteFirstChecked, @RequestParam(value = "manualDeleteFirstCondition", required = false) boolean manualDeleteFirstConditionChecked
            , @RequestParam(value = "type") Integer type, Class deleteCondition) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>();
        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map2.put("src", "");
        map.put("data", map2);


        if (file == null || file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidFileInputException(msg);
        }


        if (!Excel.isExcel(file.getOriginalFilename())) {
            String msg = "上传文件不是excel";
            logger.error(msg);
            throw new InvalidFileInputException(msg);
        }

        long startTime = System.currentTimeMillis();   //获取开始时间
        int excelEffectiveDataRowCount = 0; //表格有效数据行数
        int databaseUpdateRowCount = 0; //数据库更新记录数
        int databaseInsertRowCount = 0; //数据库插入记录数
        int databaseDeleteRowCount = 0; //数据库删除记录数

        if (type != null) {
            StudentListImportToDatabaseExcel excel = null;

            String msg = Constants.SUCCESS;
            DefaultFromExcelUpdateResult r = new DefaultFromExcelUpdateResult();
            if (type.equals(1)) {
                try {
                    excel = new StudentListImportToDatabaseExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));
                    excelEffectiveDataRowCount = excel.readStudentAndClassInfoFromExcel();
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                } catch (ExcelColumnNotFoundException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.EXCEL_COLUMN_NOT_FOUND);
                    map.put("whatWrong", e.getWhatWrong());
                    return map;
                } catch (InvalidFileTypeException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                } catch (ExcelTooManyRowsException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.EXCEL_TOO_MANY_ROWS);
                    map.put("rowCountThreshold", e.getRowCountThreshold());
                    map.put("actualRowCount", e.getActualRowCount());
                    return map;
                }

                try {
                    r = studentService.insertAndUpdateStudentsFromExcel(new ArrayList<>(excel.getStudents()));

                    if (deleteFirstChecked) {
                        //如果开启先导后删
                        StudentAndClassSearchCondition condition = new StudentAndClassSearchCondition();

                        if (manualDeleteFirstConditionChecked) {
                            //如果手动选择删除条件
                            if (deleteCondition == null || StringUtils.isEmpty(deleteCondition.getClassYear()) || !ClassUtils.isValidClassYear(deleteCondition.getClassYear())) {
                                map.put("msg", "yearInvalid");
                                return map;
                            }

                            if (StringUtils.isEmpty(deleteCondition.getClassSeason()) || !ClassUtils.isValidClassSeason(deleteCondition.getClassSeason())) {
                                map.put("msg", "seasonInvalid");
                                return map;
                            }

                            if (StringUtils.isEmpty(deleteCondition.getClassCampus()) || !ClassUtils.isValidClassCampus(deleteCondition.getClassCampus())) {
                                map.put("msg", "campusInvalid");
                                return map;
                            }

                            if (!ClassUtils.isValidClassSubSeason(deleteCondition.getClassSubSeason())) {
                                map.put("msg", Constants.FAILURE);
                                return map;
                            }

                            condition.setClassYear(deleteCondition.getClassYear());
                            condition.setClassSeason(deleteCondition.getClassSeason());
                            condition.setClassSubSeason(deleteCondition.getClassSubSeason());
                            condition.setClassCampus(deleteCondition.getClassCampus());
                        } else {
                            //如果不手动选择删除条件，默认读取表格中第一条记录中的班级所在的"年份-季度-分期-校区"，并删除其下的学生上课记录
                            if (excel.getStudentAndClassDetailedDtos().size() > 0) {
                                StudentAndClassDetailedDto dto = excel.getStudentAndClassDetailedDtos().get(0);
                                Class clazz = classService.getClassByClassId(dto.getClassId());

                                condition.setClassYear(clazz.getClassYear());
                                condition.setClassSeason(clazz.getClassSeason());
                                condition.setClassSubSeason(clazz.getClassSubSeason());
                                condition.setClassCampus(clazz.getClassCampus());
                            } else {
                                condition = null;
                            }
                        }

                        databaseDeleteRowCount += (int) studentAndClassService.deleteStudentAndClassesByCondition(condition).getDeleteCount();
                    }

                    DefaultFromExcelUpdateResult studentAndClassResult = studentAndClassService.insertAndUpdateStudentAndClassesFromExcel(excel.getStudentAndClassDetailedDtos());
                    r.merge(studentAndClassResult);

                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            } else if (type.equals(2)) {
                try {
                    excel = new StudentListImportToDatabaseExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));
                    excelEffectiveDataRowCount = excel.readStudentDetailInfoFromExcel();
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                } catch (ExcelColumnNotFoundException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.EXCEL_COLUMN_NOT_FOUND);
                    map.put("whatWrong", e.getWhatWrong());
                    return map;
                } catch (InvalidFileTypeException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                } catch (ExcelTooManyRowsException e) {
                    e.printStackTrace();
                    map.put("msg", Constants.EXCEL_TOO_MANY_ROWS);
                    map.put("rowCountThreshold", e.getRowCountThreshold());
                    map.put("actualRowCount", e.getActualRowCount());
                    return map;
                }

                try {
                    r = studentService.insertAndUpdateStudentsDetailedFromExcel(new ArrayList<>(excel.getStudents()));
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            }

            if (Constants.EXCEL_INVALID_DATA.equals(r.getResult())) {
                map.put("invalidCount", r.getMaxInvalidCount());
                map.put("whatInvalid", r.showValidData());
                msg = r.getResult();
            }

            databaseInsertRowCount += (int) r.getInsertCount();
            databaseUpdateRowCount += (int) r.getUpdateCount();

            long endTime = System.currentTimeMillis(); //获取结束时间
            Speed speedOfExcelImport = new Speed(excelEffectiveDataRowCount, endTime - startTime);
            SqlProceedSpeed speedOfDatabaseImport = new SqlProceedSpeed(databaseUpdateRowCount, databaseInsertRowCount, databaseDeleteRowCount, endTime - startTime);
            speedOfExcelImport.parseSpeed();
            speedOfDatabaseImport.parseSpeed();
            map.put("excelSpeed", speedOfExcelImport);
            map.put("databaseSpeed", speedOfDatabaseImport);

            map.put("msg", msg);

            //向对应校区的用户发送通知消息
            if (excel != null && excel.getStudentAndClassDetailedDtos().size() > 0) {
                StudentAndClassDetailedDto dto = excel.getStudentAndClassDetailedDtos().get(0);
                Class clazz = classService.getClassByClassId(dto.getClassId());
                try {
                    sendMessageToUser(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            }
        }

        return map;
    }

    /**
     *  向指定开课年份季度分期和校区的用户（助教）发送学生花名册更新的通知
     *
     * @param clazz 从更新的记录中选取一个班级为例，取其开课年份季度分期和校区作为要通知的校区，clazz的其他信息也用于消息正文
     */
    private void sendMessageToUser(Class clazz) throws Exception {
        if (clazz != null) {
            clazz.setParsedClassYear();
            String campus = clazz.getClassCampus();
            if (!StringUtils.isEmpty(campus)) {
                ClassSeasonDto classSeasonDto = new ClassSeasonDto(clazz.getClassYear(), clazz.getClassSeason(), clazz.getClassSubSeason());
                List<Assistant> assistants = assistantService.listAssistantsByClassSeasonAndCampus(classSeasonDto, campus);
                List<Long> userIds = new ArrayList<>();
                for (Assistant assistant : assistants) {
                    User user=userService.getUserByWorkId(assistant.getAssistantWorkId());
                    if (user != null) {
                        userIds.add(user.getId());
                    }
                }

                List<UserMessage> messages = new ArrayList<>();
                for (Long userId : userIds) {
                    UserMessage message = new UserMessage();
                    message.setUserId(userId);
                    message.setUserFromId(userService.getSessionUserInfo().getId());
                    message.setMessageTitle("学员上课信息有变化");
                    StringBuilder messageContent = new StringBuilder();
                    messageContent.append("你的学管老师刚刚更新了" + clazz.getClassCampus() + "校区" + clazz.getClassYear() + "年" + clazz.getClassSeason() + "的学员上课信息。")
                            .append("<br>点<a lay-href='/studentAndClass/admin/page' lay-text='上课信息'>这里</a>前往查看。");
                    message.setMessageContent(messageContent.toString());
                    message.setMessageTime(new Date());
                    if (UserMessageUtils.isValidUserMessageUpdateInfo(message)) {
                        messages.add(message);
                    }
                }
                userMessageService.insertManyUserMessages(messages);
            }
        }
    }

    /**
     * 导入学校统计
     *
     * @param file
     * @return
     */
    @RequestMapping("/importSchool")
    @ResponseBody
    public Map<String, Object> importSchool(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>();
        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map2.put("src", "");
        map.put("data", map2);

        if (file == null || file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidFileInputException(msg);
        }


        if (!Excel.isExcel(file.getOriginalFilename())) {
            String msg = "上传文件不是excel";
            logger.error(msg);
            throw new InvalidFileInputException(msg);
        }

        long startTime = System.currentTimeMillis();   //获取开始时间
        int excelEffectiveDataRowCount = 0; //表格有效数据行数
        int databaseUpdateRowCount = 0; //数据库更新记录数
        int databaseInsertRowCount = 0; //数据库插入记录数
        int databaseDeleteRowCount = 0; //数据库删除记录数

        StudentSchoolExcel excel = null;
        try {
            excel = new StudentSchoolExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));
            excelEffectiveDataRowCount = excel.readStudentsSchoolsFromExcel();
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        } catch (ExcelColumnNotFoundException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_COLUMN_NOT_FOUND);
            map.put("whatWrong", e.getWhatWrong());
            return map;
        } catch (InvalidFileTypeException e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        } catch (ExcelTooManyRowsException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_TOO_MANY_ROWS);
            map.put("rowCountThreshold", e.getRowCountThreshold());
            map.put("actualRowCount", e.getActualRowCount());
            return map;
        }

        String msg = Constants.SUCCESS;
        try {
            DefaultFromExcelUpdateResult result = studentService.insertAndUpdateStudentsSchoolsFromExcel(excel.getStudents());

            if (Constants.EXCEL_INVALID_DATA.equals(result.getResult())) {
                map.put("invalidCount", result.getMaxInvalidCount());
                map.put("whatInvalid", result.showValidData());
                msg = result.getResult();
            }


            databaseInsertRowCount += (int) result.getInsertCount();
            databaseUpdateRowCount += (int) result.getUpdateCount();

            long endTime = System.currentTimeMillis(); //获取结束时间
            Speed speedOfExcelImport = new Speed(excelEffectiveDataRowCount, endTime - startTime);
            SqlProceedSpeed speedOfDatabaseImport = new SqlProceedSpeed(databaseUpdateRowCount, databaseInsertRowCount, databaseDeleteRowCount, endTime - startTime);
            speedOfExcelImport.parseSpeed();
            speedOfDatabaseImport.parseSpeed();
            map.put("excelSpeed", speedOfExcelImport);
            map.put("databaseSpeed", speedOfDatabaseImport);

            map.put("msg", msg);
        }  catch (Exception e) {
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
        condition.setStudentId(StringUtils.upperCase(condition.getStudentId()));
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

        map.put("data", studentService.insertOneStudent(student).getResult());

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
        studentService.deleteStudentsByCondition(condition);
        map.put("data", Constants.SUCCESS);
        return map;
    }
}
