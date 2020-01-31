package com.jzy.manager.aspect;

import com.jzy.manager.constant.Constants;
import com.jzy.model.entity.User;
import com.jzy.model.vo.UserLoginResult;
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
 * @ClassName AuthenticationAspect
 * @Author JinZhiyun
 * @Description 认证的controller的切面
 * @Date 2020/1/20 10:04
 * @Version 1.0
 **/
@Aspect
@Component
public class AuthenticationAspect extends AbstractLogger {
    private final static Logger logger = LogManager.getLogger(AuthenticationAspect.class);

    @Pointcut("execution(* com.jzy.web.controller.AuthenticationController.loginTest*(..)) ")
    public void loginTestPoints() {
    }

    @AfterReturning(returning = "map", pointcut = "loginTestPoints()")
    public void afterLoginTest(JoinPoint jp, Map<String, Object> map) {
        Object data = map.get("data");
        //原ajax返回的登录成功与否信息描述
        String returnMsg = "";
        boolean isLogin = false;
        //判断原方法是否返回success，即是否登录成功
        if (data instanceof String) {
            isLogin = Constants.SUCCESS.equals(data) || "verifyCodeCorrect".equals(data);
        }
        if (data instanceof UserLoginResult) {
            UserLoginResult result = (UserLoginResult) data;
            isLogin = result.isSuccess();
        }


        String message, operatorIp = getIpAddress(jp);

        MethodSignature signature = (MethodSignature) jp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        if (isLogin) {
            //登录成功日志
            User user = userService.getSessionUserInfo();
            if (user == null) {
                return;
            }
            message = "用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")通过" + methodName + "方法登录成功";
            saveLogToDatebase(message, user, operatorIp);
        } else {
            //登录失败日志
            message = "ip=" + operatorIp + "通过" + methodName + "方法登录失败。失败原因：" + data;
            saveLogToDatebase(message, null, operatorIp);
        }

        logger.info(message);
    }
}
