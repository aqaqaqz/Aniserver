package com.aniserver.api.batch;

import org.quartz.*;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String defaultGroupName = "adminScheduler";

    private static Scheduler scheduler;

    public static Scheduler getScheduler() throws SchedulerException {
        if(scheduler==null){
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        }
        return scheduler;
    }

    //job 추가
    public static boolean addJob(String time, String classPath, String jobId){
        return addJob(time, classPath, jobId, defaultGroupName);
    }
    public static boolean addJob(String time, String classPath, String jobId, String jobGroupName) {
        try{
            Class targetClass  = Class.forName(classPath);

            //java9부터 clazz.newInstance() deprecated
            JobDetail job = newJob(targetClass)
                    .withIdentity(jobId, jobGroupName)
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(cronSchedule(time))
                    .build();

            Scheduler scheduler = getScheduler();
            scheduler.scheduleJob(job, trigger);
        }catch(SchedulerException | ClassNotFoundException e) {
            System.out.println("message : " + e.getMessage());
            System.out.println("getStackTrace : " + e.getStackTrace());
            System.out.println("getLocalizedMessage : " + e.getLocalizedMessage());
            System.out.println("getCause : " + e.getCause());
            System.out.println("getSuppressed : " + e.getSuppressed());

            return false;
        }

        return true;
    }

    //job 삭제
    public static boolean removeJob(String jobName) {
        return removeJob(jobName, defaultGroupName);
    }
    public static boolean removeJob(String jobName, String _groupName) {
        try{
            Scheduler scheduler = getScheduler();
            scheduler.deleteJob(new JobKey(jobName, _groupName));
        }catch(SchedulerException e){
            System.out.println("removeJob : " + e.getMessage());
            return false;
        }
        return true;
    }

    //등록된 job들의 id리스트
    public static List<String> getJobIdList() {
        return getJobIdList(defaultGroupName);
    }
    public static List<String> getJobIdList(String _groupName) {
        List<String> jobList = new ArrayList();

        try{
            Scheduler scheduler = getScheduler();
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(_groupName))){
                jobList.add(jobKey.getName());
            }
        }catch(SchedulerException e){
            System.out.println("getJobIdList : " + e.getMessage());
        }
        return jobList;
    }

    //등록된 job의 시간 변경
    public static boolean updateJobTime(String jobName, String time, String jobGroupName){
        try {
            Scheduler scheduler = getScheduler();

            CronTriggerImpl cronTriggerImpl = (CronTriggerImpl) scheduler.getTrigger(new TriggerKey(jobName, jobGroupName));

            cronTriggerImpl.setCronExpression(time);
            scheduler.rescheduleJob(new TriggerKey(jobName, jobGroupName), cronTriggerImpl);
        }catch(SchedulerException | ParseException e){
            System.out.println("updateJob : " + e.getMessage());
            return false;
        }

        return true;
    }
}
