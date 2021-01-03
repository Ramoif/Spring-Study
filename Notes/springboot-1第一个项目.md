### 什么是SpringBoot？
##### (摘自知乎)
SpringBoot简介

* 1、什么是SpringBoot
Spring Boot 是所有基于 Spring 开发的项目的起点。
Spring Boot 的设计是为了让你尽可能快的跑起来 Spring 应用程序并且尽可能减少你的配置文件。
简单来说就是SpringBoot其实不是什么新的框架，它默认配置了很多框架的使用方式，
就像maven整合了所有的jar包，spring boot整合了所有的框架（不知道这样比喻是否合适）。

* 2、SpringBoot四个主要特性
    1. SpringBoot Starter：他将常用的依赖分组进行了整合，
将其合并到一个依赖中，这样就可以一次性添加到项目的Maven或Gradle构建中；
    2. 自动配置：SpringBoot的自动配置特性利用了Spring4对条件化配置的支持，
合理地推测应用所需的bean并自动化配置他们；
    3. 命令行接口：（Command-line-interface, CLI）：
SpringBoot的CLI发挥了Groovy编程语言的优势，
并结合自动配置进一步简化Spring应用的开发；
    4. Actuatir：它为SpringBoot应用的所有特性构建一个小型的应用程序。
但首先，我们快速了解每项特性，更好的体验他们如何简化Spring编程模型。

* 3、SpringBoot开发的具体好处
回顾我们之前的 SSM 项目，搭建过程还是比较繁琐的，需要：
    * 1、配置web.xml，加载spring和spring mvc
    * 2、配置数据库连接、配置spring事务
    * 3、配置加载配置文件的读取，开启注解

配置完成之后部署tomcat 调试
而使用 SpringBoot 来开发项目则只需要非常少的几个配置就可以搭建起来一个Web项目。
并且利用 IDEA 可以自动生成生成，~~这简直是太方便了？？？~~

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