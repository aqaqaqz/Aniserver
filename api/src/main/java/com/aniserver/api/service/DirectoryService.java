package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DirectoryService extends BaseService {

    private List<Directory> directoryList;
    private Map<String, Directory> directoryMap;

    public void initDirectoryList(){
        directoryMap = new HashMap<>();
        directoryList = Util.file.scanDirectory("", directoryMap);
    }

    private List<Directory> getSearchDirectoryList(List<Directory> list, String keyword){
        List<Directory> newList = new ArrayList<>();
        if(list == null) return newList;

        for(Directory d : list){
            if(d.getName().indexOf(keyword) > -1){
                newList.add(d);
            }else if(d.isFolder()){
                Directory newDir = d.clone();
                newDir.setSublist(getSearchDirectoryList(d.getSublist(), keyword));
                if(!newDir.getSublist().isEmpty())
                    newList.add(newDir);
            }
        }

        return newList;
    }

    public List<Directory> getSearchDirectoryList(String keyword){
        if(directoryList == null) initDirectoryList();

        if(keyword.isEmpty())
            return directoryList;

        return getSearchDirectoryList(directoryList, keyword);
    }

    public String getQuarterByTitle(String title){
        Pattern p = Pattern.compile(Const.ANIMATION_QUARTER_PATTERN);
        for(Directory d : getSearchDirectoryList("")) {
            Matcher matcher = p.matcher(d.getName());
            if (!matcher.find()) continue;

            if(hasTitle(d.getSublist(), title)) return d.getName();
        }
        return Const.EMPTY;
    }

    private boolean hasTitle(List<Directory> list, String title){
        boolean find = false;

        for(Directory d : list){
            find |= title.equals(d.getName());
        }

        return find;
    }

    public Directory getDirectoryList(String path){
        return directoryMap.get(path);
    }
}
