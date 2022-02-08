package com.aniserver.api.batch.Job;

import com.aniserver.api.util.Util;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OhysDownload implements Job {
    private final String def = "empty";
    private Map<String, String> header;
    private String url = def;
    private String downPath = def;
    private String downUrl = def;

    public OhysDownload(){
        header = new HashMap<>();
        header.put("cache-control", "max-age=0");
        header.put("sec-fetch-user", "?1");
        header.put("User-Agent", "Mozilla/5.0");
    }

    private JSONArray getAniList(){

        String data = null;
        try {
            JSONObject param = new JSONObject();
            data = Util.http.api(url, "GET", header, param);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Util.http.convertStringToJsonArray(data);
    }

    private void downloadTorrent(JSONArray jsonArr) {
        for(Object o : jsonArr){
            JSONObject json = (JSONObject)o;

            String path = downPath + json.get("t");
            String link = downUrl + json.get("a");
            System.out.println(link);

            HttpURLConnection connection = null;
            try {
                JSONObject param = new JSONObject();
                connection = Util.http.getConnection(url, "GET", header, param);

                InputStream in = connection.getInputStream();
                Path p = Paths.get(path);
                Files.copy(in, p);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if(def.equals(url)){
            System.out.println("url parameter not exists");
            return;
        }

        JSONArray json = getAniList();
        downloadTorrent(json);

        System.out.println("OhysDownload complete");
    }
}
