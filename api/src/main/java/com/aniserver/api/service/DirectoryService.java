package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DirectoryService extends BaseService {

    private List<Directory> directoryList;

    public void initDirectoryList(){
        directoryList = Util.file.scanDirectory(Const.DEFAULT_PATH);
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

    public String getQuarterByTitle(String title){
        Pattern p = Pattern.compile(Const.ANIMATION_QUARTER_PATTERN);
        for(Directory d : getDirectoryList("")) {
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
}
