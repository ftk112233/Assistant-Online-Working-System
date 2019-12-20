package com.jzy.web.interceptor;

/**
 * @ClassName TokenInterceptor
 * @Author JinZhiyun
 * @Description Token拦截器
 * @Date 2019/5/11 18:51
 * @Version 1.0
 **/

import com.jzy.manager.util.ShiroUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    private final static Logger logger = LogManager.getLogger(TokenInterceptor.class);

    /**
     * @return boolean
     * @Author JinZhiyun
     * @Description 实现Token相应注解的功能
     * @Date 13:04 2019/5/12
     * @Param [request, response, handler]
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Session session = ShiroUtils.getSession();
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    session.setAttribute("token", UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        logger.warn(session.getAttribute("userId") + "重复提交了表单");
                        response.sendRedirect(request.getContextPath() + "/formRepeatSubmit");
                        return false;
                    }
                    session.removeAttribute("token");
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * @return boolean
     * @Author JinZhiyun
     * @Description 判断表单是否重复提交
     * @Date 13:05 2019/5/12
     * @Param [request]
     **/
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) ShiroUtils.getSession().getAttribute("token");
        System.out.println(serverToken);
        if (serverToken == null) {
            return true;
        }
        String clientToken = request.getParameter("token");
        System.out.println(clientToken);
        if (clientToken == null) {
            return true;
        }
        if (!serverToken.equals(clientToken)) {
            return true;
        }
        return false;
    }
}

