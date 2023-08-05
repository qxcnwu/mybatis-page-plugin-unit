# mybatis-page-plugin-unit

mybatis-page-plugin-unit是一个帮助mybatis进行分页的插件，通过实现mybatis中的Interceptor接口，在初始化StatementHandler过程中设置AOP切点，对原始查询方法的注解进行判断，查询是否为分页业务，并且通过缓存加速查询。

标准使用模式如下

1. 导入mybatis-config.xml配置文件

```xml
<plugins>
    <plugin interceptor="com.qxc.mybatis.interceptor.PageInterceptor">
        <property name="catchError" value="false"/>
    </plugin>
</plugins>
```

2. 使用

```java
// 基于方法注解
@PageSplit(page = 1, count = 5)
ArrayList<User> selectUsers(@Param("page") @PageValue(type = PageType.PAGE) Integer page, @Param("count") @PageValue(type = PageType.COUNT) Integer count);
// 基于参数注解
ArrayList<User> selectUsers(@Param("page") @PageValue(type = PageType.PAGE) Integer page, @Param("count") @PageValue(type = PageType.COUNT) Integer count);
```

## PageSplit方法注解

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageSplit {
    // 第几页
    int page() default 1;

    // 每一页默认几条数据
    int count() default 10;
}
```

**PageSplit注解实现在方法上标注分页功能的默认值，包括默认查询页面编号，默认每一页的容量，使用方法如下，我们将默认页面设置为1，默认的页面容量设置为5，拥有了方法注解之后可以在参数中省略对应的页面容量，设置为固定容量**

```java
@PageSplit(page = 1, count = 5)
ArrayList<User> selectUsers(@Param("page") @PageValue(type = PageType.PAGE) Integer page);
```

**当然如下的操作也是合法的，不过参数中注解的优先级高于方法的参数优先级，会将方法中的默认参数进行覆盖**

```java
@PageSplit(page = 1, count = 5)
ArrayList<User> selectUsers(@Param("page") @PageValue(type = PageType.PAGE) Integer page, @Param("count") @PageValue(type = PageType.COUNT) Integer count);
```

## PageValue参数注解

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PageValue {
    int value() default 1;
    PageType type() default PageType.PAGE;
}
```

**参数注解实现了分页业务中页面以及容量参数的指定，这是不得已而为之的办法，注解中的PageType包括页面PageType.PAGE以及容量PageType.COUNT**

## Properties属性

> 在配置文件中提供了页面验证属性，当查询页面大于所有页面时根据属性判断是否抛出Runningtime异常

```xml
<plugins>
    <plugin interceptor="com.qxc.mybatis.interceptor.PageInterceptor">
        // 默认为false
        <property name="catchError" value="false"/>
    </plugin>
</plugins>
```

## 具体实现

详见**src/main/java/com/qxc/mybatis/interceptor/PageInterceptor.java**

## 案例

```java
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
    public void test33() throws IOException {
        final InputStream asStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sb = new SqlSessionFactoryBuilder();
        final SqlSessionFactory factory = sb.build(asStream);
        final SqlSession sqlSession = factory.openSession(true);
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final ArrayList<User> users = mapper.selectUsers(10,3);
        System.out.println(users);
    }
}
```

1. 不开启页面检查

![image-20230805171244280](https://raw.githubusercontent.com/qxcnwu/mybatis-page-plugin-unit/master/image-20230805171244280.png))

2. 开启页面检查

![image-20230805171402737.png (1564×848) (raw.githubusercontent.com)](https://raw.githubusercontent.com/qxcnwu/mybatis-page-plugin-unit/master/image-20230805171402737.png)
