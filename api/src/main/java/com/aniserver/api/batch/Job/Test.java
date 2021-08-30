package com.aniserver.api.batch.Job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Test implements Job {
    public Test() {
        System.out.println("TestJob created");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("TestJob execute~");
    }
}
