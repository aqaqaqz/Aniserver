package com.aniserver.api.controller;

import com.aniserver.api.batch.Batch;
import com.aniserver.api.model.QuartzInfo;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class BatchController {

    public static void initDefaultBatch(){
        QuartzInfo info = new QuartzInfo();
        info.setGroupName("aniserver");
        info.setJobName("ohys");
        info.setType("class");
        info.setTarget("com.aniserver.api.batch.Job.OhysDownload");
        info.setDescription("add ohys download");
        info.setTime("0/10 * * * * ?");
        info.setParams(""
            .concat("{")
                .concat("\"url\":\"https://ohys.nl/tt/json.php?dir=disk&p=0\",")
                .concat("\"downUrl\":\"https://ohys.nl/tt/\",")
                .concat("\"downPath\":\"C:/test/\"")
            .concat("}")
        );
        Batch.addJob(info);
    }


    @GetMapping(value = "/quartz/job")
    public List<String> getQuartzList(@RequestParam String groupName) {
        return Batch.getJobIdList(groupName);
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