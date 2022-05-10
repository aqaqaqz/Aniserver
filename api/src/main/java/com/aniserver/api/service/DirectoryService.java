package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.common.Const;
import com.aniserver.common.util.Utils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class DirectoryService extends BaseService {

    private Map<String, Directory> directoryMap = new HashMap<>();

    public Directory searchDirectoryList(String keyword){
        if(directoryMap.isEmpty()) initDirectoryMap();

        if("".equals(keyword)) return directoryMap.get(Const.DEFAULT_PATH);

        return search(keyword);
    }

    private void initDirectoryMap(){
        directoryMap = new HashMap<>();
        makeDirectoryMap(Const.DEFAULT_PATH);
    }

    private Directory makeDirectoryMap(String path){
        File f = Utils.file.getFileInfo(path);
        String type = getType(f);
        if(!Const.CODE_FILE_TYPE_FOLDER.equals(type) && !Const.CODE_FILE_TYPE_MOVIE.equals(type))
            return null;

        Directory temp = new Directory();
        temp.setName(f.getName());
        temp.setPath(path);
        temp.setType(getType(f));

        if(Const.CODE_FILE_TYPE_FOLDER.equals(temp.getType())){
            for(File next : f.listFiles()){
                Directory lower = makeDirectoryMap(next.getPath());
                if(lower != null)
                    temp.getLower().add(lower);
            }
        }

        if(Const.CODE_FILE_TYPE_MOVIE.equals(temp.getType())){
            for(String sub : Const.ABLE_SUBTITLE_EXTENSION){
                String extension = Utils.file.getExtension(f.getName());
                if(Utils.file.isExist(path.replaceAll("."+extension, "."+sub)))
                    temp.getSubtitle().add(sub);
            }
        }

        directoryMap.put(path, temp);
        return directoryMap.get(path);
    }

    private String getType(File f){
        if(f.isDirectory()) return Const.CODE_FILE_TYPE_FOLDER;

        String extension = Utils.file.getExtension(f.getName());
        if(Const.ABLE_MOVIE_EXTENSION.stream().anyMatch(s -> s.equals(extension)))
            return Const.CODE_FILE_TYPE_MOVIE;
        if(Const.ABLE_SUBTITLE_EXTENSION.stream().anyMatch(s -> s.equals(extension)))
            return Const.CODE_FILE_TYPE_SUBTITLE;

        return Const.CODE_FILE_TYPE_NOTHING;
    }

    private Directory search(String keywrod){
        return directoryMap.get(Const.DEFAULT_PATH);
    }
}
