package com.aniserver.api.controller;

import com.aniserver.api.batch.QuartzTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    static int seq = 0;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/")
    public String check() {
        return "server works";
    }

    @GetMapping(value = "/dbTest")
    public List<Map<String, Object>> dbTest() throws Exception {
        return jdbcTemplate.queryForList("SELECT * FROM DASHBOARD");
    }

    @PostMapping(value = "/quartz")
    public String addQuartz() {
        QuartzTest.addJob("0/10 * * * * ?", "com.aniserver.api.batch.Job.QuartzJobTest",  "schdule_"+seq, "class");
        return "[schdule_" + seq++ + "]가 추가되었습니다.";
    }

    @GetMapping(value = "/quartz")
    public List<String> getQuartzList() {
        return QuartzTest.getJobIdList();
    }
}