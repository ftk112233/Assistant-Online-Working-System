package com.jzy.manager.aspect;

import com.jzy.manager.constant.RedisConstants;
import com.jzy.service.RedisOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName QuestionAspect
 * @description 游客登录问题业务方法切面
 * @date 2019/12/20 19:20
 **/
@Aspect
@Component
public class QuestionAspect {
    @Autowired
    private RedisOperation redisOperation;

    @Pointcut("execution(* com.jzy.service.impl.QuestionServiceImpl.insert*(..)) " +
            "|| execution(* com.jzy.service.impl.QuestionServiceImpl.update*(..))" +
            "|| execution(* com.jzy.service.impl.QuestionServiceImpl.delete*(..))")
    public void updatePoints() {
    }

    /**
     * 在question被更新后，清空redis缓存
     * @param jp
     */
    @AfterReturning("updatePoints()")
    public void clearRedisAfterUpdate(JoinPoint jp){
        String key=RedisConstants.QUESTION_KEY;
        redisOperation.expireKey(key);
    }
}
