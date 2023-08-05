import com.qxc.mybatis.mapper.UserMapper;
import com.qxc.mybatis.pojo.Dept;
import com.qxc.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.qxc.mybatis.Utiles.sqlSession.getSqlSession;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 10:21
 * @Version 1.0
 * @PACKAGE PACKAGE_NAME
 */
public class test1 {
    @Test
    public void test2() throws IOException {
        final InputStream asStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 获取sqlSessionFactory工厂模式
        SqlSessionFactoryBuilder sb = new SqlSessionFactoryBuilder();
        final SqlSessionFactory factory = sb.build(asStream);
        // java与数据库的session
        // 设置自动提交事务
        final SqlSession sqlSession = factory.openSession(true);
        // 获取mapper接口对象,代理模式
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 测试
        final int i = mapper.insertUser();
        final int i2 = mapper.updateUser();
        final int i3 = mapper.deleteUser();
        // 提交实现
//        sqlSession.commit();
        System.out.println(i);
        System.out.println(i2);
        System.out.println(i3);
    }

    @Test
    public void test() throws IOException {
        final InputStream asStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sb = new SqlSessionFactoryBuilder();
        final SqlSessionFactory factory = sb.build(asStream);
        final SqlSession sqlSession = factory.openSession(true);
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final User user = mapper.selectUser();
        System.out.println(user);
    }

    @Test
    public void test33() throws IOException {
        final InputStream asStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sb = new SqlSessionFactoryBuilder();
        final SqlSessionFactory factory = sb.build(asStream);
        final SqlSession sqlSession = factory.openSession(true);
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final ArrayList<User> users = mapper.selectUsers(10,3);
        System.out.println(users);
    }

    @Test
    public void test4() {
        final SqlSession sqlSession = getSqlSession();
        /**
         * ${}字符串拼接，sql注入
         * #{}占位符赋值
         * MyBatis获取参数值,占位符拼接，直接与顺粗有关与名称无关
         * 字符串拼接不会手动生成‘’
         * 可以以任意的字符串获取参数值
         *
         */
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        /**
         * 多个参数会生成两个hashmap放入参数值
         * 单引号问题还是存在
         */
        User user = mapper.selectUser2(3, 3);
        System.out.println(user);
        /**
         * 可以自己设置mapper接口方法的参数可以手动将参数放入map
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 3);
        map.put("num1", 3);
        user = mapper.selectUser4(map);
        System.out.println(user);
    }

    @Test
    public void t() {
        /**
         * mapper接口的方法参数为实体类对象
         * #{}以属性值访问
         */
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User users = new User(null, 123, "123", 123, 123, "123", "123",new Dept(1,"123"));
        mapper.insertUser4(users);
    }

    @Test
    public void t2() {
        /**
         * @Param标志注解，明明参数
         * 这种方法可以调整顺序
         */
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final User user = mapper.selectUser33(3, 3);
        System.out.println(user);

        /**
         * mybatis sql语句拼接有几种方式？
         * 1.首先单变量的无所谓采用什么方式，{}中填入什么值
         * 2.采用mybatis自定义的hashmap存储arg0,param0
         * 3.采用map，类似于param注解
         * 4.采用实体类的get set
         * 5.采用param注解
         */
        System.out.println(mapper.getCount());
        System.out.println(mapper.getUserById());
        System.out.println(mapper.getUserById2(2));
    }

    @Test
    public void t5(){
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final List<User> byLike = mapper.getByLike("780a");
        System.out.println(byLike);
    }

    @Test
    public void t6(){
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.getCount());
        mapper.deleteMore("1,2,3,4");
        System.out.println(mapper.getCount());
    }

    @Test
    public void t7(){
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final List<User> test2 = mapper.selectFromTable("test2");
        System.out.println(test2);
    }

    @Test
    public void t8(){
        final SqlSession sqlSession = getSqlSession();
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User users = new User(null, 123, "123", 123, 123, "123", "123",new Dept(null,"123"));
        mapper.insertUserss(users);
        System.out.println(users);


    }
}
