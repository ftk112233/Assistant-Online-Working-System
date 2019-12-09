package com.jzy.web.controller;

import com.google.code.kaptcha.Producer;
import com.jzy.manager.constant.SessionConstants;
import com.jzy.manager.util.ShiroUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName CodeController
 * @description 所有验证码相关服务（图形验证码、邮箱验证码。。。）的控制器
 * 这些请求应该在shiro中配置为匿名访问
 * @date 2019/11/14 14:35
 **/
@Controller
public class CodeController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(CodeController.class);

    @Autowired
    private Producer captchaProducer;

    @RequestMapping("/kaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Session session = ShiroUtils.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        session.setAttribute(SessionConstants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
