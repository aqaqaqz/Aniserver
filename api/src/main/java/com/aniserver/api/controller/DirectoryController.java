package com.aniserver.api.controller;

import com.aniserver.api.exception.EmptyParamException;
import com.aniserver.api.model.Directory;
import com.aniserver.api.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class DirectoryController {

    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/search/directory")
    public List<Directory> getSearchDirectoryList(@RequestParam(defaultValue="") String keyword) {
        return directoryService.getSearchDirectoryList(keyword);
    }

    @GetMapping(value = "/directory")
    public Directory getDirectoryList(@RequestParam(defaultValue="") String path) {
        return directoryService.getDirectoryList(path);
    }

    @PostMapping(value = "/directory/scan")
    public void scan() {
        directoryService.initDirectoryList();
    }

    @PutMapping(value = "/directory/move")
    public void move(@RequestBody List<String> dirList) throws EmptyParamException {
        //directoryService.movePath(dirList);
    }
}
