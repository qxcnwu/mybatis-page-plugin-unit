package com.qxc.mybatis.pojo;

import java.io.Serializable;

/**
 * @Author qxc
 * @Date 2023 2023/8/4 22:57
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.pojo
 */
public class Atest implements Serializable {
    private Integer id;
    private String name;
    private Integer deptid;
    private Dept dept;

    @Override
    public String toString() {
        return "Atest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept=" + dept +
                '}';
    }

    public Integer getDeptid() {
        return deptid;
    }

    public Atest setDeptid(Integer deptid) {
        this.deptid = deptid;
        return this;
    }

    public Atest(Integer id, String name, Integer deptid, Dept dept) {
        this.id = id;
        this.name = name;
        this.deptid = deptid;
        this.dept = dept;
    }

    public Atest() {
    }

    public Integer getId() {
        return id;
    }

    public Atest setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Atest setName(String name) {
        this.name = name;
        return this;
    }

    public Dept getDept() {
        return dept;
    }

    public Atest setDept(Dept dept) {
        this.dept = dept;
        return this;
    }
}
