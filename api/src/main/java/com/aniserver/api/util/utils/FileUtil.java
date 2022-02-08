package com.aniserver.api.util.utils;

import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtil {
    public List<Directory> scanDirectory(String path, Map<String, Directory> directoryMap){
        return scanDirectory(path, true, directoryMap);
    }
    public List<Directory> scanDirectory(String path, boolean subList, Map<String, Directory> directoryMap){
        List<Directory> list = new ArrayList<>();

        File dir = new File(Const.DEFAULT_PATH+path);
        File files[] = dir.listFiles();

        for (File f : files) {
            String nowPath = path + "/" + f.getName();
            String directoryYn = f.isFile()? Const.FALSE:Const.TRUE;

            Directory newDirectory = Directory.builder()
                    .name(f.getName())
                    .path(path)
                    .type((f.isFile()?Util.code.FILE_VIDEO:Util.code.FILE_DIRECTORY))
                    .build();

            if(Const.TRUE.equals(directoryYn)){
                newDirectory.setType(Util.code.FILE_DIRECTORY);
                if(subList) newDirectory.setSublist(scanDirectory(nowPath, subList, directoryMap));
            }else{
                newDirectory.setType(Util.code.FILE_VIDEO);
            }

            directoryMap.put(nowPath, newDirectory);
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

        File f = new File(path);
        f.mkdir();
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
