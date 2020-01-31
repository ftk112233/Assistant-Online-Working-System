package com.jzy.manager.aspect;

import com.jzy.manager.util.ImportantLogUtils;
import com.jzy.manager.util.ShiroUtils;
import com.jzy.model.LogLevelEnum;
import com.jzy.model.entity.ImportantLog;
import com.jzy.model.entity.User;
import com.jzy.service.ImportantLogService;
import com.jzy.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AbstractLogger
 * @Author JinZhiyun
 * @Description 抽象的日志管理器
 * @Date 2020/1/31 12:35
 * @Version 1.0
 **/
@Aspect
@Component
public abstract class AbstractLogger {
    @Autowired
    protected UserService userService;

    @Autowired
    protected ImportantLogService importantLogService;

    /**
     * 持久化日志，日志级别默认——info，备注为空
     *
     * @param msg  日志消息正文
     * @param user 触发日志事件的用户
     * @param ip   触发日志事件的客户端ip
     * @return 记录日志成功？
     */
    protected boolean saveLogToDatebase(String msg, User user, String ip) {
        return saveLogToDatebase(msg, LogLevelEnum.INFO, user, ip);
    }


    /**
     * 持久化日志，备注为空
     *
     * @param msg   日志消息正文
     * @param level 日志级别
     * @param user  触发日志事件的用户
     * @param ip    触发日志事件的客户端ip
     * @return 记录日志成功？
     */
    protected boolean saveLogToDatebase(String msg, LogLevelEnum level, User user, String ip) {
        return saveLogToDatebase(msg, level, user, ip, null);
    }

    /**
     * 持久化日志
     *
     * @param msg    日志消息正文
     * @param level  日志级别
     * @param user   触发日志事件的用户
     * @param ip     触发日志事件的客户端ip
     * @param remark 备注
     * @return 记录日志成功？
     */
    protected boolean saveLogToDatebase(String msg, LogLevelEnum level, User user, String ip, String remark) {
        ImportantLog log = new ImportantLog();
        log.setMessage(msg);
        log.setLevel(level.getLevel());
        if (user != null) {
            log.setOperatorId(user.getId());
        }
        log.setOperatorIp(ip);
        log.setRemark(remark);
        //将该重要日志记录持久化到数据库
        if (ImportantLogUtils.isValidImportantLogUpdateInfo(log)) {
            importantLogService.insertOneImportantLog(log);
            return true;
        }
        return false;
    }

    /**
     * 获取增强方法的request，以获取调用该方法的客户端ip
     *
     * @param jp 连接点
     * @return 客户端ip地址
     */
    protected String getIpAddress(JoinPoint jp){
        String ip = null;

        Object[] args = jp.getArgs();
        //获得request参数以获得请求的客户端ip
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) arg;
                ip = ShiroUtils.getClientIpAddress(request);
            }
        }
        return ip;
    }

}
