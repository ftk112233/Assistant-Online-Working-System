package com.jzy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName HomeController
 * @description 主页的一些页面的跳转
 * @date 2019/11/19 12:57
 **/
@Controller
public class HomeController extends AbstractController{
    /**
     * 跳转控制台
     *
     * @return
     */
    @RequestMapping("/console")
    public String console() {
        return "home/console";
    }
}
