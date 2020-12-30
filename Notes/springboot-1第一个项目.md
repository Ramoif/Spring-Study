微服务阶段
```text
笔记摘自https://dwz.cn/P1N121RT
JavaSe                      OOP
Mysql                       持久化
html+css+js+jquery+框架     视图
JavaWeb                     mvc三层架构
SSM                         框架简化开发
SpringBoot                  微服务架构
SpringCloud                 服务拓展
Linux
JVM
MybatisPlus
```
SpringBoot
```text
1、SpringBoot是什么？
2、配置编写yaml
3、自动装配（☆）
4、集成web开发
5、集成数据库 Druid
6、分布式开发 Dubbo+zookeeper
7、swagger 接口文档
8、任务调度
9、SpringSecurity : Shiro 安全
```
SpringCloud
```text
1、微服务
2、RestFul风格
3、Eureka
4、Ribbon和Feign
5、HyStrix
6、Zuul 路由网关
7、SpringCloud Config : git
```
什么是SpringBoot
```text
Spring是为了解决企业级应用开发的复杂性，简化开发。Spring的IOC，AOP两个核心理念。
Spring是一个javaweb的开发框架，和SpringMVC类似，约定大于配置。
它能够迅速的开发web应用。几行代码开发一个http接口。
约定大于配置的核心思想，默认帮我们进行了很多设置。集成了大量的第三方库配置。
使用Tomcat作为默认的嵌入式容器。
```
第一个SpringBoot项目
```text
1、使用官网的快速搭建（在Notes下有我创建的一个zip文件，是官网快速生成的）
2、使用IDEA创建。
    NewProject选择Spring Initializr
3、删除多余的文件。
4、编写一个HelloController测试。
5、application.properties配置一个端口，默认8080
6、配置一个banner.txt来改变启动时候的控制台图片。
```