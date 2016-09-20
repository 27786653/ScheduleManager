package com.yuhi.schedule.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.bcel.internal.Constants;
import com.yuhi.core.common.BaseTools;
import com.yuhi.schedule.bean.ScheduleJob;
import com.yuhi.schedule.service.ScheduleJobService;
import com.yuhi.schedule.utils.ScheduleUtils;

@Controller
public class ScheduleController{

	/** 操作成功 */
	private  static final String SUCCESSFUL_OPERATION="{\"status\":true,\"msg\":\"操作成功\"}";
	/** 非法操作 */
	private static final String ILLEGAL_OPERATION="{\"status\":false,\"msg\":\"操作非法\"}";
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@RequestMapping("/scheduleList")
	public String ScheduleList(ModelMap map){
		map.put("schedulelist", scheduleJobService.queryList());
		return "index";
	}
	
	@RequestMapping("/gotoAddschedule")
	public String gotoAddschedule(ModelMap map){
		return "add";
	}
	
	
	@RequestMapping("/insertSchedule")
	public String insertSchedule(ScheduleJob schedulejob){
		if(schedulejob!=null){
			schedulejob.setGmtCreate(new Date());
			schedulejob.setScheduleJobId(BaseTools.getUUID());
		}
		String key=scheduleJobService.insert(schedulejob);
		System.out.println(key);
		return "redirect:scheduleList.do";
	}
	
	
	@RequestMapping("/deleteSchedule")
	@ResponseBody
	public String deleteSchedule(ScheduleJob schedulejob){
		String JobGroup=schedulejob.getJobGroup();
		String JobName=schedulejob.getJobName();
		if(!StringUtils.isEmpty(JobName)&&!StringUtils.isEmpty(JobGroup)){
			scheduleJobService.delete(ScheduleUtils.getZKpath(schedulejob));
			return SUCCESSFUL_OPERATION;
		}
		return ILLEGAL_OPERATION;
	}
	
	@RequestMapping("/pauseSchedule")
	@ResponseBody
	public String pauseSchedule(ScheduleJob schedulejob){
		String JobGroup=schedulejob.getJobGroup();
		String JobName=schedulejob.getJobName();
		if(!StringUtils.isEmpty(JobName)&&!StringUtils.isEmpty(JobGroup)){
			scheduleJobService.pauseJob(ScheduleUtils.getZKpath(schedulejob));
			return SUCCESSFUL_OPERATION;
		}
		return ILLEGAL_OPERATION;
	}
	
	@RequestMapping("/resumeSchedule")
	@ResponseBody
	public String resumeSchedule(ScheduleJob schedulejob){
		String JobGroup=schedulejob.getJobGroup();
		String JobName=schedulejob.getJobName();
		if(!StringUtils.isEmpty(JobName)&&!StringUtils.isEmpty(JobGroup)){
			scheduleJobService.resumeJob(ScheduleUtils.getZKpath(schedulejob));
			return SUCCESSFUL_OPERATION;
		}
		return ILLEGAL_OPERATION;
	}
	
	@RequestMapping("/runOnce")
	@ResponseBody
	public String runOnce(ScheduleJob schedulejob){
		String JobGroup=schedulejob.getJobGroup();
		String JobName=schedulejob.getJobName();
		if(!StringUtils.isEmpty(JobName)&&!StringUtils.isEmpty(JobGroup)){
			scheduleJobService.runOnce(ScheduleUtils.getZKpath(schedulejob));
			return SUCCESSFUL_OPERATION;
		}
		return ILLEGAL_OPERATION;
	}
}
