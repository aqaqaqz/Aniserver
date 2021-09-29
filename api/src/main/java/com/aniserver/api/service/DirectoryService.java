package com.aniserver.api.service;

import com.aniserver.api.dao.DirectoryDao;
import com.aniserver.api.exception.EmptyParamException;
import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DirectoryService extends BaseService {

    @Value("${system.default.file_path}")
    private String defPath;

    @Value("${system.default.down_path}")
    private String downPath;

    @Autowired
    DirectoryDao directoryDao;

    private List<String> setDirectoryInfo(List<Directory> list, String upperId) throws IOException {
        List<String> newDirIdList = new ArrayList<>();
        if(ObjectUtils.isEmpty(list)) return newDirIdList;

        for(Directory d : list){
            d.setUpperId(upperId);
            d.setDirectoryId(directoryDao.getSeqIdByName("directory_seq"));
            newDirIdList.add(d.getDirectoryId());
            setDirectoryInfo(d.getSublist(), d.getDirectoryId());
        }

        return newDirIdList;
    }

    public void scanPath() throws IOException {
        scanPath(defPath+downPath);
    }
    public void scanPath(String path) throws IOException {
        List<Directory> list = Util.file.scanDirectory(path);
        setDirectoryInfo(list, "root");

        directoryDao.insertDirectoryList(list);
    }

    private int getEpsodeNum(String name){
        int episodeNum = -1;

        Pattern pattern = Pattern.compile(" - [\\d]+");
        Matcher matcher = pattern.matcher(name);
        if(matcher.find()) {
            episodeNum = Integer.parseInt(""+matcher.start());
        }

        return episodeNum;
    }
    private String getTitle(String name){
        //타이틀 이후의 string을 제거하여 타이틀을 구한다.
        String title = name;

        return title;
    }
    private String getTitleQuarter(String title){
        //타이틀의 quarter를 구한다.
        String quarter = "new";

        return quarter;
    }
    private String getQuarter(String title){
        //현재 날짜의 분기를 구한다. -> 2021-1, 2021-2....
        String quarter = "2021-1";

        return quarter;
    }
    private String findNewPath(String name){
        String newPath = this.defPath;

        for(String s : Const.useRaws){
            name.replace(s, "");
        }
        name.trim();

        int ep = getEpsodeNum(name);
        String title = getTitle(name);
        String quarter = getTitleQuarter(title);

        if("new".equals(quarter)){
            //db에 없는 타이틀
            if(ep==0 || ep==1 || ep==2 || ep==3){
                // 분기가 시작된걸로 판단하고 현재 날짜 기준으로 path를 만들어준다.
            }else{
                // 옛날 애니메이션으로 생각하고 etc의 하위에 path를 만든다.
                newPath += ("/etc/"+title);
            }
        }else{
            //기존 quarter에 등록된 타이틀이기에 해당 폴더 하위로 path를 만든다.
            newPath += ("/"+quarter+"/"+title);
        }

        return newPath;
    }
    public void movePath(List<String> dirList) throws EmptyParamException, IOException {
        List<Directory> list = directoryDao.getDirectory(dirList);
        for(Directory d : list){
            Util.file.moveDirectory(d.getFullPath(), findNewPath(d.getName()));
        }
    }

    public void newDirectory(List<Directory> dirList) throws EmptyParamException, IOException {
        List<String> newDirIdList = setDirectoryInfo(dirList, Const.TEMP_DIR_ID);
        directoryDao.insertDirectoryList(dirList);
        movePath(newDirIdList);
    }
}
