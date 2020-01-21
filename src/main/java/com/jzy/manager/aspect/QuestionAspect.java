package com.jzy.manager.aspect;

import com.jzy.manager.constant.RedisConstants;
import com.jzy.model.entity.User;
import com.jzy.service.RedisOperation;
import com.jzy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger logger = LogManager.getLogger(QuestionAspect.class);

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private UserService userService;

    @Pointcut("execution(* com.jzy.service.QuestionService.insert*(..)) " +
            "|| execution(* com.jzy.service.QuestionService.update*(..))" +
            "|| execution(* com.jzy.service.QuestionService.delete*(..))")
    public void updatePoints() {
    }

    @Pointcut("execution(* com.jzy.service.QuestionService.listQuestions(..)) ")
    public void listQuestionsPoint() {
    }

    /**
     * 在question被更新后，清空redis缓存
     *
     * @param jp
     */
    @AfterReturning("updatePoints()")
    public void clearRedisAfterUpdate(JoinPoint jp){
        String key=RedisConstants.QUESTION_KEY;
        redisOperation.expireKey(key);
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")更新了懒癌登陆问题信息!");
        }
    }

    /**
     * 用户查询了问题信息，记录日志
     *
     * @param jp
     */
    @AfterReturning("listQuestionsPoint()")
    public void listQuestionsPoint(JoinPoint jp){
        User user = userService.getSessionUserInfo();
        if (user != null) {
            logger.info("用户(姓名=" + user.getUserRealName() + ", id=" + user.getId() + ")查询了懒癌登陆问题信息!");
        }
    }
}
