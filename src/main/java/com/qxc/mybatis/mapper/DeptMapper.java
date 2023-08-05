package com.qxc.mybatis.mapper;

import com.qxc.mybatis.pojo.Dept;
import org.apache.ibatis.annotations.Param;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 16:10
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.mapper
 */
public interface DeptMapper {
    Dept getById(@Param("id") Integer id);

    Dept getAllAtest(@Param("id") Integer id);
    Dept getAllAtestSteps(@Param("id") Integer id);
}
