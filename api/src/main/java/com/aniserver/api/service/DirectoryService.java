package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.api.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService extends BaseService {

    @Value("${system.default.path}")
    private String defPath;

    @Value("${system.default.path.down}")
    private String downPath;

    @Value("${system.default.path.torrent}")
    private String torrentPath;

    @Value("${system.default.path.divide}")
    private String dividePath;

    private List<Directory> directoryList;

    public void initDirectoryList(){
        directoryList = Util.file.scanDirectory(defPath);
    }

    private List<Directory> searchList(List<Directory> list, String keyword){
        List<Directory> newList = new ArrayList<>();
        if(list == null) return newList;

        for(Directory d : list){
            if(d.getName().indexOf(keyword) > -1){
                newList.add(d);
            }else if(d.isFolder()){
                Directory newDir = d.clone();
                newDir.setSublist(searchList(d.getSublist(), keyword));
                if(!newDir.getSublist().isEmpty())
                    newList.add(newDir);
            }
        }

        return newList;
    }

    public List<Directory> getDirectoryList(String keyword){
        if(directoryList == null) initDirectoryList();

        if(keyword.isEmpty())
            return directoryList;

        return searchList(directoryList, keyword);
    }
}
