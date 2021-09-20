package com.aniserver.api.controller;

import com.aniserver.api.model.Directory;
import com.aniserver.api.util.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
public class MainController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/")
    public String check() {
        return "server works";
    }

    @GetMapping(value = "/dbTest")
    public List<Map<String, Object>> dbTest() throws Exception {
        return jdbcTemplate.queryForList("SELECT * FROM TEST");
    }

    @GetMapping(value = "/apiutil")
    public String apiUtilTest(@RequestParam String keyword) throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put("X-Naver-Client-Id", "qxx5tYNz5QnKfeNHRP1H");
        header.put("X-Naver-Client-Secret", "q3MFVwumGt");

        Map<String, String> param = new HashMap<>();
        param.put("query", URLEncoder.encode(keyword, "UTF-8"));

        String url = "https://openapi.naver.com/v1/search/blog";

        String rst = Util.http.api(url, "GET", header, param);
        JSONObject json = Util.http.convertStringToJson(rst);

        return rst;
    }

    //https://goddaehee.tistory.com/248 -> 인코딩설정

    @PostMapping(value = "/apiutiltest")
    public List<Directory> forTest(String input){
        List<Directory> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Directory d = new Directory();
            d.setDirectoryId(input + " : id_"+(i+1));
            d.setName(input + " : name_"+(i+1));
            list.add(d);
        }
        return list;
    }
    @GetMapping(value = "/apiutiltest2")
    public String apiutiltest2(@RequestParam String input) throws IOException {
        Map<String, String> header = new HashMap<>();
        //header.put("X-Naver-Client-Id", "qxx5tYNz5QnKfeNHRP1H");
        //header.put("X-Naver-Client-Secret", "q3MFVwumGt");

        Map<String, String> param = new HashMap<>();
        param.put("input", URLEncoder.encode(input, "UTF-8"));

        String url = "http://localhost:8080/apiutiltest";
        String rst = Util.http.api(url, "POST", header, param);

        JSONArray arr = Util.http.convertStringToJsonArray(rst);
        for(int i=0;i<arr.size();i++){
            JSONObject json = (JSONObject) arr.get(i);
            System.out.println("id : " + json.get("directoryId"));
            System.out.println("name : " + json.get("name"));
        }

        return rst;
    }

}