package com.jzy.manager.aspect;

import com.jzy.manager.constant.Constants;
import com.jzy.model.dto.MissLessonStudentDetailedDto;
import com.jzy.model.entity.User;
import com.jzy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MissLessonStudentAspect
 * @Author JinZhiyun
 * @Description 补课学生的切面
 * @Date 2020/1/20 18:30
 * @Version 1.0
 **/
@Aspect
@Component
public class MissLessonStudentAspect {
    private final static Logger logger = LogManager.getLogger(MissLessonStudentAspect.class);

    @Autowired
    private UserService userService;

    @Pointcut("execution(* com.jzy.service.MissLessonStudentService.insertOneMissLessonStudent(..)) ")
    public void insertMissLessonStudentPoint() {
    }

    /**
     * 插入补课学生的日志记录
     *
     * @param jp 连接点
     * @param result 原方法返回值
     * @param missLessonStudentDetailedDto 补课学生入参信息
     */
    @AfterReturning(returning = "result", pointcut = "insertMissLessonStudentPoint() && args(missLessonStudentDetailedDto)")
    public void importAssistantExcelLog(JoinPoint jp, String result, MissLessonStudentDetailedDto missLessonStudentDetailedDto) {
        if (!Constants.SUCCESS.equals(result)){
            return;
        }
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")开设补课单成功。补课学生信息：" + missLessonStudentDetailedDto);
        }
    }
}
