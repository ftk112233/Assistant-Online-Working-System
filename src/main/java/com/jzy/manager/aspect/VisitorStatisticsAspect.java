package com.jzy.manager.aspect;

import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.util.MyTimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName VisitorStatisticsAspect
 * @Author JinZhiyun
 * @Description 访客量统计的切面
 * @Date 2020/1/31 18:32
 * @Version 1.0
 **/
@Aspect
@Component
public class VisitorStatisticsAspect {
    private final static Logger logger = LogManager.getLogger(VisitorStatisticsAspect.class);

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Autowired
    protected HashOperations<String, String, Object> hashOps;

    @Autowired
    protected ValueOperations<String, Object> valueOps;

    @Pointcut("execution(* com.jzy.web.controller.AuthenticationController.index(..)) ")
    public void indexPoint() {
    }

    @Pointcut("execution(* com.jzy.web.controller.UsefulInformationAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.StudentAndClassAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.MissLessonStudentAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.StudentAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.ClassAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.AssistantAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.TeacherAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.UserAdminController.page(..))" +
            "|| execution(* com.jzy.web.controller.QuestionAdminController.page(..))")
    public void infoManagementPagePoint() {
    }

    @Pointcut("execution(* com.jzy.web.controller.ToolboxController.startClassExcel(..))" +
            "|| execution(* com.jzy.web.controller.ToolboxController.missLessonStudentExcel(..))" +
            "|| execution(* com.jzy.web.controller.ToolboxController.exportStudentPhonePage(..))" +
            "|| execution(* com.jzy.web.controller.ToolboxController.infoImport(..))" +
            "|| execution(* com.jzy.web.controller.ToolboxController.templateImport(..))" +
            "|| execution(* com.jzy.web.controller.ToolboxController.studentSchoolExport(..))")
    public void toolboxPagePoint() {
    }


    @AfterReturning(pointcut = "indexPoint()")
    public void visitIndex(JoinPoint jp) {
        //当日主页访问量加1
        String key = RedisConstants.getTodayIndexVisitorStatisticsKey();
        String hourKey = MyTimeUtils.getCurrentHour() + "";
        int newVisitTimes = 1;
        if (hashOps.hasKey(key, hourKey)) {
            int visitTimes = (int) hashOps.get(key, hourKey);
            newVisitTimes = ++visitTimes;
        }
        hashOps.put(key, hourKey, newVisitTimes);
        redisTemplate.expire(key, RedisConstants.VISITOR_STATISTICS_EXPIRE, TimeUnit.DAYS);
    }

    @AfterReturning(pointcut = "infoManagementPagePoint()")
    public void visitInfoManagementPage(JoinPoint jp) {
        //当日信息管理区访问量加1
        String key = RedisConstants.getTodayInfoManagementVisitorStatisticsKey();
        int newVisitTimes = 1;
        if (redisTemplate.hasKey(key)) {
            int visitTimes = (int) valueOps.get(key);
            newVisitTimes = ++visitTimes;
        }
        valueOps.set(key, newVisitTimes);
        redisTemplate.expire(key, RedisConstants.VISITOR_STATISTICS_EXPIRE, TimeUnit.DAYS);
    }

    @AfterReturning(pointcut = "toolboxPagePoint()")
    public void visitToolboxPage(JoinPoint jp) {
        //当日百宝箱区访问量加1
        String key = RedisConstants.getTodayToolboxVisitorStatisticsKey();
        int newVisitTimes = 1;
        if (redisTemplate.hasKey(key)) {
            int visitTimes = (int) valueOps.get(key);
            newVisitTimes = ++visitTimes;
        }
        valueOps.set(key, newVisitTimes);
        redisTemplate.expire(key, RedisConstants.VISITOR_STATISTICS_EXPIRE, TimeUnit.DAYS);
    }
}
