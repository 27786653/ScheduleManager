package com.yuhi.schedule.service;

import java.util.List;

import com.yuhi.schedule.bean.ScheduleJob;



public interface ScheduleJobService {

    /**
     * 初始化定时任务
     */
    public void initScheduleJob() throws Exception;

    /**
     * 新增
     * 
     * @param scheduleJobVo
     * @return
     */
    public String insert(ScheduleJob scheduleJob);

    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     * 
     * @param scheduleJobVo
     */
    public void update(ScheduleJob scheduleJob);

    /**
     * 删除重新创建方式
     * 
     * @param scheduleJobVo
     */
    public void delUpdate(ScheduleJob scheduleJob);

    /**
     * 删除
     * 
     * @param scheduleJobId
     */
    public void delete(String scheduleJobpath);

    /**
     * 运行一次任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void runOnce(String scheduleJobpath);

    /**
     * 暂停任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void pauseJob(String scheduleJobpath);

    /**
     * 恢复任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void resumeJob(String scheduleJobpath);

    /**
     * 获取任务对象
     * 
     * @param scheduleJobId
     * @return
     */
    public ScheduleJob get(String scheduleJobpath);

    /**
     * 查询任务列表
     * 
     * @param scheduleJobVo
     * @return
     */
    public List<ScheduleJob> queryList();

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
    public List<ScheduleJob> queryExecutingJobList();

}
