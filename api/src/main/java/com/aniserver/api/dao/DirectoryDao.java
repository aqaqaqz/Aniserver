package com.aniserver.api.dao;

import com.aniserver.api.dao.base.BaseDao;
import com.aniserver.api.exception.EmptyParamException;
import com.aniserver.api.model.db.Directory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DirectoryDao extends BaseDao {

    private int removeAllDirectory(String directoryId) throws IOException, EmptyParamException {
        if(StringUtils.isEmpty(directoryId)) throw new EmptyParamException("getDirectory", "directoryId");
        return getSession().insert("directory.removeAllDirectory", directoryId);
    }

    public List<Directory> getDirectory(String directoryId) throws IOException, EmptyParamException {
        List<String> dirList = new ArrayList<>();
        dirList.add(directoryId);
        return getDirectory(dirList);
    }
    public List<Directory> getDirectory(List<String> directoryId) throws IOException, EmptyParamException {
        if(StringUtils.isEmpty(directoryId)) throw new EmptyParamException("getDirectory", "directoryId");

        return getSession().selectList("directory.getDirectory");
    }

    public int addDirectory(Directory directory) throws IOException {
        return getSession().insert("directory.insertDirectory", directory);
    }

    public int updateDirectory(Directory directory) throws IOException, EmptyParamException {
        if(StringUtils.isEmpty(directory.getDirectoryId())) throw new EmptyParamException("updateDirectory", "directoryId");
        return getSession().insert("directory.updateDirectory", directory);
    }

    public int insertDirectoryList(List<Directory> list) throws IOException {
        if(ObjectUtils.isEmpty(list)) return 0;
        for(Directory d : list){
            insertDirectory(d);
            insertDirectoryList(d.getSublist());
        }
        return 1;
    }

    public int insertDirectory(Directory directory) throws IOException {
        if(ObjectUtils.isEmpty(directory)) return 0;
        return getSession().insert("directory.insertDirectory", directory);
    }

}
