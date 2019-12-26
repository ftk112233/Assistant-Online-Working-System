package com.jzy.manager.util;

import com.jzy.manager.constant.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName CookieUtils
 * @description Cookie工具类
 * @date 2019/11/18 13:04
 **/
public class CookieUtils {
    private CookieUtils(){}

    /**
     * 设置CSRFToken到session和cookie
     *
     * @param request
     * @param response
     */
    public static void setCSRFTokenCookieAndSession(HttpServletRequest request, HttpServletResponse response){
        String authorization = UUID.randomUUID().toString();
        ShiroUtils.setSessionAttribute(Constants.CSRF_NUMBER,authorization);
        Cookie cookie = new Cookie(Constants.CSRF_NUMBER, authorization);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath(request.getContextPath()+"/");
        response.addCookie(cookie);
    }
}
