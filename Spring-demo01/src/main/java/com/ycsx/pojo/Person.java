package com.ycsx.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

@Data
public class Person {
    /* 直接在属性上设置自动装配，还有一个方法（@Resource） */
    @Resource
    private Cat cat;

    /*Autowired-Qualifier组合使用(byName)*/
    @Autowired
    @Qualifier(value = "dog2")
    private Dog dog;
    private String name;
}
