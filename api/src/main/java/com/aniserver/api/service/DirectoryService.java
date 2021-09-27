package com.aniserver.api.service;

import com.aniserver.api.dao.DirectoryDao;
import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Service
public class DirectoryService extends BaseService {

    @Value("${system.default.file_path}")
    private String defPath;

    @Value("${system.default.down_path}")
    private String downPath;

    @Autowired
    DirectoryDao directoryDao;

    private void setDirectoryInfo(List<Directory> list, String upperId) throws IOException {
        if(ObjectUtils.isEmpty(list)) return;

        for(Directory d : list){
            d.setUpperId(upperId);
            d.setDirectoryId(directoryDao.getSeqIdByName("directory_seq"));
            setDirectoryInfo(d.getSublist(), d.getDirectoryId());
        }
    }

    public void scanPath() throws IOException {
        scanPath(defPath+downPath);
    }
    public void scanPath(String path) throws IOException {
        List<Directory> list = Util.file.scanDirectory(path);
        setDirectoryInfo(list, "root");

        directoryDao.insertDirectoryList(list);
    }
}
