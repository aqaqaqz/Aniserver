package com.aniserver.api.controller;

import com.aniserver.api.exception.EmptyParamException;
import com.aniserver.api.model.Directory;
import com.aniserver.api.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class DirectoryController {

    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/directory")
    public List<Directory> getList(@RequestParam(defaultValue="") String keyword) throws IOException {
        return directoryService.getDirectoryList(keyword);
    }

    @GetMapping(value = "/directory/scan")
    public void scan() throws IOException {
        directoryService.initDirectoryList();
    }

    @PutMapping(value = "/directory/move")
    public void move(@RequestBody List<String> dirList) throws EmptyParamException, IOException {
        //directoryService.movePath(dirList);
    }
}
