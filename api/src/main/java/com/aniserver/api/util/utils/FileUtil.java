package com.aniserver.api.util.utils;

import com.aniserver.api.model.db.Directory;
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

    public void moveDirectory(String targetPath, String movePath){

    }

    public String getFilePath(String name, String rootPath){
        return "";
    }

    public void divideFile(String path, String extension, String rootPath){
        List<Directory> list = scanDirectory(path, false);
        for(Directory d : list){
            String e = "";
            String name = d.getName();
            for(int i=name.length()-1;i>=0;i--){
                if(name.charAt(i) == '.') break;
                e = name.charAt(i)+e;
            }

            if (extension.equals(e)) {
                moveDirectory(d.getFullPath(), getFilePath(d.getName(), rootPath));
            }
        }
    }
}
