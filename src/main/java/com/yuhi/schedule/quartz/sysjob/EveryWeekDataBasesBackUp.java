package com.yuhi.schedule.quartz.sysjob;

import java.io.OutputStream;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuhi.core.common.DatabaseBackup;
import com.yuhi.schedule.quartz.tarlogJob;
/**
 * 备份数据库
 * @author 李森林
 *
 */
public class EveryWeekDataBasesBackUp implements Job {

	private static final String BinPath = "H:\\Mysql\\bin";
	Logger logger=LoggerFactory.getLogger(tarlogJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		DatabaseBackup  backup=new DatabaseBackup(BinPath, "root", "root");
		String dbname="test2";
		Calendar calendar=Calendar.getInstance();
		String desc="D://"+calendar.get(Calendar.YEAR)+
		calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH)+"//"+dbname+".sql";
		backup.backup(desc, dbname);
	}
	
	
}
