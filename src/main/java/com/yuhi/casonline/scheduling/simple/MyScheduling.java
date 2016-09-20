package com.yuhi.casonline.scheduling.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务
 * @author 李森林
 *
 */
@Configuration
@EnableScheduling 
public class MyScheduling {

    private final Logger logger = LoggerFactory.getLogger(MyScheduling.class);

    @Scheduled(cron = "0/20 * * * * ?") // 20秒/次
    public void scheduler() {
        logger.debug("scheduled is start... ");
        logger.info("scheduled is start... ");
        logger.error("scheduled is start... ");
    }

}