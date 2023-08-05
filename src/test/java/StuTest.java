import com.qxc.mybatis.mapper.AtestMapper;
import com.qxc.mybatis.mapper.DeptMapper;
import com.qxc.mybatis.mapper.PeopleMapper;
import com.qxc.mybatis.mapper.UserMapper;
import com.qxc.mybatis.pojo.Atest;
import com.qxc.mybatis.pojo.Dept;
import com.qxc.mybatis.pojo.People;
import com.qxc.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.qxc.mybatis.Utiles.sqlSession.getSqlSession;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 15:56
 * @Version 1.0
 * @PACKAGE PACKAGE_NAME
 */
public class StuTest {
    @Test
    public void t1() {
        final SqlSession sqlSession = getSqlSession();
        final PeopleMapper mapper = sqlSession.getMapper(PeopleMapper.class);
        final People people = mapper.selectById(10);
        System.out.println(people);
    }

    @Test
    public void t2() {
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final Dept dept = mapper.getDept(6);
        System.out.println(dept);
    }

    @Test
    public void t3() {
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final User dept = mapper.getDeptByStep(6);
        System.out.println(dept);
    }

    @Test
    public void t4() throws IOException {
        /**
         * 一级缓存sqlsesson级别，同一个sqlsession语句被缓存
         * 一级缓存失效
         * 1.不同的sqlsesson
         * 2。条件不同
         * 3. 两次查询之间查询了真删改，清空缓存
         * 4. 主动清空缓存
         */
        final SqlSession sqlSession = getSqlSession();
        final AtestMapper mapper = sqlSession.getMapper(AtestMapper.class);
        final Atest dept = mapper.getDeptByStep(3);
        // 清空缓存
        sqlSession.clearCache();
        System.out.println(dept);
        System.out.println("================================");
        System.out.println(dept.getDept());

    }

    @Test
    public void t12() throws IOException {
        /**
         * 二级缓存sqlSessionFactory级别
         * 1.首先需要开启缓存
         * 2.mapper中添加cache标签
         * 3.sqlsession关闭
         * 4。对象实现seralizable接口
         *
         * 清空缓存
         * 两次查询之间增删改
         * 条件改变
         */
        final InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sb = new SqlSessionFactoryBuilder();
        final SqlSessionFactory build = sb.build(resourceAsStream);
        final SqlSession sqlSession1 = build.openSession(true);
        final AtestMapper mapper1 = sqlSession1.getMapper(AtestMapper.class);
        System.out.println(mapper1.getDeptByStep(2));

        sqlSession1.close();

        final SqlSession sqlSession2 = build.openSession(true);
        final AtestMapper mapper2 = sqlSession2.getMapper(AtestMapper.class);
        final Atest deptByStep = mapper2.getDeptByStep(2);
        deptByStep.setName("123");
        System.out.println(deptByStep);
    }

    @Test
    public void t5() {
        final SqlSession sqlSession = getSqlSession();
        final AtestMapper mapper = sqlSession.getMapper(AtestMapper.class);
        final Atest dept = mapper.getDeptByStep(1);
        /**
         * 开启了延迟加载
         */
        System.out.println(dept.getName());
        System.out.println("================================");
        System.out.println(dept.getDept());
    }

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.137.2:3306/leetcode";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    @Test
    public void t6() throws ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "select * from test2";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void t7() {

        final SqlSession sqlSession = getSqlSession();
        final DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        final Dept allAtest = mapper.getAllAtest(2);
        System.out.println(allAtest);
    }

    @Test
    public void t8() {
        final SqlSession sqlSession = getSqlSession();
        final DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        final Dept allAtestSteps = mapper.getAllAtestSteps(2);
        System.out.println(allAtestSteps);
    }

    @Test
    public void t9() {
        final SqlSession sqlSession = getSqlSession();
        final AtestMapper mapper = sqlSession.getMapper(AtestMapper.class);
        final List<Atest> q = mapper.selectByCondition(new Atest().setId(-2).setName(""));
        System.out.println(q);
    }

    @Test
    public void t10() {
        final SqlSession sqlSession = getSqlSession();
        final AtestMapper mapper = sqlSession.getMapper(AtestMapper.class);
        List<Atest> list = new ArrayList<>();
        list.add(new Atest().setDept(new Dept()).setDeptid(2).setName("qxc"));
        list.add(new Atest().setDept(new Dept()).setDeptid(2).setName("qxc1"));
        list.add(new Atest().setDept(new Dept()).setDeptid(2).setName("qxc2"));
        list.add(new Atest().setDept(new Dept()).setDeptid(2).setName("qxc3"));
        list.add(new Atest().setDept(new Dept()).setDeptid(2).setName("qxc4"));
        list.add(new Atest().setDept(new Dept()).setDeptid(2).setName("qxc5"));
        mapper.insertDepts(list);
    }

    @Test
    public void t11() {
        final SqlSession sqlSession = getSqlSession();
        final AtestMapper mapper = sqlSession.getMapper(AtestMapper.class);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(11);
        mapper.deleteDepts(list);
    }
}

