package com.aniserver.api.controller;

import com.aniserver.api.model.db.User;
import com.aniserver.api.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    LoginService loginService;

    @PostMapping(value = "/user/join")
    public Map<String, Object> join(@RequestParam User user){
        return loginService.join(user);
    }

    @PostMapping(value = "/user/login")
    public Map<String, Object> login(@RequestParam User user){
        return loginService.login(user);
    }

    @PostMapping(value = "/user/logout")
    public Map<String, Object> logout(@RequestParam User user){
        return loginService.logout(user);
    }
}
