SSM整合（Spring+SpringMVC+Mybatis）
```text
环境要求：
IDEA、Mysql5.7、Tomcat9、Maven。
知识要求：
MySql、Spring、JavaWeb原理、Mybatis、简单前端知识。
```
设计的开发步骤：第一步、数据库构建
```text
开始构建自己的项目，思考数据库要创建一些什么内容？
主要的步骤依次是：
    需求分析、设计数据库、业务、前端页面。
```
建表和插入
```sql
create table `books` (
    `bookID` int(10) not null auto_increment comment '书id',
    `bookName` varchar(100) not null comment '书名',
    `bookCounts` int(11) not null comment '数量',
    `detail` varchar(200) not null comment '描述',
    key `bookID` (`bookID`)
) engine = INNODB default charset = utf8

insert into `books`(bookID, bookName, bookCounts, detail) values
(1,'Java',1,'从入门到放弃'),
(2,'Mysql',10,'从删库到跑路'),
(3,'Linux',5,'从学习到自闭');
```
开始创建项目-整合SSM的Mybatis层
```text
第一步：导入依赖。（本案例直接使用了子项目...）
第二步：IDEA连接数据库（方便查看）
第三步：建立项目的java包结构（dao、pojo、controller、service）
第四步：建立resources包下的两个配置文件：applicationContext和mybatis-config
第五步：搞定mybatis连接数据库的配置，建立一个database.properties
第六步：编写mybatis-config，编写pojo实体类
第七步：编写dao层接口，和Mapper(mybatis)实现增删改查 ☆重点复习
第八步：编写Mapper，然后将它注册到mybatis-config中
第九步：编写业务层service接口和实现类Impl（service调用dao层）
```
第一步中需要准备的东西
```xml
<!--
需要的依赖：junit、数据库驱动、数据库连接池(c3p0 / dbcp)、servlet、jsp、mybatis、mybatis-Spring、Spring。
数据库连接池的依赖：
-->
<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.5.2</version>
</dependency>

<!--静态资源导出的问题:(pom.xml)-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```
整合SSM-Spring层(spring-dao.xml)
```text
第一步：新建一个spring-dao.xml
第二步：编写spring-dao.xml中的关联数据库配置文件
    <context:property-placeholder location="classpath:database.properties"/>
第三步：连接池
（dbcp：半自动化，不能自动连接； c3p0：自动化操作，自动加载配置文件，自动设置到对象中；druid、hikari了解）
  我们这里使用c3p0，前四个是必备属性。后续为特有属性。
    <bean id="datasource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <!--分别是 c3p0连接池的私有属性、关闭连接后不自动commit、获取连接超时时间、当获取连接失败的重试次数。-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <property name="autoCommitOnClose" value="false"/>
        <property name="checkoutTimeout" value="10000"/>
        <property name="acquireRetryAttempts" value="2"/>
    </bean>
第四步：sqlSessionFactory
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--引用数据源-->
        <property name="dataSource" ref="datasource"/>
        <!--绑定mybatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>
第五步：配置Dao自动扫描注入到Spring。
```
Spring层(spring-service.xml)
```text
第一步：扫描service下的包
    <context:component-scan base-package="com.ycsx.service"/>
第二步：将所有的业务类，注入到Spring中，或者通过注解@Service和@Autowired实现。
    <bean id="BookServiceImpl" class="com.ycsx.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>
第三步：声明式事务配置
    <bean id="TransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="datasource"/>
    </bean>
第四步：aop事务支持
    暂无。
备注：第二步中bookMapper报错，需要在applicationContext中import两个Spring的xml。
```
SSM整合-SpringMVC层
```text
第一步：添加项目的web框架。（右键idea配置）
第二步：开始编写web.xml
第三步：Dispatcher
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
第四步：乱码过滤
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
第五步：Session过期时间设置
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
```
spring-mvc.xml
```text
第1步：注解驱动
第2步：静态资源过滤
第3步：扫描包：controller
第4步：视图解析器
下面是配置：
    <!--第一步 注解驱动-->
    <mvc:annotation-driven/>
    <!--第二步 静态资源过滤-->
    <mvc:default-servlet-handler/>
    <!--第三步 扫描controller包-->
    <context:component-scan base-package="com.ycsx.controller"/>
    <!--第四步 视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```
到此框架整合结束。开始业务编写。