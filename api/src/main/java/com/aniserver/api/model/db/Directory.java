package com.aniserver.api.model.db;

import com.aniserver.api.util.Const;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private List<String> getOvaTitlePattern(){
        List<String> pattern = new ArrayList<>();
        for(String extention : Const.movieExtension){
            pattern.add(Const.OVA_TITLE_PATTERN+extention);
        }

        return pattern;
    }

    private String getOvaTitle(String title){
        for(String p : getOvaTitlePattern()){
            Pattern pattern = Pattern.compile(p);
            Matcher matcher = pattern.matcher(title);
            if(matcher.find()) {
                title = title.substring(0, matcher.start());
                break;
            }
        }
        return title;
    }

    public String getFullPath(){
        return path+"/"+name;
    }

    public int getEpsode(){
        final String separator = " - ";
        int episodeNum = -1;

        Pattern pattern = Pattern.compile(Const.EPISODE_PATTERN);
        Matcher matcher = pattern.matcher(name);
        if(matcher.find()) {
            episodeNum = Integer.parseInt(""+matcher.group().replace(Const.EPISODE_SEPERATOR, ""));
        }

        return episodeNum;
    }

    public String getTitle(){
        if(type == "dir") return name;

        String title = name;
        for(String raw : Const.USE_RAWS){
            title = title.replace(raw, "");
        }

        Pattern pattern = Pattern.compile(Const.EPISODE_PATTERN);
        Matcher matcher = pattern.matcher(title);
        if(matcher.find()) {
            title = title.substring(0, matcher.start());
        }else{
            title = getOvaTitle(title);
        }
        title = title.trim();

        return title;
    }
}
