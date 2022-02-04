package com.aniserver.api.util;

public class Const {
    public static final String TRUE = "Y";
    public static final String FALSE = "N";

    public static final String[] movieExtension = {"mp4", "mkv"};
    public static final String[] USE_RAWS = {"[Ohys-Raws]", "[Leopard-Raws]"};

    public static final String EPISODE_SEPERATOR = " - ";
    public static final String EPISODE_PATTERN = EPISODE_SEPERATOR + "[0-9]{1,3}";
    public static final String ANIMATION_QUARTER_PATTERN = "[0-9]{4}-[0-9]{1}";
    public static final String OVA_TITLE_PATTERN = "\\(.+\\).";

    public static final String EMPTY = "EMPTY";

    public static final String DEFAULT_PATH = "c:/test";
    public static final String PATH_ANIMATION = "animation";
    public static final String PATH_DIVIDE = "divide";
    public static final String PATH_DOWN = "download";
    public static final String PATH_TORRENT = "torrent";

    public final static String LOGIN_SESSION_NAME = "member";

    public final static String HTTP_SUCCESS_CODE = "200";
    public final static String HTTP_REQUEST_ERROR_CODE = "400";
    public final static String HTTP_SERVER_ERROR_CODE = "200";
}
