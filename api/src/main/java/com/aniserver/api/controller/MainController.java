package com.aniserver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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
}