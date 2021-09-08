package com.aniserver.api.controller;

import com.aniserver.api.batch.Batch;
import com.aniserver.api.model.QuartzInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class BatchController {
/*
    BatchController(){
        QuartzInfo info = new QuartzInfo();
        info.setGroupName("aniserver");
        info.setJobName("ohys");
        info.setTarget("com.aniserver.api.batch.Job.OhysDownload");
        info.setTime("10/* * * * * ?");
        Batch.addJob(info);
    }
*/

    @GetMapping(value = "/quartz/job")
    public List<QuartzInfo> getQuartzList(@RequestBody QuartzInfo info) {
        return Batch.getJobIdList(info);
    }

    @PostMapping(value = "/quartz/job")
    public String addQuartz(@RequestBody QuartzInfo info) {
        return (Batch.addJob(info)?info.getJobName()+" add":"fail");
    }

    @DeleteMapping(value = "/quartz/job")
    public void removeQuartz(@RequestBody QuartzInfo info) {
        Batch.removeJob(info);
    }

    @PutMapping(value = "/quartz/job/detail")
    public void updateQuartz(@RequestBody QuartzInfo info) {
        Batch.updateJobDetail(info);
    }

    @PutMapping(value = "/quartz/job/state")
    public void updateJobState(@RequestBody QuartzInfo info) {
        Batch.updateJobState(info);
    }

}