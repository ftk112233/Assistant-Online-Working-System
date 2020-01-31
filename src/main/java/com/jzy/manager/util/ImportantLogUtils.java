package com.jzy.manager.util;

import com.jzy.model.entity.ImportantLog;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName ImportantLogUtils
 * @Author JinZhiyun
 * @Description 持久化到数据库的重要日志工具类
 * @Date 2020/1/31 11:02
 * @Version 1.0
 **/
public class ImportantLogUtils {
    private ImportantLogUtils() {
    }

    public static boolean isValidLogMessage(String message) {
        return !StringUtils.isEmpty(message) && message.length() <= 1000;
    }

    public static boolean isValidLogLevel(String level) {
        return StringUtils.isEmpty(level) || level.length() <= 20;
    }

    public static boolean isValidLogOperatorId(Long operatorId) {
        return true;
    }

    public static boolean isValidLogOperatorIp(String operatorIp) {
        return StringUtils.isEmpty(operatorIp) || MyStringUtils.isIpAddress(operatorIp);
    }

    public static boolean isValidLogRemark(String remark) {
        return remark == null || remark.length() <= 500;
    }

    /**
     * 输入的ImportantLog是否合法
     *
     * @param log 输入的ImportantLog对象
     * @return
     */
    public static boolean isValidImportantLogInfo(ImportantLog log) {
        return log != null && isValidLogMessage(log.getMessage()) && isValidLogLevel(log.getLevel())
                && isValidLogOperatorId(log.getOperatorId()) && isValidLogOperatorIp(log.getOperatorIp())
                && isValidLogRemark(log.getRemark());
    }

    public static boolean isValidImportantLogUpdateInfo(ImportantLog log) {
        return isValidImportantLogInfo(log);
    }
}
