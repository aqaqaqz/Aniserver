package com.aniserver.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @RequestMapping(value = "/")
    public String check() {
        return "server works";
    }
}