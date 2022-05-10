package com.aniserver.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Directory {
    private String name = ""; //이름
    private String path = ""; //이름을 포함한 전체 경로
    private String type = ""; //폴더, 동영상, 자막
    private List<Directory> lower = new ArrayList<>(); //하위 리스트
    private List<String> subtitle = new ArrayList<>(); //자막 리스트
}
