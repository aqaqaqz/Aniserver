package com.aniserver.api.controller;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.DirectoryService;
import com.aniserver.common.Const;
import com.aniserver.common.exception.DirectoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api")
@RestController
public class DirectoryController {

    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/search/directory")
    public ResponseEntity<?> getSearchDirectoryList(@RequestParam(defaultValue="") String keyword) {
        try {
            List<Directory> data = directoryService.getDirectory(keyword);
            return ResponseEntity.status(HttpStatus.OK).body(data);
        }catch(DirectoryException e){
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
