package com.qxc.mybatis.pojo;

import java.io.Serializable;
import java.util.List;


public class Dept implements Serializable {
    private Integer id;
    private String name;
    private List<Atest> atests;

    public List<Atest> getAtests() {
        return atests;
    }

    public Dept setAtests(List<Atest> atests) {
        this.atests = atests;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Dept setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Dept setName(String name) {
        this.name = name;
        return this;
    }

    public Dept(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Dept() {
        System.out.println("无参构造");
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", atests=" + atests +
                '}';
    }
}
