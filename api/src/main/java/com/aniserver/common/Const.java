package com.aniserver.common;

import java.util.Arrays;
import java.util.List;

public class Const {
    public static final String TRUE = "Y";
    public static final String FALSE = "N";

    public static final List<String> ABLE_MOVIE_EXTENSION = Arrays.asList("mp4", "mkv");
    public static final String ABLE_MOVIE_EXTENSION_MP4 = "mp4";
    public static final List<String> ABLE_SUBTITLE_EXTENSION = Arrays.asList("smi", "ass", "srt");
    public static final List<String> USE_RAWS = Arrays.asList("[Ohys-Raws]", "[Leopard-Raws]");

    public static final String EPISODE_SEPERATOR = " - ";
    public static final String EPISODE_PATTERN = EPISODE_SEPERATOR + "[0-9]{1,3}";
    public static final String ANIMATION_QUARTER_PATTERN = "[0-9]{4}-[0-9]{1}";
    public static final String OVA_TITLE_PATTERN = "\\(.+\\).";

    public static final String EMPTY = "EMPTY";

    public static final String DEFAULT_PATH = "/Users/lsh/Documents/aniserver test/";
    public static final String THUMBNAIL_PATH = "./thumbnail/";
    public static final String RECENT_PATH = "./recent_list";
    public static final String RECENT_LIST_SEPARATOR = "1q2w3e4r";
    public static final String RECENT_INFO_SEPARATOR = "QWERASDF";

    public final static String LOGIN_SESSION_NAME = "member";

    public final static String HTTP_SUCCESS_CODE = "200";
    public final static String HTTP_REQUEST_ERROR_CODE = "400";
    public final static String HTTP_SERVER_ERROR_CODE = "200";

    public final static String CODE_FILE_TYPE_FOLDER = "F";
    public final static String CODE_FILE_TYPE_MOVIE = "M";
    public final static String CODE_FILE_TYPE_SUBTITLE = "S";
    public final static String CODE_FILE_TYPE_NOTHING = "N";
}
