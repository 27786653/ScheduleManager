package com.yuhi.schedule.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class tarlogJob implements Job {
	
	Logger logger=LoggerFactory.getLogger(tarlogJob.class);
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("log.....................");
	}

}
