package com.aniserver.api.dao.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class BaseDao {

    protected SqlSession getSession() throws IOException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(Resources.getResourceAsReader("mapper/config/mybatis.xml"));
        SqlSession session = factory.openSession(true);
        return session;
    }
}
