package com.jzy.service.impl;

import com.jzy.config.FilePathProperties;
import com.jzy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName AbstractServiceImpl
 * @description 业务实现类的基类
 * @date 2019/11/14 23:28
 **/
public abstract class AbstractServiceImpl {
    @Autowired
    protected UserService userService;

    @Autowired
    protected StudentService studentService;

    @Autowired
    protected TeacherService teacherService;

    @Autowired
    protected ClassService classService;

    @Autowired
    protected StudentAndClassService studentAndClassService;

    @Autowired
    protected AssistantService assistantService;

    @Autowired
    protected CampusAndClassroomService campusAndClassroomService;

    @Autowired
    protected RoleAndPermissionService roleAndPermissionService;

    @Autowired
    protected MissLessonStudentService missLessonStudentService;

    @Autowired
    protected QuestionService questionService;

    @Autowired
    protected UsefulInformationService usefulInformationService;

    @Autowired
    protected UserMessageService userMessageService;

    @Autowired
    protected RedisOperation redisOperation;

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

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
