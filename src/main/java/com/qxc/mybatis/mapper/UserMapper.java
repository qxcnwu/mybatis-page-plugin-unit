package com.qxc.mybatis.mapper;

import com.qxc.mybatis.interceptor.PageSplit;
import com.qxc.mybatis.interceptor.PageType;
import com.qxc.mybatis.interceptor.PageValue;
import com.qxc.mybatis.pojo.Dept;
import com.qxc.mybatis.pojo.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 10:00
 * @Version 1.0
 * @PACKAGE com.qxc
 */
public interface UserMapper {
    /**
     * 插入对象
     *
     * @return
     */
    int insertUser();

    int updateUser();

    int deleteUser();

    User selectUser();

    @PageSplit(page = 1, count = 5)
    ArrayList<User> selectUsers(@Param("page") @PageValue(type = PageType.PAGE, value = 2) Integer page, @Param("count") @PageValue(type = PageType.COUNT, value = 3) Integer count);

    /**
     * 获取参数
     */
    User selectUser1(int id);

    User selectUser2(int id, int num1);

    User selectUser33(@Param("id") int id, @Param("num1") int num1);

    User selectUser4(Map<String, Object> map);

    void insertUser4(User user);

    /**
     * 查询总数
     */
    int getCount();

    /**
     * 查询map
     */
    @MapKey("id")
    Map<String, Object> getUserById();

    Map<String, Object> getUserById2(@Param("id") Integer id);

    /**
     * 模糊查询
     */
    List<User> getByLike(@Param("username") String username);

    /**
     * 批量删除
     */
    void deleteMore(@Param("ids") String ids);

    List<User> selectFromTable(@Param("tableName") String name);

    void insertUserss(User user);

    /**
     * 处理多对一银蛇关系
     */
    Dept getDept(@Param("id") int id);

    User getDeptByStep(@Param("id") int id);

}