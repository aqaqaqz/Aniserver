package com.aniserver.api.util.utils;

import com.aniserver.api.model.Directory;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class FileUtil {
    public List<Directory> scanDirectory(String path){
        List<Directory> list = new ArrayList<>();

        File dir = new File(path);
        File files[] = dir.listFiles();

        for (File f : files) {
            String fullPath = path + "/" + f.getName();
            String directoryYn = f.isFile()? Const.FALSE:Const.TRUE;

            Directory newDirectory = Directory.builder()
                    .name(fullPath)
                    .build();

            if(Const.TRUE.equals(directoryYn)){
                newDirectory.setType(Util.code.FILE_DIRECTORY);
                newDirectory.setSublist(scanDirectory(fullPath));
            }else{
                newDirectory.setType(Util.code.FILE_VIDEO);
            }

            list.add(newDirectory);
        }
        return list;
    }

    public void makeDirectory(String path){

    }

    public void moveDirectory(String targetPath, String movePath){

    }
}
