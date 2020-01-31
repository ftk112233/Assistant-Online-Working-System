package com.jzy.manager.aspect;

import com.jzy.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName ToolboxLogger
 * @Author JinZhiyun
 * @Description 百宝箱请求的日志记录
 * @Date 2020/1/20 19:02
 * @Version 1.0
 **/
@Aspect
@Component
public class ToolboxLogger extends AbstractLogger {
    private final static Logger logger = LogManager.getLogger(ToolboxLogger.class);

    /**
     * 导出助教工作手册的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.ToolboxController.exportAssistantTutorialWithoutSeatTable(..)) ")
    public void exportAssistantTutorialWithoutSeatTablePoint() {
    }

    /**
     * 导出座位表的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.ToolboxController.exportAssistantTutorialAndSeatTable(..)) ")
    public void exportAssistantTutorialAndSeatTablePoint() {
    }

    /**
     * 导出学生联系方式到表单的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.ToolboxController.exportStudentPhoneToForm(..)) ")
    public void exportStudentPhoneToFormPoint() {
    }

    /**
     * 导出学生联系方式到表格的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.ToolboxController.exportStudentPhoneToExcel(..)) ")
    public void exportStudentPhoneToExcelPoint() {
    }


    @AfterReturning("exportAssistantTutorialWithoutSeatTablePoint()")
    public void exportAssistantTutorialWithoutSeatTableLog(JoinPoint jp) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            String msg = "用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")导出助教工作手册!";
            logger.info(msg);
            saveLogToDatebase(msg, user, getIpAddress(jp));
        }
    }

    @AfterReturning("exportAssistantTutorialAndSeatTablePoint()")
    public void exportAssistantTutorialAndSeatTableLog(JoinPoint jp) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            String msg = "用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")导出座位表!";
            logger.info(msg);
            saveLogToDatebase(msg, user, getIpAddress(jp));
        }
    }

    @AfterReturning("exportStudentPhoneToFormPoint()")
    public void exportStudentPhoneToFormLog(JoinPoint jp) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            String msg = "用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")导出学生联系方式到表单!";
            logger.info(msg);
            saveLogToDatebase(msg, user, getIpAddress(jp));
        }
    }

    @AfterReturning("exportStudentPhoneToExcelPoint()")
    public void exportStudentPhoneToExcelLog(JoinPoint jp) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            String msg = "用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")导出学生联系方式到表格!";
            logger.info(msg);
            saveLogToDatebase(msg, user, getIpAddress(jp));
        }
    }
}
