package com.ycsx.dao;

public class UserDaoOracleImpl implements UserDao {

    @Override
    public void getUser() {
        System.out.println("Oracle调用了UserDao()接口方法！");
    }
}
