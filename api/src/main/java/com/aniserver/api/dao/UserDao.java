package com.aniserver.api.dao;

import com.aniserver.api.dao.base.BaseDao;
import com.aniserver.api.model.db.User;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class UserDao extends BaseDao {
    public User getUserInfo() throws IOException {
		return new User();
	}
}
