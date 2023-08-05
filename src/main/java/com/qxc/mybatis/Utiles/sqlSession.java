package com.qxc.mybatis.Utiles;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 13:01
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.Utiles
 */
public class sqlSession {
    public static SqlSession getSqlSession() {
        SqlSession sqlSession;
        try {
            final InputStream asStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactoryBuilder sb = new SqlSessionFactoryBuilder();
            final SqlSessionFactory factory = sb.build(asStream);
            sqlSession = factory.openSession(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sqlSession;
    }
}
