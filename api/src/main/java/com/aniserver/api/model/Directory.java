package com.aniserver.api.model;

import java.util.List;

public class Directory {
    private String directoryId;
    private String upperId;
    private String name;
    private String type;

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }

    public String getUpperId() {
        return upperId;
    }

    public void setUpperId(String upperId) {
        this.upperId = upperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Directory> getSublist() {
        return sublist;
    }

    public void setSublist(List<Directory> sublist) {
        this.sublist = sublist;
    }

    private List<Directory> sublist;
}
