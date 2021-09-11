package com.aniserver.api.dao;

import com.aniserver.api.dao.base.BaseDao;
import com.aniserver.api.exception.EmptyParamException;
import com.aniserver.api.model.Batch;
import com.aniserver.api.model.Directory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Repository
public class DirectoryDao extends BaseDao {

    private int removeDirectory(String directoryId) throws IOException, EmptyParamException {
        if(StringUtils.isEmpty(directoryId)) throw new EmptyParamException("getDirectory", "directoryId");
        return getSession().insert("directory.removeDirectory", directoryId);
    }

    public List<Batch> getDirectory(String directoryId) throws IOException, EmptyParamException {
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

}
