package com.aniserver.api.common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Util {
    public static HttpURLConnection getConnection(String url, String type, Map<String, String> header) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(type);
        for(String key : header.keySet()){
            connection.setRequestProperty(key, header.get(key));
        }

        connection.setDoOutput(false);
        return connection;
    }

    public static String api(String url, String type, Map<String, String> header) throws IOException {
        String data = "";

        HttpURLConnection connection = getConnection(url, type, header);

        StringBuffer sb = new StringBuffer();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8")
            );

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
        }

        data = sb.toString();
        data = data.replace("\uFEFF", ""); //remove UTF-16 BOM
        data = data.replace("\uEFBBBF", ""); // remove UTF-8 BOM

        return data;
    }

    public static JSONObject convertStringToJson(String str){
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        try {
            json = (JSONObject) parser.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    public static JSONArray convertStringToJsonArray(String str){
        System.out.println(str);

        JSONParser parser = new JSONParser();
        JSONArray json = new JSONArray();
        try {
            json = (JSONArray) parser.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }
}
