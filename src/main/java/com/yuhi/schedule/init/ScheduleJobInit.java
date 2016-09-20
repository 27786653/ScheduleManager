package com.yuhi.schedule.init;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuhi.schedule.service.ScheduleJobService;

@Component
public class ScheduleJobInit {
	
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInit.class);

    @Autowired
    private ScheduleJobService jobService; 
    
	@PostConstruct
	public void initScgeduleAllJob(){
		
		if(LOG.isDebugEnabled()){
			LOG.info("initScgeduleAllJob...");
		}
		//初始化已有任务
		try {
			jobService.initScheduleJob();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void PreDestroyScgeduleAllJob(){
		if(LOG.isDebugEnabled()){
			LOG.info("ScgeduleJob is Destroy...");
		}
	}
	
	
}
