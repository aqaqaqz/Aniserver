package com.aniserver.api.batch;

import com.aniserver.api.model.QuartzInfo;
import org.quartz.*;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.text.ParseException;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String defaultGroupName = "adminScheduler";

    private static Scheduler scheduler;

    public static JobDataMap convertJobDataMap(Map<String, Object> params){
        JobDataMap m = new JobDataMap();
        for(String key : params.keySet()){
            m.put(key, params.get(key));
        }

        return m;
    }

    public static Scheduler getScheduler() throws SchedulerException {
        if(scheduler==null){
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        }
        return scheduler;
    }

    //public static boolean addJob(String time, String target, String type, String jobId, String jobGroupName, JobDataMap params) {
    public static boolean addJob(QuartzInfo info){
        String type = info.getType();
        if("class".equals(type)) return addJobClass(info);
        if("procedure".equals(type)) return addJobProcedure(info);

        return false;
    }

    private static boolean addJobClass(QuartzInfo info) {
        try{
            Class targetClass  = Class.forName(info.getTarget());

            JobDetail job = newJob(targetClass)
                    .withIdentity(info.getJobName(), info.getJobGroupName())
                    .withDescription(info.getDescription())
                    .setJobData(convertJobDataMap(info.getParams()))
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity(info.getJobName()+"Trigger", info.getJobGroupName())
                    .withSchedule(cronSchedule(info.getTime()))
                    .build();

            Scheduler scheduler = getScheduler();
            scheduler.scheduleJob(job, trigger);
        }catch(SchedulerException | ClassNotFoundException e) {
            System.out.println("message : " + e.getMessage());
            return false;
        }

        return true;
    }
    private static boolean addJobProcedure(QuartzInfo info) {
        return true;
    }

    //job 삭제
    public static boolean removeJob(QuartzInfo info) {
        try{
            String jobName = info.getJobName();
            String groupName = info.getJobGroupName();
            if(groupName == null) groupName = defaultGroupName;

            Scheduler scheduler = getScheduler();
            scheduler.deleteJob(new JobKey(jobName, groupName));
        }catch(SchedulerException e){
            System.out.println("removeJob : " + e.getMessage());
            return false;
        }
        return true;
    }

    //job 일시정지
    public static boolean updateJobState(QuartzInfo info) {
        try{
            String jobName = info.getJobName();
            String groupName = info.getJobGroupName();
            if(groupName == null) groupName = defaultGroupName;

            Scheduler scheduler = getScheduler();
            if("pause".equals(info.getState()))
                scheduler.pauseJob(new JobKey(jobName, groupName));
            else if("resume".equals(info.getState()))
                scheduler.resumeJob(new JobKey(jobName, groupName));
        }catch(SchedulerException e){
            System.out.println("pauseJob : " + e.getMessage());
            return false;
        }
        return true;
    }

    //등록된 job들의 id리스트
    public static List<String> getJobIdList(QuartzInfo info) {
        List<String> jobList = new ArrayList();

        String groupName = info.getJobGroupName();
        if(groupName == null) groupName = defaultGroupName;
        try{
            Scheduler scheduler = getScheduler();
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))){
                jobList.add(jobKey.getName());
            }
        }catch(SchedulerException e){
            System.out.println("getJobIdList : " + e.getMessage());
        }
        return jobList;
    }

    //등록된 job의 업데이트
    public static boolean updateJobDetail(QuartzInfo info){
        try {
            String triggerName = info.getJobName() + "Trigger";
            String groupName = info.getJobGroupName();
            if(groupName == null) groupName = defaultGroupName;

            Scheduler scheduler = getScheduler();

            CronTriggerImpl cronTriggerImpl = (CronTriggerImpl) scheduler.getTrigger(new TriggerKey(triggerName, groupName));

            if(!ObjectUtils.isEmpty(info.getTime())) cronTriggerImpl.setCronExpression(info.getTime());
            if(!ObjectUtils.isEmpty(info.getParams())) cronTriggerImpl.setJobDataMap(convertJobDataMap(info.getParams()));
            if(!ObjectUtils.isEmpty(info.getDescription())) cronTriggerImpl.setDescription(info.getDescription());

            scheduler.rescheduleJob(new TriggerKey(triggerName, groupName), cronTriggerImpl);
        }catch(SchedulerException | ParseException e){
            System.out.println("updateJob : " + e.getMessage());
            return false;
        }

        return true;
    }
}
