### 实战前准备
```text
要解决的问题：
1、导入静态资源
2、首页
3、jsp，模板引擎Thymeleaf
4、装配拓展SpringMVC
5、CRUD
6、拦截器

然后新建我们的项目，勾选Spring Web。
创建一个HelloController测试一下。成功打印了HelloWorld!
```
```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld!";
    }
}
```
### webjars和处理静态资源的方式
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
### 自定义首页、图标
```text
选择在resources/public下创建一个index.html
然后直接启动项目，发现首页变成了index的内容，更换首页成功了。
更换图标的话，在static下创建一个favicon.ico。
然后因为谷歌浏览器缓存清理发现没效果，我选择使用Edge，发现图标更换成功了。
```
### Thymeleaf模板引擎
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
使用Thymeleaf，先导入命名空间
```html
<html xmlns:th="http://www.thymeleaf.org">
```
### Thymeleaf取值方式
```text
${...}  变量
*{...}  选择表达式
#{...}  消息
@{...}  url
~{...}  Fragment
```