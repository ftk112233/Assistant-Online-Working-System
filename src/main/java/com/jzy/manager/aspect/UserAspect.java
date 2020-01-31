package com.jzy.manager.aspect;

import com.jzy.manager.constant.Constants;
import com.jzy.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName UserApsect
 * @Author JinZhiyun
 * @Description 用户服务的切面
 * @Date 2020/1/20 17:40
 * @Version 1.0
 **/
@Aspect
@Component
public class UserAspect extends AbstractLogger {
    private final static Logger logger = LogManager.getLogger(UserAspect.class);

    @Pointcut("execution(* com.jzy.web.controller.UserController.update*(..)) " +
            "|| execution(* com.jzy.web.controller.UserController.add*(..))" +
            "|| execution(* com.jzy.web.controller.UserController.modify*(..))")
    public void updatePoints() {
    }

    @Pointcut("execution(* com.jzy.web.controller.UserController.uploadUserIcon(..)) ")
    public void uploadUserIconPoints() {
    }

    /**
     * 更新用户自己的资料的日志记录
     *
     * @param jp  连接点
     * @param map 原方法返回json
     */
    @AfterReturning(returning = "map", pointcut = "updatePoints()")
    public void updateUserOwnInfoLog(JoinPoint jp, Map<String, Object> map) {
        String data = (String) map.get("data");
        if (!Constants.SUCCESS.equals(data)) {
            return;
        }

        MethodSignature signature = (MethodSignature) jp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        User user = userService.getSessionUserInfo();
        if (user != null) {
            String msg = "用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")更新了用户的资料。请求方法：" + methodName;
            logger.info(msg);
            saveLogToDatebase(msg, user, getIpAddress(jp));
        }
    }

    /**
     * 更新用户自己的资料的日志记录
     *
     * @param jp  连接点
     * @param map 原方法返回json
     */
    @AfterReturning(returning = "map", pointcut = "uploadUserIconPoints()")
    public void uploadUserIconLog(JoinPoint jp, Map<String, Object> map) {
        Map map2 = (Map) map.get("data");
        String fileName = (String) map2.get("src");
        if (!StringUtils.isEmpty(fileName)) {
            User user = userService.getSessionUserInfo();
            if (user != null) {
                String msg = "用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")上传了头像。头像文件名：" + fileName;
                logger.info(msg);
                saveLogToDatebase(msg, user, getIpAddress(jp));
            }
        }
    }
}

