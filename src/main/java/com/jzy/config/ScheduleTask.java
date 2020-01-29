package com.jzy.config;

import com.jzy.web.controller.ToolboxController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @ClassName ScheduleTask
 * @Author JinZhiyun
 * @Description 基于springboot的定时任务
 * @Date 2020/1/28 10:19
 * @Version 1.0
 **/
@Configuration
@EnableScheduling
public class ScheduleTask {
    private final static Logger logger = LogManager.getLogger(ScheduleTask.class);

    /**
     * 每天早上2点执行ToolboxController的cache自动清理
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void clearToolboxControllerCache() {
        ToolboxController.clearCache();
        logger.info("ToolboxController的cache定时清理执行！----");
    }
}
