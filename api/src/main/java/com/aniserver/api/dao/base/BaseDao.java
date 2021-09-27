package com.aniserver.api.dao.base;

import com.aniserver.api.model.Seq;
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

    public String getSeqIdByName(String name) throws IOException {
        Seq seq = this.getSession().selectOne("base.getSeq", Seq.builder().name(name).build());
        seq.setNext(seq.getNext() + 1);
        updateSeq(seq);
        return ""+seq.getNext();
    }

    private void updateSeq(Seq seq) throws IOException{
        this.getSession().update("base.updateSeq", seq);
    }
}
