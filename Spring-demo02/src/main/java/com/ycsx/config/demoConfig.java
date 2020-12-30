package com.ycsx.config;

import com.ycsx.pojo.User;
import com.ycsx.pojo.User2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* 会被Spring容器托管。注册到容器。本质是一个@Component，@Configuration代表是一个配置类(.xml) */
@Configuration
@ComponentScan("com.ycsx.pojo")
public class demoConfig {
    /* 注册了一个Bean，方法名=id；返回值=class；return值=注入的对象。 */
    @Bean
    public User2 getUser(){
        return new User2();
    }
}
