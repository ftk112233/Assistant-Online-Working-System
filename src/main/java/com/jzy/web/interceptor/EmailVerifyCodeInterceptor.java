package com.jzy.web.interceptor;


import com.jzy.manager.aspect.AbstractLogger;
import com.jzy.manager.constant.SessionConstants;
import com.jzy.manager.util.ShiroUtils;
import com.jzy.model.LogLevelEnum;
import com.jzy.model.vo.EmailVerifyCodeSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName EmailVerifyCodeInterceptor
 * @description 邮箱验证码校验拦截器
 * @date 2019/10/11 12:45
 **/
public class EmailVerifyCodeInterceptor extends AbstractLogger implements HandlerInterceptor {
    private final static Logger logger = LogManager.getLogger(EmailVerifyCodeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        EmailVerifyCodeSession emailVerifyCodeSession = (EmailVerifyCodeSession) request.getSession().getAttribute(SessionConstants.USER_EMAIL_SESSION_KEY);
        if (!emailVerifyCodeSession.isAuth()) {
            String msg = "邮箱验证码校验出错，疑似绕过验证码攻击";
            logger.error(msg);
            saveLogToDatebase(msg, LogLevelEnum.ERROR, userService.getSessionUserInfo(), ShiroUtils.getClientIpAddress(request));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
