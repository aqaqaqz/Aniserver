package com.aniserver.common.util.utils;

import com.aniserver.common.Const;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public String getAnimatinoTitle(String name){
        String title = name;

        for(String raw : Const.USE_RAWS)
            title = title.replace(raw, "");

        Pattern p = Pattern.compile(Const.EPISODE_PATTERN);
        Matcher m = p.matcher(title);
        if(m.find())
            title = title.substring(0, m.start());

        return title.trim();
    }
}
