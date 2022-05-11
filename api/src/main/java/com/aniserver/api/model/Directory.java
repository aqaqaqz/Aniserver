package com.aniserver.api.model;

import com.aniserver.common.Const;
import com.aniserver.common.util.Utils;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class Directory implements Cloneable {
    @Override
    public Directory clone() {
        try {
            return (Directory) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    private String name = ""; //이름
    private String path = ""; //이름을 포함한 전체 경로
    private String type = ""; //폴더, 동영상, 자막
    private List<Directory> lower = new ArrayList<>(); //하위 리스트
    private List<String> subtitle = new ArrayList<>(); //자막 리스트
    private List<Directory> search = new ArrayList<>(); //검색결과 리스트
}
