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

    @GetMapping(value = "/directory/scan")
    public void scanPath() throws IOException {
        directoryService.scanPath();
    }

    @PutMapping(value = "/directory/move")
    public void movePath(@RequestBody List<String> dirList) throws EmptyParamException, IOException {
        directoryService.movePath(dirList);
    }

    @PostMapping(value = "/directory/new")
    public void newDirectory(@RequestBody List<Directory> dirList) throws EmptyParamException, IOException {
        directoryService.newDirectory(dirList);
    }
}
