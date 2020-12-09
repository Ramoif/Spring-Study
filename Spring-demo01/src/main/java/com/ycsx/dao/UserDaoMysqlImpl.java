package com.ycsx.dao;

public class UserDaoMysqlImpl implements UserDao {

    @Override
    public void getUser() {
        System.out.println("Mysql调用了UserDao()接口方法！");
    }
}
