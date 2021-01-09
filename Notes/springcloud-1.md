#### 前言 - 微服务架构的问题
微服务架构实现了模块化和功能化，但是也有一些问题。
1. 大量服务，客户端如何去访问？
2. 大量服务，服务之间的通信问题？
3. 大量服务，如何治理？
4. 服务出现问题，怎么解决？  

于是出现了SpringCloud - 基于SpringBoot的一套**生态**用来解决上述问题。  
* Spring Cloud NetFlix 的一站式解决方案，但是已经停止维护。
    * Api网关 - zuul组件 
    * Feign - HttpClient - Http的通信方式，同步并阻塞
    * 服务注册与发现 - Eureka
    * 熔断机制 - Hystrix
* Apache Dubbo zookeper 第二套解决系统
    * API - 需要第三方组件
    * Dubbo - RPC通信框架，基于java实现，高性能
    * 服务注册与发现 - zookeeper （hadoop，Hive）
    * 熔断机制 - 借助Hystrix
* SpringCloud Alibaba 一站式解决方案
* 服务网格 - 下一代微服务标准 Service Mesh
* 代表解决方案 - istio

他们都是解决下面四个问题，因为**网络是不可靠**的
* API网关 - 服务路由
* HTTP，PRC框架 - 异步调用
* 服务注册与发现 - 高可用
* 熔断机制 - 服务降级