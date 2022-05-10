package com.aniserver.common.util.utils;

import com.aniserver.api.model.Directory;
import com.aniserver.common.Const;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {
    public boolean isFolder(){
        return true;
    }

    public Directory getDirectory(String path) {
        Directory dir = new Directory();
        dir.setSublist(new ArrayList<>());

        File files = new File(Const.DEFAULT_PATH+path);
        if(files.isFile()){
            dir.setType(Const.FILE_TYPE_FOLDER);
            for (File f : files.listFiles())
                dir.getSublist().add(getDirectory(path+"/"+f.getName()));
        }else{
            String type = getFileType(files);
            if(!Const.FILE_TYPE_MOVIE.equals(type)) return null;

            dir.setType(Const.FILE_TYPE_MOVIE);
            // 자막목록 작성 필요
            // dir.setSubtitle();
        }
        dir.setName(files.getName());
        dir.setPath(path);

        return dir;
    }

    private String getFileType(File file){
        if(file.isFile()) return Const.FILE_TYPE_FOLDER;

        for(String ext : Const.FILE_MOVIE_EXTENDS)
            if(file.getName().indexOf(ext) > -1) return Const.FILE_TYPE_MOVIE;

        for(String ext : Const.FILE_SUBTITLE_EXTENDS)
            if(file.getName().indexOf(ext) > -1) return Const.FILE_TYPE_SUBTITLE;

        return Const.FILE_TYPE_NOT_ALLOWED;
    }
}
