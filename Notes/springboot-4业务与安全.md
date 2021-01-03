#### 完善知识
之前已经学习的知识，已经能够借用模板搭建一个简单的网站页面了。  
但是第三个案例使用的并没有接触到数据库。接下来应该要接触的有？
* JDBC 结合数据库
* Mybatis / MybatisPlus
* Druid
* Shiro - 安全框架
* Spring Security - 安全框架
* 异步任务，邮件发送，定时任务
* Swagger，Dubbo + Zookeeper ...
---
#### JDBC
一定会接触到的**数据库**打交道环节。  
首先我们创建一个新的模块/项目。创建的时候勾选上SQL- JDBC API 和 MySQL Driver 两个选项。
（不勾选的话就自己导入相关依赖即可）
```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <scope>runtime</scope>
</dependency>
```
写一个测试类，测试一下和数据库的连接。  
查看到了打印的默认数据源：class com.zaxxer.hikari.HikariDataSource
测试连接，报错java.sql.SQLException:   
The server time zone value '�й���׼ʱ��' is unrecognized  
需要在url加上时区设置。serverTimezone=UTC
---
#### 链接数据库在网页上打印出json格式类型的数据
```java
//需要加上(@RestController)注解
//查询数据库的所有信息(没有实体类如何查询)
    @GetMapping("/userList")
    public List<Map<String,Object>> userList(){
        String sql = "select * from books";
        List<Map<String,Object>> list_maps = jdbcTemplate.queryForList(sql);
        return list_maps;
    }
```
通过上面的代码，启动springboot，在网页上成功打印出来我之前项目中存放在books中的数据。  
```json
[{"bookID":1,"bookName":"Java","bookCounts":1,"detail":"从入门到放弃"},
{"bookID":2,"bookName":"Mysql","bookCounts":10,"detail":"从删库到跑路"},
{"bookID":3,"bookName":"Linux","bookCounts":5,"detail":"从学习到自闭"}]
```
增删改查：增加一本书
```java
//增加一本书
@GetMapping("/addBook")
public String addBook(){
    String sql = "insert into books(bookID,bookName,bookCounts,detail) values(10,'Mybatis',20,'从入坑到独创');";
    jdbcTemplate.update(sql);
    return "add-AllRight";
}
```
改
```java
//改
@GetMapping("/update/{bookID}")
public String updateBook(@PathVariable("bookID") int id) {
    String sql = "update books set bookName=?,bookCounts=?,detail=? where bookID=" + id;
    Object[] objects = new Object[3];
    objects[0] = "MybatisPlus";
    objects[1] = 21;
    objects[2] = "从mybatis到Plus";
    jdbcTemplate.update(sql,objects);
    return "update-AllRight";
}
```
---
### Druid 数据源
Druid(德鲁伊) 是阿里巴巴开源平台上的一个数据库连接池实现。结合了c3p0，DBCP，PROXOOL等
DB池的优点，加入了日志监控。Druid可以很好地监控DB池连接和Sql的执行情况，针对监控而生的
DB连接池。
```xml
<!--Druid-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>
```
在application.yml中更换默认的hikari数据源为Druid，并且需要自己绑定一些配置，因为SpringBoot默认不注入。
```yaml
spring:
  datasource:
    username: root
    password: 1234
    url: jdbc:mysql://localhost:3306/pro_db?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource

    #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true

    #配置监控统计拦截的filters，sat：监控统计、log4j：日志记录、wall：防御sql注入
    #如果允许时报错，则导入log4j的依赖即可。（报错了，所以我导入了log4j的依赖）
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```
#### Druid Monitor
创建一个config文件夹，编写DruidConfig
```java
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控
    /*SpringBoot内置了Servlet容器，没有web.xml，替代方法ServletRegistrationBean*/
    @Bean
    public ServletRegistrationBean a(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //后台需要有人登陆，账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();
        //增加配置
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","1234");
        //允许谁访问
        initParameters.put("allow","");
        //禁止谁访问
        //initParameters.put("Mack","192.168.99.61");
        bean.setInitParameters(initParameters);
        return bean;
    }

    //过滤器filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
```
打开http://localhost:8080/druid，可以看到一个登陆的页面。
直接输入上面配置的固定的用户名和密码。登录后显示Druid Monitor，登录成功。
---
### Mybatis
启动器名称：mybatis-spring-boot-starter
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
```
可以看到，他的starter和其他的依赖不同。  
非Spring官方的使用框架名称作为开头。  
  
配置yaml，在resources下新建一个mybatis的文件夹，创建一个BookMapper.xml编写。  
在application中整合mybatis
```yaml
mybatis:
  type-aliases-package: com.ycsx.pojo
  mapper-locations: classpath:mybatis/mapper/*.xml
```
BookMapper.java
```java
//注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface BookMapper {
    List<Book> queryBookList();
    Book queryBookById(int bookID);
    int addBook(Book book);
    int updateBook(Book book);
    int deleteBook(Book book);
}
```
BookMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ycsx.mapper.BookMapper">
    <select id="queryBookList" resultType="Book">
        select * from books;
    </select>
    <select id="queryBookById" resultType="Book">
        select * from books where bookID = #{bookID};
    </select>
    <insert id="addBook" parameterType="Book">
        insert into books(bookID, bookName, bookCounts, detail)
        VALUES (#{bookID}, #{bookName}, #{bookCounts}, #{detail});
    </insert>
    <update id="updateBook" parameterType="Book">
        update books
        set bookName=#{bookName},
            bookCounts=#{bookCounts},
            detail=#{detail}
        where bookID = #{bookID};
    </update>
    <delete id="deleteBook" parameterType="int">
        delete from books where bookID = #{u_id};
    </delete>
</mapper>
```
BookController.java
```java
@RestController
public class BookController {
    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/queryBookList")
    public List<Book> queryUserList(){
        List<Book> bookList = bookMapper.queryBookList();
        for (Book book : bookList) {
            System.out.println(book);
        }
        return bookList;
    }
    //下面写事务。这里略。
}
```
---
### SpringSecurity 和 Shiro (安全框架)
web开发中的第一位就是安全！  过滤器，拦截器！不能隐私泄露。
所以必须在设计阶段就应该考虑这个问题。知名的框架有Shiro和SpringSecurity。
主要的就是SpringSecurity和Shiro。  

* 功能权限
* 访问权限
* 菜单权限
* 拦截器，过滤器  
大量的原生代码，冗余！所以出现了框架来管理这个问题。  
---
#### SpringSecurity
他是针对Spring项目的安全框架， 也是SpringBoot底层安全模块默认的技术选型，
他可以实现强大的Web安全控制，我们只需要引入spring-boot-starter-security模块，
进行少量的配置就可以实现强大的安全管理。  
需要记住几个类：  
* WebSecurityConfigurerAdapter - 自定义Security策略
* AuthenticationManagerBuilder - 自定义认证策略
* @EnableWebSecurity - 开启WebSecurity模式
* 通用的概念：认证(Authentication)和授权(Authorization)  

导入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
项目中没有具体页面，所以这里举例仅用代码演示：
```java
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
```
---
### Shiro



