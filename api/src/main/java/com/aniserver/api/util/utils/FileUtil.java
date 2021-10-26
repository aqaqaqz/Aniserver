package com.aniserver.api.util.utils;

import com.aniserver.api.model.Directory;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public List<Directory> scanDirectory(String path){
        return scanDirectory(path, true);
    }
    public List<Directory> scanDirectory(String path, boolean subList){
        List<Directory> list = new ArrayList<>();

        File dir = new File(path);
        File files[] = dir.listFiles();

        for (File f : files) {
            String fullPath = path + "/" + f.getName();
            String directoryYn = f.isFile()? Const.FALSE:Const.TRUE;

            Directory newDirectory = Directory.builder()
                    .name(f.getName())
                    .path(path)
                    .build();

            if(Const.TRUE.equals(directoryYn)){
                newDirectory.setType(Util.code.FILE_DIRECTORY);
                if(subList) newDirectory.setSublist(scanDirectory(fullPath));
            }else{
                newDirectory.setType(Util.code.FILE_VIDEO);
            }

            list.add(newDirectory);
        }
        return list;
    }

    public boolean isExist(String path){
        File file = new File(path);
        return file.exists();
    }

    public void makeDirectory(String target, String name){
        String path = target + "/" + name;
        if(isExist(path)) return;

        try {
            Files.createDirectory(Path.of(path));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removeDirectory(String path){
        File f = new File(path);
        if(!isExist(path)) return;

        f.delete();
    }

    public void moveDirectory(String target, String move){
        if(!isExist(target)) {
            System.out.println("["+target+"] is not exist");
            return;
        }

        if(isExist(move)) {
            System.out.println("["+target+"] is exist");
            return;
        }

        File t = new File(target);
        File m = new File(move);
        t.renameTo(m);
    }
}
