package com.jzy.manager.aspect;

import com.jzy.manager.constant.Constants;
import com.jzy.model.entity.User;
import com.jzy.model.vo.Speed;
import com.jzy.model.vo.SqlProceedSpeed;
import com.jzy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName DataImportLogger
 * @Author JinZhiyun
 * @Description 信息导入的日志记录
 * @Date 2020/1/20 16:21
 * @Version 1.0
 **/
@Aspect
@Component
public class DataImportLogger {
    private final static Logger logger = LogManager.getLogger(DataImportLogger.class);

    private static final String EXCEL_SPEED = "excelSpeed";

    private static final String DATABASE_SPEED = "databaseSpeed";

    private static final String INVALID_COUNT = "invalidCount";

    private static final String WHAT_INVALID = "whatInvalid";

    @Autowired
    private UserService userService;

    /**
     * 导入助教信息的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.UserAdminController.importExcel(..)) ")
    public void importAssistantExcelPoint() {
    }

    /**
     * 导入排班信息的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.ClassAdminController.importExcel(..)) ")
    public void importClassExcelPoint() {
    }

    /**
     * 导入学生花名册信息的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.StudentAdminController.importExcel(..)) ")
    public void importStudentExcelPoint() {
    }

    /**
     * 导入学生花名册信息的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.StudentAdminController.importSchool(..)) ")
    public void importStudentSchoolExcelPoint() {
    }

    /**
     * 导入座位表模板的切面
     */
    @Pointcut("execution(* com.jzy.web.controller.ToolboxController.seatTableTemplateImport(..)) ")
    public void importSeatTableTemplatePoint() {
    }

    /**
     * 助教和用户导入日志记录，仅输出"成功"和"需要引起注意的"
     *
     * @param jp 连接点
     * @param map 原方法的返回值，json
     */
    @AfterReturning(returning = "map", pointcut = "importAssistantExcelPoint()")
    public void importAssistantExcelLog(JoinPoint jp, Map<String, Object> map) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")助教信息导入" + calculableImportSuccessMsg(map));
        }
    }

    /**
     * 排班导入日志记录，仅输出"成功"和"需要引起注意的"
     *
     * @param jp 连接点
     * @param map 原方法的返回值，json
     */
    @AfterReturning(returning = "map", pointcut = "importClassExcelPoint()")
    public void importClassExcelLog(JoinPoint jp, Map<String, Object> map) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")排班信息导入" + calculableImportSuccessMsg(map));
        }
    }

    /**
     * 花名册导入日志记录，仅输出"成功"和"需要引起注意的"
     *
     * @param jp 连接点
     * @param map 原方法的返回值，json
     */
    @AfterReturning(returning = "map", pointcut = "importStudentExcelPoint()")
    public void importStudentExcelLog(JoinPoint jp, Map<String, Object> map) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")花名册学生信息导入" + calculableImportSuccessMsg(map));
        }
    }

    /**
     * 学校统计导入日志记录，仅输出"成功"和"需要引起注意的"
     *
     * @param jp 连接点
     * @param map 原方法的返回值，json
     */
    @AfterReturning(returning = "map", pointcut = "importStudentSchoolExcelPoint()")
    public void importStudentSchoolExcelLog(JoinPoint jp, Map<String, Object> map) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")学生学校统计信息导入" + calculableImportSuccessMsg(map));
        }
    }

    /**
     * “批量”信息导入（结果带更新条数、速度）成功后的用户回显信息，与前端保持一致。
     *
     * @param map controller返回的json
     * @return 用户回显信息
     */
    private String calculableImportSuccessMsg(Map<String, Object> map) {
        String s;

        String msg = (String) map.get("msg");
        String tip = "失败";
        String timeAndSpeed = "";
        String wrong = "";

        Speed excelSpeed = (Speed) map.get(EXCEL_SPEED);
        SqlProceedSpeed databaseSpeed = (SqlProceedSpeed) map.get(DATABASE_SPEED);
        if (Constants.SUCCESS.equals(msg)) {
            tip = "成功!";
            timeAndSpeed = "\n" + "总耗时：" + excelSpeed.getParsedTime() + "\n" +
                    "导入表格记录数：" + excelSpeed.getCount() + "条；平均速度：" + excelSpeed.getParsedSpeed() + "\n" +
                    "变更数据库记录数：删除" + databaseSpeed.getDeleteCount() + "条；插入" + databaseSpeed.getInsertCount()
                    + "条；更新" + databaseSpeed.getUpdateCount() + "条；平均速度：" + databaseSpeed.getParsedSpeed() + "。";
            wrong = "";
        }

        if (Constants.EXCEL_INVALID_DATA.equals(msg)) {
            tip = "需要引起注意！";
            timeAndSpeed = "\n" + "总耗时：" + excelSpeed.getParsedTime() + "\n" +
                    "导入表格记录数：" + excelSpeed.getCount() + "条；平均速度：" + excelSpeed.getParsedSpeed() + "\n" +
                    "变更数据库记录数：删除" + databaseSpeed.getDeleteCount() + "条；插入" + databaseSpeed.getInsertCount()
                    + "条；更新" + databaseSpeed.getUpdateCount() + "条；平均速度：" + databaseSpeed.getParsedSpeed() + "。\n";
            wrong = "不合法而未被更新的记录：" + map.get(INVALID_COUNT) + "条。所在位置[(列名=值),...]：" + map.get(WHAT_INVALID);
        }

        s = tip + timeAndSpeed + wrong;
        return s;
    }


    /**
     * 座位表导入日志记录
     *
     * @param jp 连接点
     * @param map 原方法的返回值，json
     */
    @AfterReturning(returning = "map", pointcut = "importSeatTableTemplatePoint()")
    public void importSeatTableTemplateLog(JoinPoint jp, Map<String, Object> map) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")座位表模板导入" + generalImportSuccessMsg(map));
        }
    }

    /**
     * 一般信息导入后的用户回显信息，与前端保持一致。
     *
     * @param map controller返回的json
     * @return 用户回显信息
     */
    private String generalImportSuccessMsg(Map<String, Object> map) {
        String s;

        String msg = (String) map.get("msg");

        String tip = "失败";
        String wrong = "";
        if (Constants.SUCCESS.equals(msg)) {
            tip = "成功!";
            wrong = "";
        }

        s = tip + wrong;
        return s;
    }

}
