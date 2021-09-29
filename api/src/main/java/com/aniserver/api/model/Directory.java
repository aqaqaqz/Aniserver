package com.aniserver.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Directory {
    private String directoryId;
    private String upperId;
    private String path;
    private String name;
    private String type;
    private List<Directory> sublist;

    public String getFullPath(){
        return path+"/"+name;
    }
}
