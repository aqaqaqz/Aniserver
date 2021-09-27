package com.aniserver.api.util.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
    public HttpURLConnection getConnection(String url, String type, Map<String, String> header, JSONObject p) throws IOException {
        boolean isGet = "GET".equals(type);

        if(isGet && !p.isEmpty()){
            String param = "";
            for(String key : (Set<String>)p.keySet()){
                if("".equals(param)) param = "?";
                else param += "&";

                param += (key+"="+p.get(key));
            }
            url += "?"+param;
        }

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(type);
        for(String key : header.keySet()){
            connection.setRequestProperty(key, header.get(key));
        }

        if(!isGet) {
            connection.setDoOutput(true);
            String param = p.toString();
            if(param!=null && !"".equals(param)) {
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                printWriter.print(param);
                printWriter.close();
            }
        }

        return connection;
    }

    public HttpURLConnection getConnection(String url, String type, Map<String, String> header, JSONArray p) throws IOException {
        boolean isGet = "GET".equals(type);
        String param = p.toString(); //get확인해봐야함.

        if(isGet) url += "?"+param;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(type);
        for(String key : header.keySet()){
            connection.setRequestProperty(key, header.get(key));
        }

        if(!isGet) {
            connection.setDoOutput(true);
            if(param!=null && !"".equals(param)) {
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                printWriter.print(param.getBytes(StandardCharsets.UTF_8));
                printWriter.close();
            }
        }

        return connection;
    }

    public String getParamSting(Map<String, String> param){
        if(param==null || param.isEmpty()) return "";

        StringBuffer sb = new StringBuffer();

        for(String key : param.keySet()){
            if(sb.length()!=0) sb.append("&");
            sb.append(key + "=" + param.get(key));
        }

        return sb.toString();
    }

    public String api(String url, String type, Map<String, String> header, JSONObject p) throws IOException {
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

    public String api(String url, String type, Map<String, String> header, JSONArray p) throws IOException {
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
