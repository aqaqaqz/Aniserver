package com.aniserver.api.batch.Job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class OhysDownload implements Job {
    private String msg = "OhysDownload 실행되었습니다.";

    public void setMsg(String _msg){
        msg = _msg;
    }

    public String getMsg(){
        return msg;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(msg);
    }
}
