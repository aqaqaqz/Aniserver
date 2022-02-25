package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.common.util.Util;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectoryService extends BaseService {

    private Map<String, Directory> directoryMap = new HashMap<>();

    public Directory getDirectory(String path){
        if(!directoryMap.containsKey(path)) {
            Directory dir = Util.file.getDirectory(path);
            directoryMap.put(path, dir);
        }

        return directoryMap.get(path);
    }

    public List<Directory> searchDirectoryList(String keyword) {
        return null;
    }
}
