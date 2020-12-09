AOP和IOC
```text
两大核心：AOP，IOC：面向切面编程和轻量级的控制反转。
Spring就是一个IOC，AOP的框架。

重点：di依赖   
      Autowire自动装配
      annotation注解支持
      proxy动态和静态反射
      mybatis整合

其他的笔记：
再次复习execution：
第一个*代表返回类型 *是所有的类型，
第二个是包名，
第二个*号代表类名:
*表示所有的类，(..)代表方法名，..代表所有的参数。 *(..)
execution(* com.ycsx.service.UserServiceImpl.*(..))
```


Mybatis的简单整合步骤：
```text
1、编写数据源dataSource配置
    (重点是2、SqlSessionFactory和3、SqlSessionTemplate)
2、SqlSessionFactory
3、SqlSessionTemplate
4、给接口添加实现类
5、实现类注入Spring

目录结构
java.*(..)（
    mapper(
         UserMapper = 接口
         UserMapper.xml = 实现的sql语句。
         UserMapperImpl = 实现类
         )、
    pojo = 基类
）
resources（
    applicationContext = 导入和配置mapper、
    spring-dao = 专注数据库连接、
    mybatis-config = 别名和设置
）
```


Spring-Dao.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--第一步：sqlSessionFactory对象和DataSource对象。-->
    <!--DataSource使用Spring的数据源替换Mybatis的配置，其实就是取代mybatis-config（c3p0 dbcp druid）-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/pro_db?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
        <property name="username" value="root"/>
        <property name="password" value="1234"/>
    </bean>
    <!--sqlSessionFactory取代utils工具类中的Mybatis获取类。直接取用即可。-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/ycsx/mapper/*.xml"/>
    </bean>
    <!--Template就是我们使用的sqlSession-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!--因为没有set方法。所以只能使用构造器注入。-->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
    <!--已经删除了其他的东西，现在这个配置文件专注于操作数据库。-->
</beans>
```


Mybatis-config.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <typeAlias type="com.ycsx.pojo.User" alias="user"/>
    </typeAliases>
</configuration>
```


applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
    <import resource="spring-dao.xml"/>
    <bean id="userMapper" class="com.ycsx.mapper.UserMapperImpl">
        <property name="sqlSession" ref="sqlSession"/>
    </bean>
</beans>
```


事务的ACID原则
```text
原子性、一致性、隔离性、持久性
只有成功和失败，不允许完成一半。（插入删除只有一个操作成功了。）
所以需要有事务管理。(声明式事务AOP(交给容器)，编程式事务try catch)
```


UserMapperImpl.java
```java
package com.ycsx.mapper;

import com.ycsx.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper{
    //所有操作都需要sqlSession来执行。现在使用sqlSessionTemplate
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> selectUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectUser();
    }
}
```

