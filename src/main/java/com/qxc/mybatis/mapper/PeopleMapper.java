package com.qxc.mybatis.mapper;

import com.qxc.mybatis.pojo.People;
import org.apache.ibatis.annotations.Param;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 15:54
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.mapper
 */
public interface PeopleMapper {

    People selectById(@Param("id") Integer id);

}
