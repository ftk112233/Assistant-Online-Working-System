package com.jzy.service.impl;

import com.jzy.config.FilePathProperties;
import com.jzy.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

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

    /**
     * 清除redis指定key的缓存
     *
     * @param key redis缓存的键
     */
    protected void expireKey(String key) {
        if (!StringUtils.isEmpty(key)) {
            redisTemplate.expire(key, 0, TimeUnit.SECONDS);
        }
    }

    /**
     * 清除redis指定key的hash
     *
     * @param key redis缓存的键
     */
    protected void deleteHashByKey(String key, String hashKey) {
        if (hashOps.hasKey(key, hashKey)) {
            //缓存中有
            hashOps.delete(key, hashKey);
        }
    }
}
