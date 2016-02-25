package com.example.administrator.entity;

/**
 * Created by Administrator on 2016/2/25.
 * 用一个Person类来封装姓名，性别，电话，备注这些数据。里面只有get()和set()方法
 */
public class Person {
    private int id;
    private String name;
    private String sex;
    private String number;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
