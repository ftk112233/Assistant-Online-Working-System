package com.jzy.web.interceptor;

import com.jzy.service.UserService;
import com.jzy.web.controller.ToolboxController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName ToolboxUploadCacheInterceptor
 * @Author JinZhiyun
 * @Description 工具箱用户上传文件缓存的清理，每次访问index时清理
 * @Date 2019/11/28 9:48
 * @Version 1.0
 **/
public class ToolboxUploadCacheInterceptor implements HandlerInterceptor {
    private final static Logger logger = Logger.getLogger(ToolboxUploadCacheInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long id=userService.getSessionUserInfo().getId();
        ToolboxController.studentListUploadByUserCache.remove(id);
        ToolboxController.studentListForSeatTableUploadByUserCache.remove(id);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
