package com.aniserver.api.util.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {
    public HttpURLConnection getConnection(String url, String type, Map<String, String> header) throws IOException {
        return getConnection(url, type, header, null);
    }
    public HttpURLConnection getConnection(String url, String type, Map<String, String> header, Map<String, String> p) throws IOException {
        boolean isGet = "GET".equals(type);
        String param = getParamSting(p);

        if(isGet) url += "?"+param;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(type);
        for(String key : header.keySet()){
            connection.setRequestProperty(key, header.get(key));
        }

        connection.setDoOutput(true);

        if(!isGet && !StringUtils.isEmpty(param)) {
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.print(param);
            printWriter.close();
        }

        return connection;
    }

    public String getParamSting(Map<String, String> param){
        if(ObjectUtils.isEmpty(param)) return "";

        StringBuffer sb = new StringBuffer();

        for(String key : param.keySet()){
            if(sb.length()!=0) sb.append("&");
            sb.append(key + "=" + param.get(key));
        }

        return sb.toString();
    }

    public String api(String url, String type, Map<String, String> header) throws IOException {
        return api(url, type, header, null);
    }
    public String api(String url, String type, Map<String, String> header, Map<String, String> p) throws IOException {
        String data = "";

        HttpURLConnection connection = getConnection(url, type, header, p);

        StringBuffer sb = new StringBuffer();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTF-8")
            );

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
        }

        data = sb.toString();
        data = data.replace("\uEFBBBF", ""); // remove UTF-8 BOM
        data = data.replace("\uFEFF", ""); //remove UTF-16 BOM

        return data;
    }

    public JSONObject convertStringToJson(String str){
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        try {
            json = (JSONObject) parser.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    public JSONArray convertStringToJsonArray(String str){
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
