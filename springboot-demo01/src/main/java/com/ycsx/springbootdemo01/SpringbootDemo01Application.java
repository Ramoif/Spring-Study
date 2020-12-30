package com.ycsx.springbootdemo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*标注这是一个springboot的应用，启动类下的所有资源·导入*/
@SpringBootApplication
public class SpringbootDemo01Application {

    public static void main(String[] args) {
        /*启动应用*/
        /*run代表开启了一个服务*/
        SpringApplication.run(SpringbootDemo01Application.class, args);
        /*SpringApplication这个类做了以下四件事情
        * 1.推断应用的类型是普通项目还是Web项目。
        * 2.查找并且加载所有的可用初始化器(?)，设置到initializers属性中
        * 3.找出所有的应用程序监听器，设置到listeners属性中
        * 4.推断并设置main方法的定义类，找到运行的主类*/
    }

}
