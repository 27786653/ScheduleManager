package com.yuhi.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.zookeeper.KeeperException;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.Constants;
import com.yuhi.core.zk.ZooKeeperManager;
import com.yuhi.core.zk.ZooKeeperUtil;
import com.yuhi.schedule.bean.ScheduleJob;
import com.yuhi.schedule.service.ScheduleJobService;
import com.yuhi.schedule.utils.ScheduleUtils;



/**
 * 定时任务
 *
 * Created by liyd on 12/19/14.
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
	
	
	private static final String ZK_SCHEDULE_TASK = "/pms/schedule/task";
	private ZooKeeperManager zooKeeperManager;
	
	@PostConstruct
	public void initZKManager() {
		zooKeeperManager=ZooKeeperManager.getInstance();
		zooKeeperManager.init();
	}
	
    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;
    @Override
    public void initScheduleJob() throws Exception  {
//        List<ScheduleJob> scheduleJobList = jdbcDao.queryList(Criteria.select(ScheduleJob.class));
    	List<String> children = zooKeeperManager.getZk().getChildren(ZK_SCHEDULE_TASK, false);
    	List<ScheduleJob> scheduleJobList =new ArrayList<ScheduleJob>();
    	if(!CollectionUtils.isEmpty(children)){
    		for (String string : children) {
    			String data=ZooKeeperUtil.getNodeData(zooKeeperManager.getZk(), ZK_SCHEDULE_TASK+"/"+string);
    			ScheduleJob sc=JSON.parseObject(data, ScheduleJob.class);
    			scheduleJobList.add(sc);
    		}
    	}
    	if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        try {
			for (ScheduleJob scheduleJob : scheduleJobList) {

			    CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(),
			        scheduleJob.getJobGroup());
			    //不存在，创建一个
			    if (cronTrigger == null) {
			        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			    } else {
			        //已存在，那么更新相应的定时设置
			        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public String insert(ScheduleJob scheduleJob)  {
        try {
			ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        String path=null;
        //执行插入
        try {
        	path=ZooKeeperUtil.createNodeWithRecurrence(zooKeeperManager.getZk()
        			,ZK_SCHEDULE_TASK+ScheduleUtils.getZKpath(scheduleJob)
        			,JSON.toJSONString(scheduleJob).getBytes());
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return path;
    }
    @Override
    public void update(ScheduleJob scheduleJob) {
        try {
			ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //执行 修改
        ZooKeeperUtil.updateNotedata(zooKeeperManager.getZk(),
        		ZK_SCHEDULE_TASK+ScheduleUtils.getZKpath(scheduleJob)
        		, JSON.toJSONString(scheduleJob).getBytes());
    }
    //TODO
    @Override
    public void delUpdate(ScheduleJob scheduleJob) {
        try {
			//先删除
			ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
			//再创建
			ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //数据库直接更新即可
        //执行 修改
        ZooKeeperUtil.updateNotedata(zooKeeperManager.getZk(),
        		ZK_SCHEDULE_TASK+ScheduleUtils.getZKpath(scheduleJob)
        		, JSON.toJSONString(scheduleJob).getBytes());
    }
    @Override
    public void delete(String scheduleJobpath) {
    	ScheduleJob scheduleJob = taskDataPaser(scheduleJobpath);
        //删除运行的任务
        try {
			ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
			 //删除数据
			ZooKeeperUtil.deleteNodebyPath(zooKeeperManager.getZk(), ZK_SCHEDULE_TASK+"/"+scheduleJobpath.replaceAll("/", ""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void runOnce(String scheduleJobpath) {
    	ScheduleJob scheduleJob = taskDataPaser(scheduleJobpath);
        try {
			ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void pauseJob(String scheduleJobpath) {
    	ScheduleJob scheduleJob = taskDataPaser(scheduleJobpath);
        try {
			ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //数据库更新
    }
    @Override
    public void resumeJob(String scheduleJobpath) {
    	ScheduleJob scheduleJob = taskDataPaser(scheduleJobpath);
        try {
			ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //数据库更新
    }
    @Override
    public ScheduleJob get(String scheduleJobpath) {
        ScheduleJob scheduleJob = taskDataPaser(scheduleJobpath);
        return scheduleJob;
    }
    @Override
    public List<ScheduleJob> queryList() {
        
    	List<ScheduleJob> scheduleJobList=null;
        try {
        	List<String> children = zooKeeperManager.getZk().getChildren(ZK_SCHEDULE_TASK, false);
			scheduleJobList = new ArrayList<ScheduleJob>();
        	if(!CollectionUtils.isEmpty(children)){
        		for (String string : children) {
        			String data=ZooKeeperUtil.getNodeData(zooKeeperManager.getZk(), ZK_SCHEDULE_TASK+"/"+string);
        			ScheduleJob sc=JSON.parseObject(data, ScheduleJob.class);
        			scheduleJobList.add(sc);
        		}
        	}
//            for (ScheduleJob vo : scheduleJobList) {
//
//                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
//                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
//                if (CollectionUtils.isEmpty(triggers)) {
//                    continue;
//                }
//
//                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
//                Trigger trigger = triggers.iterator().next();
//                scheduleJobVo.setJobTrigger(trigger.getKey().getName());
//
//                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//                vo.setStatus(triggerState.name());
//
//                if (trigger instanceof CronTrigger) {
//                    CronTrigger cronTrigger = (CronTrigger) trigger;
//                    String cronExpression = cronTrigger.getCronExpression();
//                    vo.setCronExpression(cronExpression);
//                }
//            }
        } catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return scheduleJobList;
    }
    @Override
    public List<ScheduleJob> queryExecutingJobList() {
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                ScheduleJob job = new ScheduleJob();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setJobTrigger(trigger.getKey().getName());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
            return jobList;
        } catch (SchedulerException e) {
            //演示用，就不处理了
            return null;
        }

    }
    
    /**
     * 序列化数据成任务对象
     * @param scheduleJobpath
     * @return
     */
	public ScheduleJob taskDataPaser(String scheduleJobpath){
		ScheduleJob scheduleJob=null;
		try {
			String path=ZK_SCHEDULE_TASK+scheduleJobpath;
			String datastr = ZooKeeperUtil.getNodeData(zooKeeperManager.getZk(),path);
			scheduleJob = JSON.parseObject(datastr, ScheduleJob.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return scheduleJob;
	}
}
