package com.jzy.web.controller;

import com.jzy.config.FilePathProperties;
import com.jzy.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author JinZhiyun
 * @ClassName BaseController
 * @Description 基础控制器，用来继承，同时提供一些基本的mvc逻辑，如登录登出。
 * 有如下内容：
 * 1、自动注入所有所需服务层接口
 * 2、设置request、response、session对象
 * 之后其他控制类只需继承此类，无需自行注入和设置
 * @Date 2019/6/4 22:39
 * @Version 1.0
 **/
@Controller
public abstract class AbstractController {
    @Autowired
    protected UserService userService;

    @Autowired
    protected StudentService studentService;

    @Autowired
    protected TeacherService teacherService;

    @Autowired
    protected ClassService classService;

    @Autowired
    protected AssistantService assistantService;

    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;

    @Autowired
    protected HashOperations<String, String, Object> hashOps;

    @Autowired
    protected ValueOperations<String, Object> valueOps;

    @Autowired
    protected ListOperations<String, Object> listOps;

    @Autowired
    protected SetOperations<String, Object> setOps;

    @Autowired
    protected ZSetOperations<String, Object> zSetOps;

    @Autowired
    protected FilePathProperties filePathProperties;
}
