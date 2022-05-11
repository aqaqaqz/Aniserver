package com.aniserver.common.util.utils;

import com.aniserver.api.model.Directory;
import com.aniserver.common.util.Msg;
import com.aniserver.common.util.exception.FileException;

import java.io.File;

public class FileUtil {
    public boolean isExist(String path){
        File file = null;
        try{
            file = new File(path);
        }catch(Exception e){
            return false;
        }

        return file.exists();
    }

    public File getFileInfo(String path){
        if(!this.isExist(path))
            throw new FileException(Msg.FILE_UTIL_ERROR_NOT_EXIST_PATH);

        File file = new File(path);
        return file;
    }

    public void makeDirectory(String path) {
        if(isExist(path)) return;

        File f = new File(path);
        f.mkdir();
    }

    public void removeDirectory(String path) throws FileException {
        if(!isExist(path))
            throw new FileException(Msg.FILE_UTIL_ERROR_NOT_EXIST_PATH);

        File f = new File(path);
        f.delete();
    }

    public void moveDirectory(String target, String move){
        if(!isExist(target))
            throw new FileException(Msg.FILE_UTIL_ERROR_NOT_EXIST_PATH);

        if(isExist(move))
            throw new FileException(Msg.FILE_UTIL_ERROR_EXIST_PATH);

        File t = new File(target);
        File m = new File(move);
        t.renameTo(m);
    }

    public String getExtension(String name){
        String extension = "";

        for(int i=name.length()-1;i>=0&&name.charAt(i)!='.';i--)
            extension = name.charAt(i) + extension;

        return extension;
    }
}
