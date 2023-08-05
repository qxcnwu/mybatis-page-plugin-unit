package com.qxc.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;


public class User {
    private Integer id;
    private Integer num1;
    private String num2;
    private Integer type1;
    private Integer type2;
    private String str1;
    private String str2;
    private Dept dept;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getNum1() {
        return num1;
    }

    public String getNum2() {
        return num2;
    }

    public Integer getType1() {
        return type1;
    }

    public Integer getType2() {
        return type2;
    }

    public String getStr1() {
        return str1;
    }

    public String getStr2() {
        return str2;
    }

    public Dept getDept() {
        return dept;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public User setNum1(Integer num1) {
        this.num1 = num1;
        return this;
    }

    public User setNum2(String num2) {
        this.num2 = num2;
        return this;
    }

    public User setType1(Integer type1) {
        this.type1 = type1;
        return this;
    }

    public User setType2(Integer type2) {
        this.type2 = type2;
        return this;
    }

    public User setStr1(String str1) {
        this.str1 = str1;
        return this;
    }

    public User setStr2(String str2) {
        this.str2 = str2;
        return this;
    }

    public User setDept(Dept dept) {
        this.dept = dept;
        return this;
    }

    public User(Integer id, Integer num1, String num2, Integer type1, Integer type2, String str1, String str2, Dept dept) {
        this.id = id;
        this.num1 = num1;
        this.num2 = num2;
        this.type1 = type1;
        this.type2 = type2;
        this.str1 = str1;
        this.str2 = str2;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", num1=" + num1 +
                ", num2='" + num2 + '\'' +
                ", type1=" + type1 +
                ", type2=" + type2 +
                ", str1='" + str1 + '\'' +
                ", str2='" + str2 + '\'' +
                ", dept=" + dept +
                '}';
    }
}
