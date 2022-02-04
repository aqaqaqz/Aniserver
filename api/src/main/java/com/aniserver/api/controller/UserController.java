package com.aniserver.api.controller;

import com.aniserver.api.exception.UserException;
import com.aniserver.api.model.db.User;
import com.aniserver.api.service.UserService;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("api")
@RestController
public class UserController {
    @Autowired
    UserService loginService;

    @PostMapping(value = "/user/join")
    public Map<String, Object> join(@RequestBody User user){
        return loginService.join(user);
    }

    @PostMapping(value = "/user/login")
    public Map<String, Object> login(@RequestBody User user, HttpServletRequest req){
        Map<String, Object> res;

        try {
            res = loginService.login(user, req.getSession());
        } catch (UserException e) {
            res = Util.http.makeResponse(Const.HTTP_REQUEST_ERROR_CODE, user.getId()+"의 정보가 올바르지 않습니다.");
        } catch (Exception e){
            e.printStackTrace();
            res = Util.http.makeResponse(Const.HTTP_REQUEST_ERROR_CODE, "관리자에게 문의 바랍니다.");
        }

        return res;
    }

    @PostMapping(value = "/user/logout")
    public Map<String, Object> logout(@RequestBody User user, HttpServletRequest req){
        return loginService.logout(req.getSession());
    }
}
