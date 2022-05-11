package com.aniserver.api.controller;

import com.aniserver.api.exception.InvalidPageException;
import com.aniserver.api.model.Directory;
import com.aniserver.api.model.Result;
import com.aniserver.api.service.DirectoryService;
import com.aniserver.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api")
@RestController
public class DirectoryController {

    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/directory/path")
    public ResponseEntity<?> searchDirectoryListUsePath(@RequestParam(defaultValue="") String path) throws InvalidPageException {
        Directory data = directoryService.searchDirectoryListUsePath(path);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping(value = "/directory/search")
    public ResponseEntity<?> searchDirectoryListKeyword(@RequestParam(defaultValue="") String keyword) {
        Directory data = directoryService.searchDirectoryListUseKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping(value = "/directory/divide")
    public ResponseEntity<?> divideDirectoryList(@RequestParam(defaultValue="") List<String> pathList) {
        int successCnt = directoryService.divideDirectoryList(pathList);
        return ResponseEntity.status(HttpStatus.OK).body(new Result(pathList.size(), successCnt));
    }

}
