package com.aniserver.api.service;

import com.aniserver.api.exception.InvalidPageException;
import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.common.Const;
import com.aniserver.common.util.Msg;
import com.aniserver.common.util.Utils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class DirectoryService extends BaseService {

    private Map<String, Directory> directoryMap = new HashMap<>();

    /**
     * 경로를 통해 하위 디렉터리를 탐색한다.
     */
    public Directory searchDirectoryListUsePath(String path) throws InvalidPageException {
        if(!Utils.file.isExist(path))
            throw new InvalidPageException(Msg.PAGE_ERROR_NOT_EXIST_PATH);

        File f = Utils.file.getFileInfo(path);
        String type = getType(f);
        if(!Const.CODE_FILE_TYPE_FOLDER.equals(type))
            throw new InvalidPageException(Msg.PAGE_ERROR_NOT_INVALID_ACCESS);

        Directory d = new Directory();
        d.setName(f.getName());
        d.setType(type);
        d.setPath(f.getPath().replace(Const.DEFAULT_PATH, ""));
        for(File lower : f.listFiles()){
            String lowerType = getType(lower);
            if(!Const.CODE_FILE_TYPE_FOLDER.equals(lowerType) && !Const.CODE_FILE_TYPE_MOVIE.equals(lowerType))
                continue;

            Directory ld = new Directory();
            ld.setName(lower.getName());
            ld.setType(lowerType);
            ld.setPath(lower.getPath().replace(Const.DEFAULT_PATH, ""));
            ld.setSubtitle(getSubtitleList(lower));
            d.getLower().add(ld);
        }
        d.getLower().sort((d1, d2)->{
            if(d1.getType().equals(d2.getType()))
                return d1.getName().compareTo(d2.getName());

            if(Const.CODE_FILE_TYPE_FOLDER.equals(d1.getType())) return -1;
            return 1;
        });

        return d;
    }

    /**
     * 키워드로 디렉터리를 루트부터 탐색한다.
     */
    public Directory searchDirectoryListUseKeyword(String keyword){
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

        if(Const.CODE_FILE_TYPE_MOVIE.equals(temp.getType()))
            temp.setSubtitle(getSubtitleList(f));

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

    /**
     * 지원가능한 자막파일의 리스트를 생성한다.
     */
    private List<String> getSubtitleList(File f){
        List<String> subtitleList = new ArrayList<>();
        if(!Const.CODE_FILE_TYPE_MOVIE.equals(getType(f))) return subtitleList;

        for(String sub : Const.ABLE_SUBTITLE_EXTENSION){
            String extension = Utils.file.getExtension(f.getName());
            if(Utils.file.isExist(f.getPath().replaceAll("."+extension, "."+sub)))
                subtitleList.add(sub);
        }

        return subtitleList;
    }

    /**
     * 키워드에 맞는 결과들을 조회한다.
     */
    private Directory search(String keyword){
        Directory result = new Directory();

        for(Directory d : searchDirectoryListUseKeyword("").getLower())
            findKeyword(result, d, keyword);

        return result;
    }

    private void findKeyword(Directory rst, Directory dir, String keyword){
        if(dir.getName().toLowerCase().indexOf(keyword.toLowerCase()) > -1) {
            Directory clone = dir.clone();
            clone.setLower(new ArrayList<>()); //불필요한 데이터 제거
            rst.getSearch().add(clone);
            return;
        }

        if(Const.CODE_FILE_TYPE_FOLDER.equals(dir.getType())){
            for(Directory lower : dir.getLower()){
                findKeyword(rst, lower, keyword);
            }
        }
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

        //자막 파일이 존재하면 이동
        String extension = Utils.file.getExtension(f.getPath());
        Utils.file.moveDirectory(f.getPath(), titlePath+"/"+f.getName());
        for(String subExtension : Const.ABLE_SUBTITLE_EXTENSION){
            String subFullPath = f.getPath().replace("."+extension, "."+subExtension);
            if(Utils.file.isExist(subFullPath)){
                File sub = Utils.file.getFileInfo(subFullPath);
                Utils.file.moveDirectory(sub.getPath(), titlePath+"/"+sub.getName());
            }
        }
    }

    private String getQuarterUseTitle(String title){
        for(Directory q : searchDirectoryListUseKeyword("").getLower()){
            for(Directory t : q.getLower()){
                if(t.getName().equals(title)) return q.getName();
            }
        }

        return Const.EMPTY;
    }
}
