package com.ycsx.config;

import com.ycsx.pojo.User;
import com.ycsx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权-doGet");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证方法-doGetAuthorizationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        /*连接真实的数据库*/
        User user = userService.queryUserByName(userToken.getUsername());
        if (user == null) {
            return null;    //UnknownAccountException
        }
        return new SimpleAuthenticationInfo("", user.getPwd(), "");
    }

    /*临时测试*/
    /*protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证-doGet");
        *//*测试用账户密码*//*
        String name = "root";
        String password = "1234";
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        if (!userToken.getUsername().equals(name)) {
            return null;//抛出异常
        }
        *//*密码认证，shiro*//*
        return new SimpleAuthenticationInfo("",password,"");
    }*/
}
