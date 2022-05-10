package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.common.Const;
import com.aniserver.common.util.Utils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectoryService extends BaseService {

    private Map<String, Directory> directoryMap = new HashMap<>();

    /**
     * 디렉터리를 탐색한다.
     */
    public Directory searchDirectoryList(String keyword){
        if(directoryMap.isEmpty()) initDirectoryMap();

        if("".equals(keyword)) return directoryMap.get(Const.DEFAULT_PATH);

        return search(keyword);
    }

    /**
     * 디렉터리 맵 초기화
     */
    private void initDirectoryMap(){
        directoryMap = new HashMap<>();
        makeDirectoryMap(Const.DEFAULT_PATH);
    }

    /**
     * 검색 대상이 될 디렉터리를 전체 탐색하여 맵에 저장시켜둔다.
     */
    private Directory makeDirectoryMap(String path){
        File f = Utils.file.getFileInfo(path);
        String type = getType(f);
        if(!Const.CODE_FILE_TYPE_FOLDER.equals(type) && !Const.CODE_FILE_TYPE_MOVIE.equals(type))
            return null;

        Directory temp = new Directory();
        temp.setName(f.getName());
        temp.setPath(path);
        temp.setType(getType(f));

        if(Const.CODE_FILE_TYPE_FOLDER.equals(temp.getType())){
            for(File next : f.listFiles()){
                Directory lower = makeDirectoryMap(next.getPath());
                if(lower != null)
                    temp.getLower().add(lower);
            }
        }

        if(Const.CODE_FILE_TYPE_MOVIE.equals(temp.getType())){
            for(String sub : Const.ABLE_SUBTITLE_EXTENSION){
                String extension = Utils.file.getExtension(f.getName());
                if(Utils.file.isExist(path.replaceAll("."+extension, "."+sub)))
                    temp.getSubtitle().add(sub);
            }
        }

        directoryMap.put(path, temp);
        return directoryMap.get(path);
    }

    /**
     * 파일의 타입을 구한다
     * 동영상, 자막, 폴더, 기타
     */
    private String getType(File f){
        if(f.isDirectory()) return Const.CODE_FILE_TYPE_FOLDER;

        String extension = Utils.file.getExtension(f.getName());
        if(Const.ABLE_MOVIE_EXTENSION.stream().anyMatch(s -> s.equals(extension)))
            return Const.CODE_FILE_TYPE_MOVIE;
        if(Const.ABLE_SUBTITLE_EXTENSION.stream().anyMatch(s -> s.equals(extension)))
            return Const.CODE_FILE_TYPE_SUBTITLE;

        return Const.CODE_FILE_TYPE_NOTHING;
    }

    private Directory search(String keywrod){
        return directoryMap.get(Const.DEFAULT_PATH);
    }

    /**
     * 경로 리스트를 입력받아 yyyy-q > title 형태로 분류해준다.
     */
    public int divideDirectoryList(List<String> pathList) {
        int cnt = 0;

        for(String path : pathList){
            try {
                File f = Utils.file.getFileInfo(path);
                divideAnimation(f);
                cnt++;
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return cnt;
    }

    private void divideAnimation(File f){
        String quarter = getQuarterUseTitle(f.getName());
        String title = Utils.string.getAnimatinoTitle(f.getName());

        String quarterPath = Const.DEFAULT_PATH + quarter;
        String titlePath = quarterPath + "/" + title;

        if(Const.EMPTY.equals(quarter)) {
            quarter = Utils.date.getYearAndQuarter();
            quarterPath = Const.DEFAULT_PATH + quarter;
            titlePath = quarterPath + "/" + title;

            if(!Utils.file.isExist(quarterPath))
                Utils.file.makeDirectory(quarterPath);
        }

        if(!Utils.file.isExist(titlePath))
            Utils.file.makeDirectory(titlePath);

        Utils.file.moveDirectory(f.getPath(), titlePath+"/"+f.getName());
    }

    private String getQuarterUseTitle(String title){
        for(Directory q : searchDirectoryList("").getLower()){
            for(Directory t : q.getLower()){
                if(t.getName().equals(title)) return q.getName();
            }
        }

        return Const.EMPTY;
    }
}
