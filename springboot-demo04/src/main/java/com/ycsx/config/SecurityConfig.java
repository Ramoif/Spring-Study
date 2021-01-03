package com.ycsx.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //实现首页所有人可以访问，对应的功能页面只有对应权限人可以访问。
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2");

        //没有权限默认回到登录页面，需要开启登录页面(默认/login)
        //在没有登录的情况下，点击任何页面都会跳转到登录页面。
        http.formLogin();
        //开启注销功能，登出成功跳转到首页
        http.logout().logoutSuccessUrl("/");
        //防止网站工具：get，post
        http.csrf().disable();//关闭csrf功能，登陆失败肯定存在的原因。
        //开启记住我的功能 cookie默认保存两周，自定义接受前端的参数
        http.rememberMe().rememberMeParameter("remember");
    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //正常应该从数据库中读取。这里演示了两种创建用户的方式。
        //这里会报错的使用上面第一个方式，加密密码。下面是不加密的写法。
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("1234"))
                .roles("vip2","vip3")
                .and()
                .withUser("root")
                .password("123456")
                .roles("vip1","vip2","vip3");
    }
}