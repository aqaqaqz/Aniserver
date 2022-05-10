package com.aniserver.api.controller;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api")
@RestController
public class DirectoryController {

    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/search/directory")
    public ResponseEntity<?> searchDirectoryList(@RequestParam(defaultValue="") String keyword) {
        Directory data = directoryService.searchDirectoryList(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
