package com.aniserver.api.controller;

import com.aniserver.api.batch.Quartz;
import com.aniserver.api.model.Batch;
import com.aniserver.api.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class DirectoryController {
    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/directory/list")
    public String addQuartz(@RequestBody Batch info) {
        return (Quartz.addJob(info)?info.getJobName()+" add":"fail");
    }
}
