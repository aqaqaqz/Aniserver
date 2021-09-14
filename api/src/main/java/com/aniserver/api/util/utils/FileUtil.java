package com.aniserver.api.util.utils;

import com.aniserver.api.model.Directory;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class FileUtil {

    @Value("${system.default.file_path}")
    private String defPath;

    public List<Directory> scanDirectory(){
        return scanDirectory(defPath);
    }
    public List<Directory> scanDirectory(String path){
        return null;
    }

    public void makeDirectory(String path){

    }

    public void moveDirectory(String targetPath, String movePath){

    }
}
