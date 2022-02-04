package com.aniserver.api.service;

import com.aniserver.api.dao.UserDao;
import com.aniserver.api.exception.UserException;
import com.aniserver.api.model.db.User;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends BaseService {
    @Autowired
    UserDao userDao;

    public Map<String, Object> join(User user){
        Map<String, Object> rst = new HashMap<>();
        return rst;
    }

    public Map<String, Object> login(User user, HttpSession session) throws Exception{
        User info = userDao.getUserInfo(user);

        if(ObjectUtils.isEmpty(info)) throw new UserException( user.getId()+"의 정보가 올바르지 않습니다." );

        session.setAttribute(Const.LOGIN_SESSION_NAME, info);

        return Util.http.makeResponse(Const.HTTP_SUCCESS_CODE, "로그인 되었습니다.");
    }

    public Map<String, Object> logout(HttpSession session){
        session.invalidate();

        Map<String, Object> rst = new HashMap();
        return rst;
    }
}
