package com.jzy.manager.aspect;

import com.jzy.manager.constant.Constants;
import com.jzy.model.entity.User;
import com.jzy.model.vo.UserLoginResult;
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
 * @ClassName AuthenticationAspect
 * @Author JinZhiyun
 * @Description 认证的controller的切面
 * @Date 2020/1/20 10:04
 * @Version 1.0
 **/
@Aspect
@Component
public class AuthenticationAspect {
    private final static Logger logger = LogManager.getLogger(AuthenticationAspect.class);

    @Autowired
    private UserService userService;

    @Pointcut("execution(* com.jzy.web.controller.AuthenticationController.loginTest*(..)) ")
    public void loginTestPoints() {
    }

    @Pointcut("execution(* com.jzy.web.controller.AuthenticationController.index(..)) ")
    public void indexPoint() {
    }

    @AfterReturning(pointcut = "indexPoint()")
    public void visitIndex(JoinPoint jp) {
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")访问主页!");
        }
    }

    @AfterReturning(returning = "map", pointcut = "loginTestPoints()")
    public void afterLoginTest(JoinPoint jp, Map<String, Object> map) {
        Object data = map.get("data");
        boolean isLogin = false;
        if (data instanceof String) {
            isLogin = Constants.SUCCESS.equals(data);
        }
        if (data instanceof UserLoginResult) {
            isLogin = ((UserLoginResult) data).isSuccess();
        }

        if (isLogin) {
            //登录成功日志
            User user = userService.getSessionUserInfo();
            if (user != null) {
                logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")登录成功");
            }
        }
    }
}
