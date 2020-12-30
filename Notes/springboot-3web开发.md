#### 实战前准备
- 要解决的问题
    1. 导入静态资源
    2. 首页
    3. jsp，模板引擎Thymeleaf
    4. 装配拓展SpringMVC
    5. CRUD
    6. 拦截器

然后新建我们的项目，勾选Spring Web。  
创建一个HelloController测试一下。成功打印了HelloWorld!
```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld!";
    }
}
```
#### webjars和处理静态资源的方式
```text
搜索webjars，打开官网，可以用有多种方式导入他的webjars。
SBT/Play2 = "org.webjars" % "jquery" % "3.5.1"
Maven（↓）
```
```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.5.1</version>
</dependency>
```
```text
v2.1.9用地址可以访问到源码（但失败了v2.4.1，还是放一个链接在这）
http://localhost:8080/webjars/jquery/3.5.1/jquery.js

在resources下创建public，resources，static三个文件夹。
分别放入1.js。启动项目测试http://localhost:8080/1.js，
先访问到了resources下的1.js，删除resources
发现访问到了static目录下的1.js
得出优先级resource->static->public

在springboot中可以用以下的方式处理静态资源：
1、webjars   [localhost:8080/webjars/]
2、public，static，/**，resources， [localhost:8080/]
```
####自定义首页、图标
```text
选择在resources/public下创建一个index.html
然后直接启动项目，发现首页变成了index的内容，更换首页成功了。
更换图标的话，在static下创建一个favicon.ico。
然后因为谷歌浏览器缓存清理发现没效果。
我选择使用Edge，发现图标更换成功了，测试成功。
```
#### Thymeleaf模板引擎
```text
首先先要引入thymeleaf。
spring-boot-starter-thymeleaf
```
```xml
        <!--thymeleaf-->
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-java8time</artifactId>
        </dependency>
```
```text
然后删除了我在static下的index首页。在templates下创建一个html文件。
重新编写了indexController，改成了test，启动项目测试，输入http://localhost:8080/test。跳转成功了。

结论：使用thymeleaf只需要导入对应的jar包即可，然后把html放到templates文件夹下。
```
####使用Thymeleaf，先导入命名空间
`<html xmlns:th="http://www.thymeleaf.org">`
#### Thymeleaf取值方式
```text
${...}  变量
*{...}  选择表达式
#{...}  消息
@{...}  url
~{...}  Fragment
```
这里编写了一个test.html，放入了`<div th:text="${msg}"></div>`测试，打印出了msg。  
#### Thymeleaf语法
```text
th:each
th:if
th:unless
th:switch/case
...

第一个测试：这里我们测试一个utext标签
model.addAttribute("msg","<h1>msg</h1>");
再次启动项目，打印发现：
text : <h1>msg</h1>
utext: msg

第二个测试：循环遍历
model.addAttribute("users", Arrays.asList("用户1","用户2"));
然后在页面上添加如下语句
<h3 th:each="user:${users}" th:text="${user}"></h3>
测试打印出来了两个用户名。成功。
```
#### thymeleaf常用命名空间
```html
xmlns:th="http://www.thymeleaf.org"
xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
xmlns:shiro="http://www.pollix.at/thymeleaf/shiro"
```
```html
<html lang="en" xmlns:th="http://www.thymeleaf.org" 
				xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
				xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
```

### 员工管理系统(仅总结部分)
* #### 设计数据库  
  初步设计员工表和部门表。  
  ~~由于参照原文档笔记没有使用链接数据库方式，所以我仅仅概括这个项目中的一些部分功能。~~  
  部分代码在springboot-demo03中测试
* #### 页面以及页面的样式  
  这里使用了模板的页面，所以下载导入后会发现引用的css样式需要在页面中修改引用位置。  
  `<link th:href="@{/.../xxx.css} rel="stylesheet">`找到这种引用，修改位置。  
  在application.properties关闭模板引擎的缓存  
  `spring.thymeleaf.cache=false`  
  改变context-path，自动在访问页面前添加/ycsx  
  `server.servlet.context-path=/ycsx`  
  所有页面的静态资源都需要Thymeleaf来托管。
* #### 国际化(多语言i18n)  
  在resources下创建一个i18n的文件夹，然后创建一个login.properties和login_zh_CN.properties。
  我们发现IDEA中的文件夹合并成了一个叫做Resource Bundle 'login'的文件夹。
  他可以直接通过右键Add Property Files to 来添加，我们选择右边的+号，输入en-US。
  他生成了一个login_en_US.properties的文件。  
  我们在login.properties添加如下的信息，并且点开其他的文件更改不同的译文版本。
```properties
#login.properties

login.tip=请登录
login.password=密码
login.remember=记住我
login.username=用户名
login.btn=登录
```
```properties
#login_en_US.properties

login.tip=Please sign in
login.password=password
login.remember=Remember Me
login.username=UserName
login.btn=Sign in
```
 在application.properties中修改`spring.messages.basename=i18n.login`  
 由于被Thymeleaf托管，他的消息类型是#{}。  
 所以需要修改首页的标签th:text="#{}"。  
 
 点击a标签切换语言的操作，需要配置一个解析器LocaleResolver，并且注册到spring容器中。   
 ~~此处略过~~ 
 
* #### 登录实现
  编写一个LoginController，这里展示一个简单粗暴的验证方式。
```java
public class LoginController {
    @RequestMapping("/user/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session){
        /*这里使用了简单粗暴的例子，只要用户名非空并且密码对上就能登录成功。*/
        if(!StringUtils.isEmpty(username)&&"123456".equals(password)){
            /*登录成功，设置session，只要存在就说明登录成功。*/
            session.setAttribute("loginUser",username);
            return "DashBoard";
        }else {
            /*登陆失败了*/
            model.addAttribute("msg","用户名或者密码错误");
            return "index";
        }
    }
}
```
* #### 登陆拦截器
  上面的登录还需要一个拦截器，不然url直接就能登录后台。  
  在config下创建一个LoginHandlerInterceptor，继承HandlerInterceptor接口。  
  在LoginController下添加登录成功后添加session验证。
  
```text
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*excludePathPatterns()设置的参数是要过滤的参数，一个星号*代表一层过滤/x/y，**代表多层比如/a/b/c/d .. */
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/user/login","/css/*","/js/**","/img/**");
    }
```
* #### 增删改查
  核心部分，篇幅受限。~~鸽了~~

* #### 404跳转
  在templates下创建一个error文件夹。然后创建一个404.html，就完成了。

* #### 后记-前端如何解决
    * 学会使用模板和框架。比如[LayUI](www.layui.com)
    * 栅格系统，表单，侧边栏。
    * 想办法实现前后端独立运行(?)，后端提供json对接。
    * 设计数据库
    * 有一套自己的后台模板，举例 X-admin
    * 让他独立运行出来！ （1个月研究）
  
### 新篇章