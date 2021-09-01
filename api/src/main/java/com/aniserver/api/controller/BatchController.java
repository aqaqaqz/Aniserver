package com.aniserver.api.controller;

import com.aniserver.api.batch.QuartzTest;
import com.aniserver.api.model.QuartzInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class BatchController {

    @GetMapping(value = "/quartz")
    public List<String> getQuartzList(@RequestBody QuartzInfo info) {
        return QuartzTest.getJobIdList(info);
    }

    @PostMapping(value = "/quartz")
    public String addQuartz(@RequestBody QuartzInfo info) {
        return (QuartzTest.addJob(info)?info.getJobName()+" add":"fail");
    }

    @DeleteMapping(value = "/quartz")
    public void removeQuartz(@RequestBody QuartzInfo info) {
        QuartzTest.removeJob(info);
    }

    @PutMapping(value = "/quartz/detail")
    public void updateQuartz(@RequestBody QuartzInfo info) {
        QuartzTest.updateJobDetail(info);
    }

    @PutMapping(value = "/quartz/state")
    public void updateJobState(@RequestBody QuartzInfo info) {
        QuartzTest.updateJobState(info);
    }

}