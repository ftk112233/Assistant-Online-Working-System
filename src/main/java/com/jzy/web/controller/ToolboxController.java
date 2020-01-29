package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.*;
import com.jzy.manager.util.*;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.ClassDetailedDto;
import com.jzy.model.dto.MissManyDaysLessonStudentDetailedDto;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
import com.jzy.model.entity.*;
import com.jzy.model.entity.Class;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import com.jzy.model.excel.input.SeatTableTemplateInputExcel;
import com.jzy.model.excel.input.StudentListForSeatTableUploadByUserExcel;
import com.jzy.model.excel.input.StudentListUploadByUserExcel;
import com.jzy.model.excel.input.StudentSchoolExcel;
import com.jzy.model.excel.template.AssistantTutorialExcel;
import com.jzy.model.excel.template.MissedLessonExcel;
import com.jzy.model.excel.template.SeatTableTemplateExcel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @ClassName ExcelController
 * @Author JinZhiyun
 * @Description 表格处理的控制器
 * @Date 2019/11/21 12:22
 * @Version 1.0
 **/
@Controller
@RequestMapping("/toolbox")
public class ToolboxController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(ToolboxController.class);

    /**
     * 做助教工作手册时，用户手动上传的花名册缓存
     */
    private static Map<Long, StudentListUploadByUserExcel> studentListUploadByUserCache = new HashMap<>();

    /**
     * 单独做座位表时，用户手动上传的学生名单缓存
     */
    private static Map<Long, StudentListForSeatTableUploadByUserExcel> studentListForSeatTableUploadByUserCache = new HashMap<>();

    /**
     * 上传学校统计空表的缓存
     */
    private static Map<Long, StudentSchoolExcel> studentSchoolUploadCache = new HashMap<>();

    static {
        //每天定时清理cache，改用springboot定时任务
//        TimerManager.startDailyTask(new TimerTask() {
//            @Override
//            public void run() {
//                ToolboxController.clearCache();
//                logger.info("定时清理ToolboxController的cache执行！----" + MyTimeUtils.dateToStringYMDHMS(new Date()));
//            }
//        });
    }

    /**
     * 清除指定用户id的文件cache
     *
     * @param id 用户id
     */
    public static void clearCache(Long id) {
        studentListUploadByUserCache.remove(id);
        studentListForSeatTableUploadByUserCache.remove(id);
        studentSchoolUploadCache.remove(id);
    }

    /**
     * 清除全部缓存
     */
    public static void clearCache() {
        studentListUploadByUserCache = new HashMap<>();
        studentListForSeatTableUploadByUserCache = new HashMap<>();
        studentSchoolUploadCache = new HashMap<>();
    }

    /**
     * 跳转助教制作开班多件套页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/assistant/startClassExcel")
    public String startClassExcel(Model model, ClassDetailedDto classDetailedDto) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));

        model.addAttribute(ModelConstants.CLASS_ID_MODEL_KEY, classDetailedDto.getClassId());
        model.addAttribute(ModelConstants.CLASS_CAMPUS_MODEL_KEY, classDetailedDto.getClassCampus());
        return "toolbox/assistant/startClassExcel";
    }

    /**
     * 手动上传花名册。
     * 这里调用excel.readStudentAndClassInfoByClassIdFromExcel("any input");的作用是扫描一下输入excel符合要求：
     * 如果不符合捕获异常，马上返回前端；如果没有任何异常，才把上传的文件放到cache中。
     *
     * @param file 上传的表格
     * @return
     */
    @RequestMapping("/assistant/uploadStudentList")
    @ResponseBody
    public Map<String, Object> uploadStudentList(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);
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

        StudentListUploadByUserExcel excel = null;
        try {
            excel = new StudentListUploadByUserExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));

            //try用户上传文件是否规范
            excel.readStudentAndClassInfoByClassIdFromExcel("any input");

            //将当前用户上传的花名册put到cache中，以备输出文件时读取
            Long id = userService.getSessionUserInfo().getId();
            studentListUploadByUserCache.put(id, excel);
        } catch (InvalidFileTypeException e) {
            e.printStackTrace();
            map.put("msg", FAILURE);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", FAILURE);
            return map;
        } catch (ExcelColumnNotFoundException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_COLUMN_NOT_FOUND);
            map.put("whatWrong", e.getWhatWrong());
            return map;
        } catch (ExcelTooManyRowsException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_TOO_MANY_ROWS);
            map.put("rowCountThreshold", e.getRowCountThreshold());
            map.put("actualRowCount", e.getActualRowCount());
            return map;
        }

        map.put("msg", SUCCESS);
        return map;
    }

    /**
     * 导出助教工作手册（不含座位表）。
     * 根据是否打开黑魔法，如果不开，表示是用户手动上传的，从cache中取文件
     *
     * @param classDetailedDto 输入的校区班号等信息的封装
     * @return
     */
    @RequestMapping("/assistant/exportAssistantTutorialWithoutSeatTable")
    public String exportAssistantTutorialWithoutSeatTable(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestParam(value = "magic", required = false) String magic, ClassDetailedDto classDetailedDto) {
        AssistantTutorialExcel excel = null;
        try {
            List<StudentAndClassDetailedWithSubjectsDto> results = new ArrayList<>();

            excel = new AssistantTutorialExcel(filePathProperties.getToolboxAssistantTutorialTemplatePathAndName(classDetailedDto.getClassCampus()));

            if (Constants.ON.equals(magic)) {
                //若打开黑魔法，从数据库解析
                results = studentAndClassService.listStudentAndClassesWithSubjectsByClassId(classDetailedDto.getClassId());

                for (StudentAndClassDetailedWithSubjectsDto result : results) {
                    result.setClassTeacherRequirement(classDetailedDto.getClassTeacherRequirement());
                }
            } else {
                Long id = userService.getSessionUserInfo().getId();

                StudentListUploadByUserExcel excelCache = studentListUploadByUserCache.get(id);

                if (excelCache != null) {
                    //缓存中有文件，即用户上传过了
                    results = excelCache.readStudentAndClassInfoByClassIdFromExcel(classDetailedDto.getClassId());

                    for (StudentAndClassDetailedWithSubjectsDto result : results) {
                        result.setClassCampus(classDetailedDto.getClassCampus());
                        result.setClassroom(classDetailedDto.getClassroom());
                        result.setClassTime(classDetailedDto.getClassTime());
                        result.setTeacherName(classDetailedDto.getTeacherName());
                        result.setAssistantName(classDetailedDto.getAssistantName());
                        result.setClassTeacherRequirement(classDetailedDto.getClassTeacherRequirement());
                    }

                }
            }

            //将读到的数据，修改到助教工作手册模板表格中
            excel.writeAssistantTutorialWithoutSeatTable(results);

            //下载处理好的文件
            FileUtils.downloadFile(request, response, excel, FileUtils.TEMPLATES.get(1));
        } catch (IOException | ExcelColumnNotFoundException | ClassTooManyStudentsException | ExcelTooManyRowsException | InvalidFileTypeException e) {
            e.printStackTrace();
            String msg = "exportAssistantTutorialWithoutSeatTable下载文件失败";
            logger.error(msg);
        }

        return SUCCESS;
    }

    /**
     * 导出座位表
     * 根据是否打开黑魔法，如果不开，表示是用户手动上传的，从cache中取文件
     *
     * @param classDetailedDto 输入的校区班号等信息的封装
     * @return
     */
    @RequestMapping("/assistant/exportAssistantTutorialAndSeatTable")
    public String exportAssistantTutorialAndSeatTable(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(value = "magic", required = false) String magic, ClassDetailedDto classDetailedDto) throws InvalidFileTypeException {
        SeatTableTemplateExcel excel = null;
        try {
            List<StudentAndClassDetailedWithSubjectsDto> results = new ArrayList<>();

            excel = new SeatTableTemplateExcel(filePathProperties.getToolboxSeatTableTemplatePathAndName(classDetailedDto.getClassCampus()));

            if (Constants.ON.equals(magic)) {
                //若打开黑魔法，从数据库解析
                results = studentAndClassService.listStudentAndClassesByClassId(classDetailedDto.getClassId());

            } else {
                Long id = userService.getSessionUserInfo().getId();
                StudentListUploadByUserExcel excelCache = studentListUploadByUserCache.get(id);

                if (excelCache != null) {
                    //缓存中有文件，即用户上传过了
                    results = excelCache.readStudentAndClassInfoByClassIdFromExcel(classDetailedDto.getClassId());

                    //读取用户输入的教室
                    for (StudentAndClassDetailedWithSubjectsDto result : results) {
                        result.setClassroom(classDetailedDto.getClassroom());
                    }

                    //下载完座位表，清除缓存
//                    studentListUploadByUserCache.remove(id);
                    //清除缓存交给springmvc拦截器
                }
            }

            //将读到的数据，修改到助教工作手册模板表格中
            excel.writeSeatTable(results);

            //下载处理好的文件
            FileUtils.downloadFile(request, response, excel, FileUtils.TEMPLATES.get(2));
        } catch (IOException | ExcelColumnNotFoundException | ExcelTooManyRowsException e) {
            e.printStackTrace();
            String msg = "exportAssistantTutorialAndSeatTable下载文件失败";
            logger.error(msg);
        }

        return SUCCESS;
    }


    /**
     * 手动上传用来做座位表的名单。 如果不符合捕获异常，马上返回前端；如果没有任何异常，才把上传的文件放到cache中。
     *
     * @param file 上传的表格
     * @return
     */
    @RequestMapping("/assistant/uploadStudentListForSeatTable")
    @ResponseBody
    public Map<String, Object> uploadStudentListForSeatTable(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);
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

        StudentListForSeatTableUploadByUserExcel excel = null;
        try {
            excel = new StudentListForSeatTableUploadByUserExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));

            excel.readStudentNames();

            //将当前用户上传的花名册put到cache中，以备输出文件时读取
            studentListForSeatTableUploadByUserCache.put(userService.getSessionUserInfo().getId(), excel);
        } catch (InvalidFileTypeException | IOException e) {
            e.printStackTrace();
            map.put("msg", FAILURE);
            return map;
        } catch (ExcelTooManyRowsException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_TOO_MANY_ROWS);
            map.put("rowCountThreshold", e.getRowCountThreshold());
            map.put("actualRowCount", e.getActualRowCount());
            return map;
        } catch (ExcelColumnNotFoundException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_COLUMN_NOT_FOUND);
            map.put("whatWrong", e.getWhatWrong());
            return map;
        }

        map.put("msg", SUCCESS);
        return map;
    }

    /**
     * 导出座位表。
     * 从cache中取上传过的文件
     *
     * @param classDetailedDto 输入的校区班号等信息的封装
     * @return
     */
    @RequestMapping("/assistant/exportSeatTable")
    public String exportSeatTable(HttpServletRequest request, HttpServletResponse response, ClassDetailedDto classDetailedDto) {
        SeatTableTemplateExcel excel = null;
        try {
            List<StudentAndClassDetailedWithSubjectsDto> results = new ArrayList<>();

            excel = new SeatTableTemplateExcel(filePathProperties.getToolboxSeatTableTemplatePathAndName(classDetailedDto.getClassCampus()));

            Long id = userService.getSessionUserInfo().getId();
            StudentListForSeatTableUploadByUserExcel excelCache = studentListForSeatTableUploadByUserCache.get(id);

            if (excelCache != null) {
                //缓存中有文件，即用户上传过了
                List<String> studentNames = excelCache.getStudentNames();

                //将学生姓名列表和输入的教室封装成StudentAndClassDetailedWithSubjectsDto对象
                for (String studentName : studentNames) {
                    StudentAndClassDetailedWithSubjectsDto tmp = new StudentAndClassDetailedWithSubjectsDto();
                    tmp.setStudentName(studentName);
                    tmp.setClassroom(classDetailedDto.getClassroom());
                    results.add(tmp);
                }
            }


            //将读到的数据，修改到座位表模板表格中
            excel.writeSeatTable(results);

            //下载处理好的文件
            FileUtils.downloadFile(request, response, excel, FileUtils.TEMPLATES.get(2));
        } catch (InvalidFileTypeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            String msg = "exportSeatTable下载文件失败";
            logger.error(msg);
        }

        return SUCCESS;
    }

    /**
     * 跳转助教制作补课单页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/assistant/missLessonStudentExcel")
    public String missLessonStudentExcel(Model model, StudentAndClassDetailedDto dto) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));

        model.addAttribute(ModelConstants.STUDENT_AND_CLASS_MODEL_KEY, dto);
        return "toolbox/assistant/missLessonStudentExcel";
    }

    /**
     * 跳转班号输入不存在页面
     *
     * @param model
     * @param wrongClassId 错误的班号
     * @return
     */
    @RequestMapping("/assistant/wrongClassId")
    public String missLessonStudentExcel(Model model, @RequestParam("classId") String wrongClassId) {
        model.addAttribute(ModelConstants.WRONG_CLASS_ID, wrongClassId);
        return "tips/classNotExist";
    }


    /**
     * 开补课单时先检测原班号和补课班号是否存在，相应返回json，如果成功才进一步下载输出补课单
     *
     * @param originalClassId 原班号
     * @param currentClassId  补课班号
     * @return
     */
    @RequestMapping("/assistant/exportAssistantMissLessonTableParamTest")
    @ResponseBody
    public Map<String, Object> exportAssistantMissLessonTableParamTest(@RequestParam("originalClassId") String originalClassId, @RequestParam("currentClassId") String currentClassId) {
        Map<String, Object> map = new HashMap<>(1);

        if (classService.getClassByClassId(originalClassId) == null) {
            map.put("data", "originalClassNotExist");
            return map;
        }

        if (classService.getClassByClassId(currentClassId) == null) {
            map.put("data", "currentClassNotExist");
            return map;
        }

        map.put("data", SUCCESS);
        return map;
    }

    /**
     * 导出补课单。
     * 1.根据是否同步数据库。如果打开——向数据库插入学生补课记录，向原班助教和补课班助教发送消息
     * 2.根据是否开启邮件提醒。如果打开——向相应的助教发送邮件提醒
     * 3.下载补课单
     *
     * @param attr
     * @param request
     * @param response
     * @param sync                         是否开启同步数据库
     * @param emailTip                     是否开启邮件提醒
     * @param originalCampus               原校区
     * @param currentCampus                补课校区
     * @param missLessonStudentDetailedDto 补课班号，原班号，学员姓名等封装
     * @return
     */
    @RequestMapping("/assistant/exportAssistantMissLessonTable")
    public String exportAssistantMissLessonTable(RedirectAttributes attr, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "sync", required = false) String sync, @RequestParam(value = "emailTip", required = false) String emailTip
            , @RequestParam("originalCampus") String originalCampus, @RequestParam("currentCampus") String currentCampus, MissManyDaysLessonStudentDetailedDto missLessonStudentDetailedDto) {

        ClassDetailedDto originalClass = classService.getClassDetailByClassId(missLessonStudentDetailedDto.getOriginalClassId());

        ClassDetailedDto currentClass = classService.getClassDetailByClassId(missLessonStudentDetailedDto.getCurrentClassId());

        if (originalClass == null) {
            String msg = "开补课单时未查到原班级！";
            logger.info(msg);
            attr.addAttribute("classId", missLessonStudentDetailedDto.getOriginalClassId());
            return "redirect:/toolbox/assistant/wrongClassId";
        }
        if (currentClass == null) {
            String msg = "开补课单时未查到补课班级！";
            logger.info(msg);
            attr.addAttribute("classId", missLessonStudentDetailedDto.getCurrentClassId());
            return "redirect:/toolbox/assistant/wrongClassId";
        }

        missLessonStudentDetailedDto.setCurrentClassGrade(currentClass.getClassGrade());
        missLessonStudentDetailedDto.setCurrentClassSubject(currentClass.getClassSubject());
        missLessonStudentDetailedDto.setCurrentClassSimplifiedTime(currentClass.getClassSimplifiedTime());
        missLessonStudentDetailedDto.setOriginalAssistantName(originalClass.getAssistantName());
        missLessonStudentDetailedDto.setCurrentClassroom(currentClass.getClassroom());
        missLessonStudentDetailedDto.setCurrentAssistantName(currentClass.getAssistantName());

        MissedLessonExcel excel = null;
        try {
            excel = new MissedLessonExcel(filePathProperties.getToolboxMissLessonTemplatePathAndName());

            //将读到的数据，修改到补课单模板表格中
            excel.writeMissLesson(missLessonStudentDetailedDto, currentCampus);

            //下载处理好的文件
            FileUtils.downloadFile(request, response, excel, FileUtils.TEMPLATES.get(3));

            if (Constants.ON.equals(sync)) {
                //向原班助教和补课班助教发送消息
                UserWithMessage originalUserWithMessage = sendMessageToOriginalClassAssistant(missLessonStudentDetailedDto, originalClass, currentClass);
                UserWithMessage currentUserWithMessage = sendMessageToCurrentClassAssistant(missLessonStudentDetailedDto, originalClass, currentClass);

                //若打开自动同步
                //数据添加补课学生记录
                List<Date> days = missLessonStudentDetailedDto.getDaysBetween();
                for (Date day : days) {
                    missLessonStudentDetailedDto.setDate(day);
                    if (MissLessonStudentUtils.isValidMissLessonStudentUpdateInfo(missLessonStudentDetailedDto)) {
                        missLessonStudentService.insertOneMissLessonStudent(missLessonStudentDetailedDto);
                    }
                }

                if (Constants.ON.equals(emailTip)) {
                    //如果打开邮件提醒
                    sendEmailToOriginalAndCurrentClassAssistant(originalUserWithMessage, currentUserWithMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            String msg = "exportAssistantMissLessonTable下载文件失败";
            logger.error(msg);
        } catch (InvalidFileTypeException e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

    /**
     * 用户和其对应提示信息的封装
     */
    @Data
    private class UserWithMessage implements Serializable {
        private static final long serialVersionUID = -8898202525737562478L;

        private User user;

        private String message;

        public UserWithMessage(User user, String message) {
            this.user = user;
            this.message = message;
        }
    }

    /**
     * 向原班级助教发送其班上有补课学生的消息
     *
     * @param missLessonStudentDetailedDto 补课学生的封装信息
     * @param originalClass                原班级
     * @param currentClass                 补课班级
     * @return 返回原班助教的用户信息（用户对象）和其相应提示信息
     */
    private UserWithMessage sendMessageToOriginalClassAssistant(MissManyDaysLessonStudentDetailedDto missLessonStudentDetailedDto, ClassDetailedDto originalClass, ClassDetailedDto currentClass) {
        Long userFromId = userService.getSessionUserInfo().getId();

        String originalAssistantWorkId = assistantService.getAssistantById(originalClass.getClassAssistantId()).getAssistantWorkId();
        User originalUser = userService.getUserByWorkId(originalAssistantWorkId);
        if (originalUser == null) {
            return null;
        }
        Long originalUserId = originalUser.getId();
        UserMessage originalMessage = new UserMessage();
        originalMessage.setUserId(originalUserId);
        originalMessage.setUserFromId(userFromId);
        originalMessage.setMessageTitle("你的班上有需要补课的学生");
        StringBuilder originalMessageContent = new StringBuilder();
        originalMessageContent.append("你的\"").append(originalClass.getClassName()).append("\"(上课时间：").append(originalClass.getClassSimplifiedTime()).append("，上课教室：").append(originalClass.getClassCampus() + originalClass.getClassroom() + "教").append(")上的学生<em>").append(missLessonStudentDetailedDto.getStudentName()).append("</em>需要补课。")
                .append("<br>" + "补课班号：").append(currentClass.getClassId())
                .append("<br>" + "补课班级名称：").append(currentClass.getClassName())
                .append("<br>" + "补课班级助教：").append(currentClass.getAssistantName())
                .append("<br>" + "补课班级任课教师：").append(currentClass.getTeacherName())
                .append("<br>" + "补课时间 ：").append(missLessonStudentDetailedDto.getDaysBetweenToString() + ", " + missLessonStudentDetailedDto.getCurrentClassSimplifiedTime())
                .append("<br>" + "补课班级上课教室：").append(currentClass.getClassCampus() + currentClass.getClassroom() + "教");
        originalMessage.setMessageContent(originalMessageContent.toString());
        originalMessage.setMessageTime(new Date());
        if (UserMessageUtils.isValidUserMessageUpdateInfo(originalMessage)) {
            userMessageService.insertOneUserMessage(originalMessage);
        }

        String messageStr = originalMessageContent.toString().replace("<br>", "\n");
        return new UserWithMessage(originalUser, messageStr);
    }

    /**
     * 向补课班级助教发送有补课学生补课到他的班级的消息
     *
     * @param missLessonStudentDetailedDto 补课学生的封装信息
     * @param originalClass                原班级
     * @param currentClass                 补课班级
     * @return 返回补课助教的用户信息（用户对象）和其相应提示信息
     */
    private UserWithMessage sendMessageToCurrentClassAssistant(MissManyDaysLessonStudentDetailedDto missLessonStudentDetailedDto, ClassDetailedDto originalClass, ClassDetailedDto currentClass) {
        Long userFromId = userService.getSessionUserInfo().getId();

        String currentAssistantWorkId = assistantService.getAssistantById(currentClass.getClassAssistantId()).getAssistantWorkId();
        User currentUser = userService.getUserByWorkId(currentAssistantWorkId);
        if (currentUser == null) {
            return null;
        }

        Long currentUserId = currentUser.getId();
        UserMessage currentMessage = new UserMessage();
        currentMessage.setUserId(currentUserId);
        currentMessage.setUserFromId(userFromId);
        currentMessage.setMessageTitle("有学生补课到你的班上");
        StringBuilder currentMessageContent = new StringBuilder();
        currentMessageContent.append("学生<em>").append(missLessonStudentDetailedDto.getStudentName()).append("</em>补课到你的\"").append(currentClass.getClassName()).append("\"(上课时间：").append(currentClass.getClassSimplifiedTime()).append("，上课教室：").append(currentClass.getClassCampus() + currentClass.getClassroom() + "教)。")
                .append("<br>" + "补课日期：").append(missLessonStudentDetailedDto.getDaysBetweenToString())
                .append("<br>" + "原班号：").append(originalClass.getClassId())
                .append("<br>" + "原班级名称：").append(originalClass.getClassName())
                .append("<br>" + "原班级助教：").append(originalClass.getAssistantName())
                .append("<br>" + "原班级任课教师：").append(originalClass.getTeacherName());
        currentMessage.setMessageContent(currentMessageContent.toString());
        currentMessage.setMessageTime(new Date());
        if (UserMessageUtils.isValidUserMessageUpdateInfo(currentMessage)) {
            userMessageService.insertOneUserMessage(currentMessage);
        }

        String messageStr = currentMessageContent.toString().replace("<br>", "\n");
        return new UserWithMessage(currentUser, messageStr);
    }

    /**
     * 想原班级助教和补课班级助教发送必要的邮件通知
     *
     * @param originalUserWithMessage 原班助教的用户信息（用户对象）和其相应提示信息
     * @param currentUserWithMessage  补课助教的用户信息（用户对象）和其相应提示信息
     */
    private void sendEmailToOriginalAndCurrentClassAssistant(UserWithMessage originalUserWithMessage, UserWithMessage currentUserWithMessage) {
        String title = "AOWS-重要提醒";
        if (originalUserWithMessage != null && originalUserWithMessage.getUser() != null && !StringUtils.isEmpty(originalUserWithMessage.getUser().getUserEmail())) {
            String content1 = originalUserWithMessage.getMessage() + "\n前往查看详情" + Constants.INDEX + "。";
            SendEmailUtils.sendConcurrentEncryptedEmail(originalUserWithMessage.getUser().getUserEmail(), title, content1);
        }
        if (currentUserWithMessage != null && currentUserWithMessage.getUser() != null && !StringUtils.isEmpty(currentUserWithMessage.getUser().getUserEmail())) {
            String content2 = currentUserWithMessage.getMessage() + "\n前往查看详情" + Constants.INDEX + "。";
            SendEmailUtils.sendConcurrentEncryptedEmail(currentUserWithMessage.getUser().getUserEmail(), title, content2);
        }
    }

    /**
     * 跳转学管信息导入页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/assistantAdministrator/infoImport")
    public String infoImport(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(Class.SEASONS));
        model.addAttribute(ModelConstants.SUB_SEASONS_MODEL_KEY, JSON.toJSONString(Class.SUB_SEASONS));
        return "toolbox/assistantAdministrator/infoImport";
    }

    /**
     * 学管工具箱中导入信息表格的范例下载。
     *
     * @param request
     * @param response
     * @param type     不同的type对应不同的文件 {@link FileUtils}
     * @return
     */
    @RequestMapping("/assistantAdministrator/downloadExample/{type}")
    public String downloadExample(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
        int typeVal = Integer.parseInt(type);
        if (typeVal <= 0) {
            String msg = "downloadExample方法入参错误!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        try {
            String filePathAndNameToRead = filePathProperties.getToolboxExamplePathAndNameByKey(typeVal);
            String downloadFileName = FileUtils.EXAMPLES.get(typeVal);
            //下载文件
            FileUtils.downloadFile(request, response, filePathAndNameToRead, downloadFileName);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "downloadExample下载文件失败";
            logger.error(msg);
            return FAILURE;
        }
        return SUCCESS;
    }

    /**
     * 跳转学管模板导入页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/assistantAdministrator/templateImport")
    public String templateImport(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        return "toolbox/assistantAdministrator/templateImport";
    }

    /**
     * 导入座位表模板。如果捕获异常，马上返回前端。
     *
     * @param file 上传的表格
     * @return
     */
    @RequestMapping("/assistantAdministrator/seatTableTemplateImport")
    @ResponseBody
    public Map<String, Object> seatTableTemplateImport(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "classCampus", required = false) String classCampus) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);
        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map2.put("src", "");
        map.put("data", map2);

        if (StringUtils.isEmpty(classCampus) || !ClassUtils.isValidClassCampus(classCampus)) {
            map.put("msg", "campusInvalid");
            return map;
        }

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

        SeatTableTemplateInputExcel excel = null;
        try {
            excel = new SeatTableTemplateInputExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));
            //读出教室
            List<CampusAndClassroom> campusAndClassrooms = excel.readSeatTable();

            //先删除当前校区的所有校区教室记录
            campusAndClassroomService.deleteCampusAndClassroomsByCampus(classCampus);
            for (CampusAndClassroom campusAndClassroom : campusAndClassrooms) {
                campusAndClassroom.setCampus(classCampus);
                //插入数据库
                if (CampusAndClassroomUtils.isValidCampusAndClassroomUpdateInfo(campusAndClassroom)) {
                    campusAndClassroomService.insertOneCampusAndClassroom(campusAndClassroom);
                } else {
                    logger.error("上传模板失败");
                    map.put("msg", FAILURE);
                    return map;
                }
            }

            String filePathAndName = filePathProperties.getToolboxSeatTableTemplatePathAndName(classCampus);
            File dest = new File(filePathAndName);
            file.transferTo(dest);

        } catch (ExcelSheetNameInvalidException e) {
            e.printStackTrace();
            map.put("msg", "sheetNameError");
            return map;
        } catch (InvalidFileTypeException e) {
            e.printStackTrace();
            map.put("msg", FAILURE);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", FAILURE);
            return map;
        } catch (ExcelTooManyRowsException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_TOO_MANY_ROWS);
            map.put("rowCountThreshold", e.getRowCountThreshold());
            map.put("actualRowCount", e.getActualRowCount());
            return map;
        }

        map.put("msg", SUCCESS);
        return map;
    }

    /**
     * 跳转学校统计页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/common/studentSchoolExport")
    public String studentSchoolExport(Model model) {
        return "toolbox/common/studentSchoolExport";
    }


    /**
     * 上传学校统计空表。如有异常捕获，返回前端；如没有，表格存cache。
     *
     * @param file 上传的表格
     * @return
     */
    @RequestMapping("/common/uploadStudentSchool")
    @ResponseBody
    public Map<String, Object> uploadStudentSchool(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);
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

        StudentSchoolExcel excel = null;

        try {
            excel = new StudentSchoolExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));

            //缓存中有文件，即用户上传过了
            excel.readStudentIdsFromExcel();
            Set<String> studentIds = excel.getStudentIds();

            Map<String, Student> studentMap = new HashMap<>();
            for (String studentId : studentIds) {
                Student student = studentService.getStudentByStudentId(studentId);
                if (student != null) {
                    studentMap.put(studentId, student);
                }
            }

            excel.setStudentCache(studentMap);
            //文件放缓存待下载处理
            studentSchoolUploadCache.put(userService.getSessionUserInfo().getId(), excel);

        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", FAILURE);
            return map;
        } catch (InvalidFileTypeException e) {
            e.printStackTrace();
            map.put("msg", FAILURE);
            return map;
        } catch (ExcelColumnNotFoundException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_COLUMN_NOT_FOUND);
            map.put("whatWrong", e.getWhatWrong());
            return map;
        } catch (ExcelTooManyRowsException e) {
            e.printStackTrace();
            map.put("msg", Constants.EXCEL_TOO_MANY_ROWS);
            map.put("rowCountThreshold", e.getRowCountThreshold());
            map.put("actualRowCount", e.getActualRowCount());
            return map;
        }

        map.put("msg", SUCCESS);
        return map;
    }

    /**
     * 下载读取好的学校统计表。从cache中读用户上传的文件
     *
     * @return
     */
    @RequestMapping("/common/downloadStudentSchool")
    public String exportSeatTable(HttpServletRequest request, HttpServletResponse response) {
        StudentSchoolExcel excelCache = studentSchoolUploadCache.get(userService.getSessionUserInfo().getId());
        //下载完学校统计表，清除缓存
        studentSchoolUploadCache.remove(userService.getSessionUserInfo().getId());
        try {
            if (excelCache != null) {
                //将读到的数据，修改到座位表模板表格中
                excelCache.writeStudentsSchools();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //下载处理好的文件
            FileUtils.downloadFile(request, response, excelCache, "学校统计_" + UUID.randomUUID().toString().replace("-", "") + excelCache.getVersion().getSuffix());
        } catch (IOException e) {
            e.printStackTrace();
            String msg = "downloadStudentSchool下载文件失败";
            logger.error(msg);
            return FAILURE;
        }


        return SUCCESS;
    }
}
