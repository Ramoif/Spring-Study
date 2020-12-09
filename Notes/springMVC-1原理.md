SpringMVC（一种软件设计规范）
```text
MVC：
模型      Model 
    1、取得表单数据
    2、调用业务逻辑
    3、转向指定的页面
视图      View
    1、业务逻辑
    2、保存数据的状态
控制器    Controller
    1、显示页面

一个典型的MVC就是 JSP + Servlet + JavaBean
常见的服务器端的MVC框架：Struts、SpringMVC、ASP.NET MVC 、Zend Framework、JSF
常见的前端MVC框架：Vue、angularjs、react、backbone

MVVM： M V VM ViewModel

```
回顾Servlet
```text
第一步：创建maven项目。(通过普通maven项目右键模块第二个添加web框架)
重定向 resp.sendRedirect();
转发携带参数 resp.getRequestDispatcher().forward(req,resp);
```
HelloController出现的问题
```text
可能会出现404的问题，在IDEA的项目配置中Artifact添加lib文件夹，并且把所有的jar包导入。
```
SpringMVC原理
```text
1、用户发送请求给前端控制器
2、前端控制器委托请求给处理器
3、页面控制器/处理器调用业务对象（模型）
4、（模型）返回模型数据
5、返回ModelAndView给前端控制器
6、渲染视图
7、返回控制 给用户产生响应
```
DispatcherServlet
```text
他是前置控制器。是整个SpringMVC的控制中心。
用户发出请求，DispatcherServlet接受并且拦截请求。
举个例子：
url = http://localhost:8080/SpringMVC/hello
拆分为下面三部分：
http://localhost:8080 = 服务器域名
SpringMVC = 部署在服务器上的web站点（配置Artifact的时候的/xxx的项目名。）
hello = 控制器
```
重写HelloServlet步骤
```text
第一步，配置DispatcherServlet，这是SpringMVC的核心。
    1.在web.xml下注册servlet（springmvc）
    2.class找到DispatcherServlet
    Tips: 在SpringMVC中，一般在url-pattern中设置/而不是/*
          /  ->只匹配所有的请求，不会去匹配jsp页面。
          /* ->匹配所有的请求，包括了jsp页面。
    3.在Servlet中需要绑定SpringMVC的配置文件<init-param>
    4.选择contextConfigLocation，然后绑定第二步中的xml。
    5.设置请求级别。（1：和服务器一起启动）

第二步，在resources中配置文件xml（目前是显示配置，以后使用注解）
    1.理清楚核心三要素：（处理映射器，处理适配器，视图解析器）
    2.处理器映射器 BeanNameUrlHandlerMapping
    3.处理器适配器 SimpleControllerHandlerAdapter
    4.视图解析器 InternalResourceViewResolver 设置一个默认id，并且配置其中的元素prefix前缀，suffix后缀。
    Tips:模板引擎还有其他的，例如Thymeleaf Freemarker..
         视图解析器中的元素用于拼接名字。注意“/”。
    
第三步，创建controller
    1.继承Controller，选择spring框架下的web.servlet.mvc.Controller
    2.重写接口函数，创建一个 new ModelAndView()，用于返回值。
    3.编写业务代码...
    Tips: modelAndView常用方法：
        addObject("页面元素名字",要设置的值) 用于添加值
        setViewName("页面名字") 用于跳转视图

第四步，测试
在springmvc-servlet.xml添加一个bean
<bean id="/hello" class="com.ycsx.controller.HelloController"/>
用于测试，启动tomcat，id就是url后面的路径。
class就是要请求的servlet。
```

