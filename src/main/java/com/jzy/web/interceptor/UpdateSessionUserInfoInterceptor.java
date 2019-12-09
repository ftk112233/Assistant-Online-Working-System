package com.jzy.web.interceptor;

import com.jzy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UpdateSessionUserInfoInterceptor
 * @description 更新session用户信息的拦截器
 * @date 2019/11/18 15:21
 **/
public class UpdateSessionUserInfoInterceptor implements HandlerInterceptor {
    private final static Logger logger = LogManager.getLogger(UpdateSessionUserInfoInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        userService.updateSessionUserInfo();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
