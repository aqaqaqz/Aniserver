package com.aniserver.api.service;

import com.aniserver.api.dao.UserDao;
import com.aniserver.api.model.db.User;
import com.aniserver.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService extends BaseService {
    @Autowired
    UserDao LoginDao;

    public Map<String, Object> join(@RequestParam User user){
        Map<String, Object> rst = new HashMap<>();
        return rst;
    }

    public Map<String, Object> login(@RequestParam User user){
        Map<String, Object> rst = new HashMap();
        return rst;
    }

    public Map<String, Object> logout(@RequestParam User user){
        Map<String, Object> rst = new HashMap();
        return rst;
    }
}
