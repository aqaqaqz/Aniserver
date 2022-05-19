package com.aniserver.api.controller;

import com.aniserver.api.exception.InvalidPageException;
import com.aniserver.api.model.Directory;
import com.aniserver.api.model.Result;
import com.aniserver.api.service.DirectoryService;
import com.aniserver.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*")
@RequestMapping("api")
@RestController
public class DirectoryController {

    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/directory/path")
    public ResponseEntity<?> searchDirectoryListUsePath(@RequestParam(defaultValue="") String path) throws InvalidPageException {
        Directory data = directoryService.searchDirectoryListUsePath(Const.DEFAULT_PATH + path);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping(value = "/directory/recent")
    public ResponseEntity<?> searchDirectoryListRecent() throws InvalidPageException {
        List<Directory> data = directoryService.searchDirectoryListRecent();
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

    /**
     * 정해진 용량대로 스트리밍
     */
    @GetMapping(value = "/directory/streaming")
    public ResponseEntity<ResourceRegion> vidoeRegionFileName(@RequestParam String path, @RequestHeader HttpHeaders headers) throws IOException {
        String fileFullPath = Const.DEFAULT_PATH + path;
        Resource resource = new FileSystemResource(fileFullPath);
        final long chunkSize = 1024 * 1024 * 3;
        long contentLength = resource.contentLength();
        ResourceRegion region;
        try {
            HttpRange httpRange = headers.getRange().stream().findFirst().get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            region = new ResourceRegion(resource, start, rangeLength);
        } catch (Exception e) {
            long rangeLength = Long.min(chunkSize, contentLength);
            region = new ResourceRegion(resource, 0, rangeLength);
        }
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .header("Accept-Ranges", "bytes")
                .eTag(path) // IE 부분 호출을 위해서 설정
                .body(region);
    }

    /**
     * 정해진 용량대로 스트리밍
     */
    @GetMapping(value = "/directory/thumbnail")
    public ResponseEntity<Resource> displayThumbnail(@RequestParam String name){
        final String thumbnailPath = Const.THUMBNAIL_PATH+name.replace(".mp4", ".png");
        Resource resource = new FileSystemResource(thumbnailPath);
        if(!resource.exists())
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        Path filePath = null;

        try{
            filePath = Paths.get(thumbnailPath);
            headers.add("context-type", Files.probeContentType(filePath));
        }catch(IOException e){
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
}
