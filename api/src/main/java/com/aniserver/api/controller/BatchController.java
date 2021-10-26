package com.aniserver.api.controller;

import com.aniserver.api.batch.Quartz;
import com.aniserver.api.model.db.Batch;
import com.aniserver.api.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class BatchController {

    @Autowired
    BatchService batchService;

    @GetMapping(value = "/batch/init")
    public void initBatchList() throws Exception {
        List<Batch> batchList = batchService.getAllBatchList();
        for(Batch batch : batchList){
            Quartz.addJob(batch);
        }
    }

    @GetMapping(value = "/batch/job")
    public List<String> getQuartzList(@RequestParam String groupName) {
        return Quartz.getJobIdList(groupName);
    }

    @PostMapping(value = "/batch/job")
    public String addQuartz(@RequestBody Batch info) {
        return (Quartz.addJob(info)?info.getJobName()+" add":"fail");
    }

    @DeleteMapping(value = "/batch/job")
    public void removeQuartz(@RequestBody Batch info) {
        Quartz.removeJob(info);
    }

    @PutMapping(value = "/batch/job")
    public void updateQuartz(@RequestBody Batch info) {
        Quartz.updateJobDetail(info);
    }

}