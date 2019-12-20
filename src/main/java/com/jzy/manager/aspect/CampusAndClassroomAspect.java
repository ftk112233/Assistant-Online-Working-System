package com.jzy.manager.aspect;

import com.jzy.manager.constant.RedisConstants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName CampusAndClassroomAspect
 * @description 校区和教室业务方法切面
 * @date 2019/12/20 19:38
 **/
@Aspect
@Component
public class CampusAndClassroomAspect {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("execution(* com.jzy.service.impl.CampusAndClassroomServiceImpl.insert*(..)) " +
            "|| execution(* com.jzy.service.impl.CampusAndClassroomServiceImpl.update*(..))" +
            "|| execution(* com.jzy.service.impl.CampusAndClassroomServiceImpl.delete*(..))")
    public void updatePoints() {
    }

    /**
     * 在CampusAndClassroom被更新后，清空redis缓存
     * @param jp
     */
    @AfterReturning("updatePoints()")
    public void clearRedisAfterUpdate(JoinPoint jp){
        //清缓存
        String key = RedisConstants.CLASSROOMS_KEY;
        redisTemplate.expire(key, 0 ,TimeUnit.SECONDS);
    }
}
