package com.yuhi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration  
public class WebAppConfig  {  
	
	
	 @Bean(name = "scheduler")  
	 public SchedulerFactoryBean getschedulerFactoryBean(){
		return new SchedulerFactoryBean();
	 }
	
	
}
