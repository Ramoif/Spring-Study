package com.ycsx.pojo;

public class User {
    private String name;

    public User() {
    }

    /* 有参数构造的三种实现：
    * 1.下标赋值:<constructor-arg index="0" value="XXX"/>
    * 2.
    * */
    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("name = " + name);
    }
}
