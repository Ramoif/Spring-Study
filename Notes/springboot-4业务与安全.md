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
```text
查看到了打印的默认数据源：class com.zaxxer.hikari.HikariDataSource
测试连接，报错 java.sql.SQLException:   
     " The server time zone value '�й���׼ʱ��' is unrecognized "
我们需要在url加上时区设置。serverTimezone=UTC
```
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
```text
打开http://localhost:8080/druid，可以看到一个登陆的页面。
直接输入上面配置的固定的用户名和密码。登录后显示Druid Monitor，登录成功。
```
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
项目中没有具体页面，所以这里举例仅用代码演示
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
首先需要几个依赖：
```xml
<!--Shiro 需要配合slf4j和log4j-->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-core</artifactId>
    <version>1.4.1</version>
</dependency>
<!--slf4j-->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.21</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.21</version>
</dependency>
<!--log4j-->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

```
配置的步骤为：
1. 导入Maven依赖
2. 编写shiro.ini
3. 用官方的快速启动案例测试  

这里说明快速启动案例中的几个重要方法：
* 获取当前用户对象subject `Subject currentUser = SecurityUtils.getSubject();`
* 通过当前用户获取Session `Session session = currentUser.getSession();`
* 判断当前用户是否被验证？ `if(!currentUser.isAuthenticated())`
* 获得当前用户认证 `currentUser.getPrincipal()`
* 是否拥有角色 `currentUser.hasRole("roleIdentifier")`
* 注销 `currentUser.logout()`

三个要素
* Subject 用户
* SecurityManager 管理所有用户
* Realm 连接数据  

编写ShiroConfig
```java
@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /*anon:无需验证
        * authc:需要认证
        * user:拥有 记住我 功能才能用
        * perms:拥有对某个资源的权限
        * role:拥有某个角色权限*/
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/add","authc");
        filterMap.put("/user/update","anon");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录请求
        bean.setLoginUrl("/toLogin");
        return bean;
    }

    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //关联UserRealm
        manager.setRealm(userRealm());
        return manager;
    }

    //Realm，需要自定义类，交给spring托管
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
```
编写UserRealm自定义类
```java
public class UserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权-doGet");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证-doGet");
        /*测试用账户密码*/
        String name = "root";
        String password = "1234";
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        if (!userToken.getUsername().equals(name)) {
            return null;//抛出异常
        }
        /*密码认证，shiro*/
        return new SimpleAuthenticationInfo("",password,"");
    }
}
```
#### 连接Mysql验证，实现数据库中的用户表和权限的认证。
新建一个User实体类，编写他的Mapper和Service，然后写一个测试类测试查询ByName方法。
User实体类中应该至少包括有用户名，密码，权限三个信息。
```text
Invalid bound statement (not found) 的解决办法
1.检查xml文件的namespace是否正确
2.Mapper.java的方法在Mapper.xml中没有，然后执行Mapper的方法会报此
3.xxxMapper.java的方法返回值是List,而select元素没有正确配置ResultMap,或者只配置ResultType
4.如果你确认没有以上问题,请任意修改下对应的xml文件,比如删除一个空行,保存.问题解决
5.看下mapper的XML配置路径是否正确
```
#### 实现简单的权限认证和用户名密码匹配认证，下面演示部分代码。  
UserRealm.java
```java
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权-doGet");

        //添加user:add授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("user:add");

        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();

        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证方法-doGetAuthorizationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        /*连接真实的数据库*/
        User user = userService.queryUserByName(userToken.getUsername());
        if (user == null) {
            return null;    //抛出UnknownAccountException
        }
        return new SimpleAuthenticationInfo(user, user.getPwd(), "");
    }
}
```
ShiroConfig.java
```java
@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /* 
            anon:无需验证
            authc:需要认证
            user:拥有 记住我 功能才能用
            perms:拥有对某个资源的权限
            role:拥有某个角色权限
        */

        Map<String, String> filterMap = new LinkedHashMap<>();

        //授权权限的语句，和未授权页面配套，在这里设置权限访问
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        //登录和未登录的权限
        /*filterMap.put("/user/add","authc");
        filterMap.put("/user/update","anon");*/

        bean.setFilterChainDefinitionMap(filterMap);
        //未授权页面
        bean.setUnauthorizedUrl("/noauth");

        //设置登录请求
        bean.setLoginUrl("/toLogin");

        return bean;
    }

    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //关联UserRealm
        manager.setRealm(userRealm());
        return manager;
    }

    //Realm，需要自定义类，交给spring托管
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
```
编写对应的Controller，首页，登录页面，error页面和功能页面。简单的项目构建好之后，
启动项目，测试在数据库中存在的和不存在的用户分别登陆，成功。  
测试数据库中没有update权限的root打开add页面成功，update跳转提示错误信息，完成功能测试。 
#### Controller部分代码
```java
@Controller
public class MyController {
    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","Hello,Shiro!");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);//执行登录的方法需要try catch捕获异常
            return "index";
        }catch (UnknownAccountException e){//用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){//密码不存在
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unAuthorized(){
        return "没有授权无法访问";
    }
}
```
#### Shiro整合Thymeleaf
待更新。

---
#### Swagger 接口
待更新。

---
#### 分布式 Duboo + Zookeeper
分布式系统是若干独立计算机的集合(对内是集合)。  
这些计算机对于用户来说就像单个相关系统(对用户显示为独立的)。  
分布式系统是由一组通过网络进行通信，为了完成共同的任务而协调工作的计算机节点组成的系统。  
分布式系统的出现是为了用廉价的，普通的机器完成单个计算机无法完成的计算和存储任务。  
目的是**利用更多的机器，处理更多的数据**。  

分布式系统 - 是建立在网络之上的软件系统。(distributed system)  
由于常规的垂直应用架构已经无法应对网站应用的规模的扩大。
分布式和流动计算架构（RPC和SOA）势在必行，需要一个治理系统确保架构有条不紊的演进。


**一些名词**
分布式系统的内部有http、rpc两种方式内部通信。
利用Nginx（代理服务器）来负载均衡(?)。  
Netty(?)

---
#### RPC
区分Http协议和RPC协议。  
RPC【Remote Procedure Call】 - 【远程过程调用】，是一种进程之间的通信方式。  
他有两个核心模块 - 通讯、序列化(数据传输需要转换)。  
它是一种技术的思想而不是规范。它允许程序调用另一个地址空间（通常是共享网络的另一台机器上）的过程或函数，
而不用程序员显式编码这个远程调用的细节。即程序员无论是调用本地还是远程的函数，本质上编写的调用代码基本相同。  

也就是说有两台服务器A、B，一个应用部署在A服务器，想要调用B服务器上应用提供的函数或方法。由于不在一个内存空间，
不能直接调用，需要通过网络来表达调用的语义和传达调用的数据。  
为什么要使用RPC呢？因为无法在一个进程内甚至一个计算机内通过本地调用的方式完成的需求。比如不同系统之间的通讯，
不同组织间的通讯，由于计算能力需要横向拓展，需要在多台机器组成的集群上部署应用。
RPC就是要像调用本地函数一样调用远程函数。  

举个例子：本地调用 - 把衣服扔进洗衣机洗。
远程过程调用 - 不在家，打个电话叫人去洗。

#### 安装与配置
待更新。

---
#### 异步和定时任务、邮件和Redis
待更新。

---
至此。SpringBoot的笔记结束。