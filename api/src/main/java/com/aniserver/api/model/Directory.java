package com.aniserver.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Directory {
    private String name;
    private String path;
    private String type;
    private List<Directory> sublist;
    private List<String> subtitle;

    public String getFullPath(){
        return path + "/" + name;
    }
}
