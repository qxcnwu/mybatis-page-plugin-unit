package com.qxc.mybatis.mapper;

import com.qxc.mybatis.pojo.Atest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 22:58
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.mapper
 */
public interface AtestMapper {
    Atest getDeptByStep(@Param("id") int id);

    List<Atest> getByDept(@Param("did") Integer did);

    List<Atest> selectByCondition(Atest atest);

    List<Atest> selectByChose(Atest atest);

    void insertDepts(@Param("lists") List<Atest> lists);
    void deleteDepts(@Param("lists") List<Integer> lists);
}
